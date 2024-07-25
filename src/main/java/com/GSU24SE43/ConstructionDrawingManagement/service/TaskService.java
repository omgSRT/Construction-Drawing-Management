package com.GSU24SE43.ConstructionDrawingManagement.service;


import com.GSU24SE43.ConstructionDrawingManagement.dto.request.*;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.*;
import com.GSU24SE43.ConstructionDrawingManagement.entity.*;
import com.GSU24SE43.ConstructionDrawingManagement.enums.Permission;
import com.GSU24SE43.ConstructionDrawingManagement.enums.TaskStatus;
import com.GSU24SE43.ConstructionDrawingManagement.exception.AppException;
import com.GSU24SE43.ConstructionDrawingManagement.exception.ErrorCode;
import com.GSU24SE43.ConstructionDrawingManagement.mapper.TaskMapper;
import com.GSU24SE43.ConstructionDrawingManagement.repository.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TaskService {

    TaskRepository taskRepository;
    ProjectRepository projectRepository;
    DepartmentRepository departmentRepository;
    AccountRepository accountRepository;
    DetailTaskRepository detailTaskRepository;
    StaffRepository staffRepository;
    @Autowired
    TaskMapper taskMapper;
    DrawingRepository drawingRepository;
    SimpMessagingTemplate messagingTemplate;

    //create task parent by admin
    @PreAuthorize("hasRole('ADMIN')")
    public TaskParentCreateResponse createTaskParentByAdmin(TaskParentCreateRequest request) {
        Project project = checkProject(request.getProjectId());
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        Account account = accountRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));
        checkDateTaskVSProject(request.getBeginDate(), request.getEndDate(), project);
        validateProjectDate(request.getBeginDate(), request.getEndDate());
        if (request.getEndDate().before(request.getBeginDate()))
            throw new AppException(ErrorCode.WRONG_BEGINDATE_OR_ENDDATE);
        else {
            Task task = taskMapper.toTask(request);
            task.setProject(project);
            task.setCreateDate(new Date());
            if (account.getRoleName().equals("ADMIN")) {
                task.setAccount(account);
            }
            task.setStatus(TaskStatus.NO_RECIPIENT.getMessage());
            taskRepository.save(task);

            messagingTemplate.convertAndSend("/realtime/notifications", task);

            return taskMapper.toTaskParentCreateResponse(task);
        }
    }

    private void checkDateTaskVSProject(Date startDate, Date endDate, Project project) {
        if (startDate.before(project.getStartDate()))
            throw new AppException(ErrorCode.WRONG_BEGINDATE);
        if (endDate.after(project.getEndDate()))
            throw new AppException(ErrorCode.WRONG_ENDDATE);
    }


    @PreAuthorize("hasRole('ADMIN')")
    public TaskChildCreateResponse createChildTaskByAdmin_V2(UUID parentTaskId, TaskChildCreateRequest_V2 request) {

        Task taskParent = checkTask(parentTaskId);
        Department department = departmentRepository.findById(request.getDepartmentId()).orElseThrow(
                () -> new AppException(ErrorCode.DEPARTMENT_NOT_FOUND)
        );

        Staff staff = findHeadOfDepartment(department);
        if (staff == null) throw new AppException(ErrorCode.THIS_ROOM_HAS_NO_HEADER);

        validateProjectDate(request.getBeginDate(), request.getEndDate());

        Task taskChild = taskMapper.toTaskByAdmin_V2(request);
        taskChild.setParentTask(taskParent);
        taskChild.setCreateDate(new Date());
        taskChild.setAccount(taskParent.getAccount());
        taskChild.setProject(taskParent.getProject());
        if (request.getPriority() <= 0) {
            throw new AppException(ErrorCode.PRIORITY_INVALID);
        }
        // check k trung department
        if (checkDuplicateHead(parentTaskId, request.getDepartmentId())) {
            throw new AppException(ErrorCode.DUPLICATE_HEAD);
        }
        if (request.getPriority() == 1) {
            taskChild.setStatus(TaskStatus.ACTIVE.name());
            taskParent.setStatus(TaskStatus.PROCESSING.name());
        } else taskChild.setStatus(TaskStatus.INACTIVE.name());
        taskChild.setDepartment(department);
        taskChild.setPriority(request.getPriority());
        List<DetailTask> detailTasks = new ArrayList<>();
        request.getPermissions().forEach(permission -> {
            detailTasks.add(
                    new DetailTask(permission, taskChild, staff));

        });
        taskChild.setDetailTasks(detailTasks);
        taskRepository.save(taskChild);
        return taskMapper.toCreateResponse(taskChild);
    }

    private Staff findHeadOfDepartment(Department department) {
        List<Staff> staffs = department.getStaffList();
        for (int i = 0; i < staffs.size(); i++) {
            if (staffs.get(i).isSupervisor()) return staffs.get(i);
        }
        return null;
    }

    private boolean checkDuplicatePriority(UUID taskParentId, int priority) {
        boolean check = false;
        Task parentTask = taskRepository.findById(taskParentId).orElseThrow(
                () -> new AppException(ErrorCode.TASK_PARENT_NOT_FOUND));
        for (int i = 0; i < parentTask.getTasks().size(); i++) {
            if (parentTask.getTasks().get(i).getPriority() == priority) {
                check = true;
                break;
            }
        }
        return check;
    }

    private boolean checkDuplicateHead(UUID taskParentId, UUID departmentId) {
        Task parentTask = taskRepository.findById(taskParentId).orElseThrow(
                () -> new AppException(ErrorCode.TASK_PARENT_NOT_FOUND));

        return parentTask.getTasks().stream()
                .anyMatch(task -> task.getDepartment().getDepartmentId() == departmentId);
    }

    //create task parent by head
    @PreAuthorize("hasRole('ADMIN') or hasRole('HEAD_OF_ARCHITECTURAL_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_STRUCTURAL_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_MvE_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_INTERIOR_DESIGN_DEPARTMENT')")
    public TaskParentCreateByHeadResponse createTaskParentByHead(TaskParentCreateByHeadRequest request) {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        Account account = accountRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));
        Project project = checkProject(request.getProjectId());

        Date beginDate = request.getBeginDate();
        Date endDate = request.getEndDate();
        if (endDate.before(beginDate)) throw new AppException(ErrorCode.WRONG_BEGINDATE_OR_ENDDATE);
        else {
            Task task = taskMapper.toTaskByHead(request);
            task.setProject(project);
            task.setDepartment(account.getStaff().getDepartment());
            task.setCreateDate(new Date());
            task.setStatus(TaskStatus.NO_RECIPIENT.getMessage());
            taskRepository.save(task);
            return taskMapper.toTaskParentCreateByHeadResponse(task);
        }
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('HEAD_OF_ARCHITECTURAL_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_STRUCTURAL_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_MvE_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_INTERIOR_DESIGN_DEPARTMENT')")
    public TaskChildCreateByHeadResponse createTaskChildByHead_LHNH(UUID parentTaskId, TaskChildCreateByHead_V2Request request) {
        Task taskParent = checkTask(parentTaskId);
        Account account = getSecurityContext();
        validateProjectDate(request.getBeginDate(), request.getEndDate());
        if (request.getPriority() <= 0) throw new AppException(ErrorCode.PRIORITY_INVALID);

        Task taskChild = taskMapper.toTaskByHead_3(request);
        List<Staff> staffList = request.getStaffs().stream()
                .map(staffId -> {
                    Staff s = staffRepository.findById(staffId).orElseThrow(
                            () -> new AppException(ErrorCode.STAFF_NOT_FOUND));
                    log.info(s.getStaffId().toString());
                    return s;
                })
                .toList();
        List<DetailTask> detailTaskList = new ArrayList<>();

        taskChild.setParentTask(taskParent);
        taskChild.setDepartment(account.getStaff().getDepartment());
        taskChild.setProject(taskParent.getProject());
        taskChild.setPriority(request.getPriority());
        taskChild.setCreateDate(new Date());
        taskChild.setStatus(TaskStatus.ACTIVE.name());
        taskParent.setStatus(TaskStatus.PROCESSING.name());

        staffList.forEach(staff -> {
            request.getPermissions().forEach(permission -> {
                detailTaskList.add(new DetailTask(
                                permission, taskChild, staff
                        )
                );
            });
        });
        taskChild.setDetailTasks(detailTaskList);
        taskRepository.save(taskChild);
        TaskChildCreateByHeadResponse response = taskMapper.toTaskChildCreateByHeadResponse(taskChild);
        response.setPermissions(request.getPermissions());
//        return taskMapper.toTaskChildCreateByHeadResponse(taskChild);
        return response;
    }

    private Account getSecurityContext() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        return accountRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));
    }


    @PreAuthorize("hasRole('ADMIN')")
    public TaskParentUpdateByAdminResponse updateTaskParentByAdmin(UUID parentTaskId, TaskParentUpdateByAdminRequest request) {
        Task taskParent = checkTask(parentTaskId);
        Project project = checkProject(request.getProjectId());
        if (request.getEndDate().before(request.getBeginDate()))
            throw new AppException(ErrorCode.WRONG_BEGINDATE_OR_ENDDATE);

        taskParent.setProject(project);
        taskParent.setTitle(request.getTitle());
        taskParent.setDescription(request.getDescription());
        taskParent.setBeginDate(request.getBeginDate());
        taskParent.setEndDate(request.getEndDate());

        taskRepository.save(taskParent);
        return taskMapper.toTaskParentUpdateByAdminResponse(taskParent);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public TaskChildUpdateByAdminResponse updateTaskChildByAdmin(UUID childTaskId, TaskChildUpdateByAdminRequest request) {
        Task taskChild = checkTask(childTaskId);
        Department department = checkDepartment(request.getDepartmentId());
        if (request.getEndDate().before(request.getBeginDate()))
            throw new AppException(ErrorCode.WRONG_BEGINDATE_OR_ENDDATE);
        //nếu check trùng head thì sẽ ko update dc task child
        taskChild.setDepartment(department);
        taskChild.setTitle(request.getTitle());
        taskChild.setDescription(request.getDescription());
        taskChild.setPriority(request.getPriority());
        taskChild.setBeginDate(request.getBeginDate());
        taskChild.setEndDate(request.getEndDate());
        taskRepository.save(taskChild);

        return taskMapper.toTaskChildUpdateByAdminResponse(taskChild);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('HEAD_OF_ARCHITECTURAL_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_STRUCTURAL_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_MvE_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_INTERIOR_DESIGN_DEPARTMENT')")
//    public TaskParentUpdateByAdminResponse updateStatusTaskParent(UUID parentTaskId, String status) {
//        Task taskParent = checkTask(parentTaskId);
//        checkStatusTask(status, taskParent);
//        return taskMapper.toTaskParentUpdateByAdminResponse(taskParent);
//    }
    public TaskParentUpdateByAdminResponse updateStatusTaskParent(UUID parentTaskId, TaskStatus status) {
        Task taskParent = checkTask(parentTaskId);
        checkStatusTask(status.name(), taskParent);
        taskRepository.save(taskParent);
        return taskMapper.toTaskParentUpdateByAdminResponse(taskParent);
    }

//    @PreAuthorize("hasRole('ADMIN') or hasRole('HEAD_OF_ARCHITECTURAL_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_STRUCTURAL_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_MvE_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_INTERIOR_DESIGN_DEPARTMENT')")
//    public TaskChildUpdateByAdminResponse updateStatusTaskChild(UUID childTaskId, TaskStatus status) {
//        Task taskChild = checkTask(childTaskId);
//        checkStatusTask(status.name(), taskChild);
//        taskRepository.save(taskChild);
//        return taskMapper.toTaskChildUpdateByAdminResponse(taskChild);
//    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('HEAD_OF_ARCHITECTURAL_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_STRUCTURAL_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_MvE_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_INTERIOR_DESIGN_DEPARTMENT')")
    public TaskChildUpdateByAdminResponse updateTaskChildByDesigner(UUID childTaskId, UUID drawingId) {
        Task taskChild = checkTask(childTaskId);
        Drawing drawing = drawingRepository.findById(drawingId).orElseThrow(() -> new AppException(ErrorCode.DRAWING_NOT_FOUND));
        taskChild.getDrawingList().add(drawing);
        taskRepository.save(taskChild);
        return taskMapper.toTaskChildUpdateByAdminResponse(taskChild);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('HEAD_OF_ARCHITECTURAL_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_STRUCTURAL_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_MvE_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_INTERIOR_DESIGN_DEPARTMENT')")
    public TaskChildUpdateByAdminResponse upgradeStatus_3(UUID childTaskId, TaskStatus status) {
        Task taskChild = checkTask(childTaskId);
        checkStatusTask(status.name(), taskChild);
        Task taskParent = taskChild.getParentTask();
        List<Task> taskList = taskParent.getTasks();
        taskRepository.save(taskChild);

        if (status.name().equals(TaskStatus.DONE.name())) {
            int priority = taskChild.getPriority();
            // Kiểm tra tất cả các task cùng priority
            boolean allTasksDone = true;
            for (Task task : taskList) {
                if (task.getPriority() == priority && !task.getId().equals(taskChild.getId())) {
                    if (!TaskStatus.DONE.name().equals(task.getStatus())) {
                        allTasksDone = false;
                        break;
                    }
                }
            }

            // Nếu tất cả các task cùng priority đã DONE
            if (allTasksDone) {
                // Kiểm tra xem task hiện tại có phải là task cuối cùng không
                boolean isLastTask = true;
                for (Task task : taskList) {
                    if (task.getPriority() > priority) {
                        isLastTask = false;
                        break;
                    }
                }

                // Nếu task hiện tại là task cuối cùng thì cập nhật trạng thái của taskParent
                if (isLastTask) {
                    taskParent.setStatus(TaskStatus.DONE.name());
                    taskRepository.save(taskParent);
                } else {
                    // Xác định task tiếp theo và cập nhật trạng thái
                    int nextPriority = priority + 1;
                    Task nextTask = taskRepository.findByPriorityAndParentTaskId(nextPriority, taskParent.getId());
                    if (nextTask != null) {
                        nextTask.setStatus(TaskStatus.ACTIVE.name());
                        taskRepository.save(nextTask);
                    } else {
                        throw new AppException(ErrorCode.NEXT_TASK_HAS_NOT_BEEN_INITIALIZE);
                    }
                }
            }
        }
        return taskMapper.toTaskChildUpdateByAdminResponse(taskChild);
    }



    private boolean checkAllTaskDuplicateSuccess(List<Task> list) {
        boolean check = true;
        for (Task x : list) {
            if (!x.getStatus().equals(TaskStatus.DONE.name())) {
                check = false;
                break;
            }
        }

        return check;
    }
    private boolean checkDuplicateHead2(UUID taskParentId, UUID departmentId) {
        Task parentTask = taskRepository.findById(taskParentId).orElseThrow(
                () -> new AppException(ErrorCode.TASK_PARENT_NOT_FOUND));

        return parentTask.getTasks().stream()
                .anyMatch(task -> task.getDepartment().getDepartmentId() == departmentId);
    }

    private Task checkTask(UUID taskId) {
        return taskRepository.findById(taskId).orElseThrow(
                () -> new AppException(ErrorCode.TASK_PARENT_NOT_FOUND));
    }

    private Project checkProject(UUID projectId) {
        return projectRepository.findById(projectId).orElseThrow(
                () -> new AppException(ErrorCode.PROJECT_NOT_FOUND)
        );
    }

    private Department checkDepartment(UUID departmentId) {
        return departmentRepository.findById(departmentId).orElseThrow(
                () -> new AppException(ErrorCode.DEPARTMENT_NOT_FOUND));
    }

    private void checkStatusTask(String status, Task task) {
        if (!status.equals(TaskStatus.NO_RECIPIENT.name())
                && !status.equals(TaskStatus.ACTIVE.name())
                && !status.equals(TaskStatus.INACTIVE.name())
                && !status.equals(TaskStatus.PROCESSING.name())
                && !status.equals(TaskStatus.DONE.name()))
            throw new AppException(ErrorCode.UNDEFINED_STATUS_TASK);
        task.setStatus(status);
    }

    private void validateProjectDate(Date startDate, Date endDate) {
        if (startDate.after(endDate)) {
            throw new AppException(ErrorCode.INVALID_START_DATE_EARLIER_THAN_END_DATE);
        }
        if (startDate.before(new Date())) {
            throw new AppException(ErrorCode.INVALID_START_DATE_NOT_IN_FUTURE);
        }
        if (endDate.before(new Date())) {
            throw new AppException(ErrorCode.INVALID_END_DATE_NOT_IN_FUTURE);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<Task> getAll() {
        return taskRepository.findAll();
    }


    @PreAuthorize("hasRole('ADMIN')")
    public List<Task> getAllParentTask() {
        return taskRepository.findByParentTaskIsNull();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<Task> getAllParentTaskOfAdmin() {
        List<Task> list = new ArrayList<>();
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        Account account = accountRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));
        if (account.getRoleName().equals("ADMIN")) {
            list = taskRepository.findByAccountAndParentTaskIsNull(account);
        }
        return list;
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('HEAD_OF_ARCHITECTURAL_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_STRUCTURAL_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_MvE_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_INTERIOR_DESIGN_DEPARTMENT')")
    public List<Task> getParentTaskByProjectId(UUID projectId) {
        Project project = checkProject(projectId);
        List<Task> taskList = new ArrayList<>();
        project.getTasks().forEach(task -> {
            if (task.getParentTask() == null) taskList.add(task);
        });
        return taskList;
    }

    //    @PreAuthorize("hasRole('ADMIN')")
//    public List<Task> getAllChildTaskOfAdmin() {
//        List<Task> list = new ArrayList<>();
//        var context = SecurityContextHolder.getContext();
//        String name = context.getAuthentication().getName();
//        Account account = accountRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));
//        if (account.getRoleName().equals("ADMIN")) {
//            list = taskRepository.findByAccount_AccountIdAndParentTaskIsNotNull(account.getAccountId());
//        }
//        return list;
//    }
    @PreAuthorize("hasRole('ADMIN')")
    public List<Task> getAllChildTasksOfAdmin() {
        List<Task> tasks = new ArrayList<>();
        var context = SecurityContextHolder.getContext();
        String username = context.getAuthentication().getName();
        Account account = accountRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));

        if (account.getRoleName().equals("ADMIN")) {
            tasks = taskRepository.findTasksWithParentByAccountId(account.getAccountId());
        }

        return tasks;
    }


    @PreAuthorize("hasRole('HEAD_OF_ARCHITECTURAL_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_STRUCTURAL_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_MvE_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_INTERIOR_DESIGN_DEPARTMENT')")
    public List<Task> getAllParentTaskOfHead() {
        List<Task> list = new ArrayList<>();
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        Account account = accountRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));
        Department department = departmentRepository.findById(account.getStaff().getDepartment().getDepartmentId()).orElseThrow(
                () -> new AppException(ErrorCode.DEPARTMENT_NOT_FOUND));
        if (account.getStaff().isSupervisor()) {
            list = taskRepository.findByDepartmentAndParentTaskIsNull(department);
        }
        return list;
    }

    @PreAuthorize("hasRole('HEAD_OF_ARCHITECTURAL_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_STRUCTURAL_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_MvE_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_INTERIOR_DESIGN_DEPARTMENT')")
    public List<Task> getAllChildTaskOfHead() {
        List<Task> list = new ArrayList<>();
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        Account account = accountRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));
        Department department = departmentRepository.findById(account.getStaff().getDepartment().getDepartmentId()).orElseThrow(
                () -> new AppException(ErrorCode.DEPARTMENT_NOT_FOUND));
        if (account.getStaff().isSupervisor()) {
            list = taskRepository.findByDepartmentAndParentTaskIsNotNull(department);
        }
        return list;
    }
//********************************************
//    @PreAuthorize("hasRole('DESIGNER')")
//    @PreAuthorize("hasRole('DESIGNER') or hasRole('HEAD_OF_ARCHITECTURAL_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_STRUCTURAL_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_MvE_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_INTERIOR_DESIGN_DEPARTMENT')")
//    public List<Task> getAllTaskOfDesigner() {
//        var context = SecurityContextHolder.getContext();
//        String name = context.getAuthentication().getName();
//        Account account = accountRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));
//        List<DetailTask> detailTasks = detailTaskRepository.findByStaffStaffId(account.getStaff().getStaffId());
//        return detailTasks.stream()
//                .map(DetailTask::getTask)
//                .distinct()
//                .collect(Collectors.toList());
//    }
// ********************************************

    //    @PreAuthorize("hasRole('DESIGNER') or hasRole('HEAD_OF_ARCHITECTURAL_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_STRUCTURAL_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_MvE_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_INTERIOR_DESIGN_DEPARTMENT')")
//    public List<Task> getAllTaskOfHead() {
//        var context = SecurityContextHolder.getContext();
//        String name = context.getAuthentication().getName();
//        Account account = accountRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));
//        List<DetailTask> detailTasks = detailTaskRepository.findByStaffStaffId(account.getStaff().getStaffId());
//        return detailTasks.stream()
//                .map(DetailTask::getTask)
//                .distinct()
//                .collect(Collectors.toList());
//    }
    @PreAuthorize("hasRole('DESIGNER') or hasRole('HEAD_OF_ARCHITECTURAL_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_STRUCTURAL_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_MvE_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_INTERIOR_DESIGN_DEPARTMENT')")
    public List<TaskResponse> getAllTaskOfHead() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        Account account = accountRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));
        List<DetailTask> detailTasks = detailTaskRepository.findByStaffStaffId(account.getStaff().getStaffId());
        return detailTasks.stream()
                .map(DetailTask::getTask)
                .distinct()
                .map(taskMapper::toTaskResponse)
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('DESIGNER') or hasRole('HEAD_OF_ARCHITECTURAL_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_STRUCTURAL_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_MvE_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_INTERIOR_DESIGN_DEPARTMENT')")
    public List<TaskResponseDesigner> getAllTaskOfDesigner() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        Account account = accountRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));
        List<DetailTask> detailTasks = detailTaskRepository.findByStaffStaffId(account.getStaff().getStaffId());
//        List<DetailTask> detailTasks_2 = detailTaskRepository.findByTask_Id();
//        return detailTasks.stream()
//                .map(DetailTask::getTask)
//                .distinct()
//                .map(taskMapper::toTaskResponse)
//                .collect(Collectors.toList());
//        Set<UUID> list = new HashSet<>();
//                detailTasks_2.forEach(detailTask -> {list.add(detailTask.getStaff().getStaffId());});
        return detailTasks.stream()
                .map(DetailTask::getTask)
                .distinct()
                .map(task -> {
                    TaskResponseDesigner respone = taskMapper.toResponseDesigner(task);
//                    respone.setStaffIds(list);
                    return respone;
                })
                .collect(Collectors.toList());
    }


    @PreAuthorize("hasRole('ADMIN') or hasRole('HEAD_OF_ARCHITECTURAL_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_STRUCTURAL_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_MvE_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_INTERIOR_DESIGN_DEPARTMENT') or hasRole('DESIGNER') or hasRole('COMMANDER')")
    public Task findTaskById(UUID taskId) {
        return taskRepository.findById(taskId).orElseThrow(() -> new AppException(ErrorCode.TASK_NOT_FOUND));
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('HEAD_OF_ARCHITECTURAL_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_STRUCTURAL_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_MvE_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_INTERIOR_DESIGN_DEPARTMENT') or hasRole('DESIGNER') or hasRole('COMMANDER')")
    public List<Task> filterTask(UUID id, String title, String status, Date beginDate, Date endDate) {
        List<Task> list = new ArrayList<>();
        if (id != null) {
            list.add(taskRepository.findById(id).orElseThrow(
                    () -> new AppException(ErrorCode.TASK_NOT_FOUND)));
        }
        if (title != null && !title.isEmpty()) {
            list = taskRepository.findByTitleContainingIgnoreCase(title);
        }
        if (status != null && !status.isEmpty()) {
            list = taskRepository.findByStatusContainingIgnoreCase(status);
        }
        if (beginDate != null) {
            list = taskRepository.findByBeginDate(beginDate);
        }
        if (endDate != null) {
            list = taskRepository.findByEndDate(endDate);
        }
        return list;
    }

    public List<Task> getChildTaskOfAParentTask(UUID taskParentId) {
        Task taskParent = checkTask(taskParentId);
        return taskParent.getTasks();
    }


    @PreAuthorize("hasRole('ADMIN')")
    public void deleteTask(UUID taskId) {
        checkTask(taskId);
        taskRepository.deleteById(taskId);
    }


}
