package com.GSU24SE43.ConstructionDrawingManagement.service;


import com.GSU24SE43.ConstructionDrawingManagement.dto.request.*;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.*;
import com.GSU24SE43.ConstructionDrawingManagement.entity.Department;
import com.GSU24SE43.ConstructionDrawingManagement.entity.Project;
import com.GSU24SE43.ConstructionDrawingManagement.entity.Task;
import com.GSU24SE43.ConstructionDrawingManagement.enums.TaskStatus;
import com.GSU24SE43.ConstructionDrawingManagement.exception.AppException;
import com.GSU24SE43.ConstructionDrawingManagement.exception.ErrorCode;
import com.GSU24SE43.ConstructionDrawingManagement.mapper.TaskMapper;
import com.GSU24SE43.ConstructionDrawingManagement.repository.DepartmentRepository;
import com.GSU24SE43.ConstructionDrawingManagement.repository.ProjectRepository;
import com.GSU24SE43.ConstructionDrawingManagement.repository.TaskRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TaskService {

    TaskRepository taskRepository;
    ProjectRepository projectRepository;
    DepartmentRepository departmentRepository;
    TaskMapper taskMapper;

    //create task parent by admin
    @PreAuthorize("hasRole('ADMIN')")
    public TaskParentCreateResponse createTaskParentByAdmin(TaskParentCreateRequest request) {
        Project project = checkProject(request.getProjectId());
        Date beginDate = request.getBeginDate();
        Date endDate = request.getEndDate();
        if (endDate.before(beginDate)) throw new AppException(ErrorCode.WRONG_BEGINDATE_OR_ENDDATE);
        else {
            Task task = taskMapper.toTask(request);
            task.setProject(project);
            task.setCreateDate(new Date());
            task.setStatus(TaskStatus.NO_RECIPIENT.getMessage());
            taskRepository.save(task);
            return taskMapper.toTaskParentCreateResponse(task);
        }
    }
    @PreAuthorize("hasRole('ADMIN')")
    public TaskChildCreateResponse createChildTaskByAdmin(UUID parentTaskId, TaskChildCreateRequest request) {

        Task taskParent = checkTask(parentTaskId);
        Department department = departmentRepository.findById(request.getDepartmentId()).orElseThrow(
                () -> new AppException(ErrorCode.DEPARTMENT_NOT_FOUND)
        );
        Date beginDate = request.getBeginDate();
        Date endDate = request.getEndDate();
        if (endDate.before(beginDate)) throw new AppException(ErrorCode.WRONG_BEGINDATE_OR_ENDDATE);

        Task taskChild = taskMapper.toTaskByAdmin(request);
        taskChild.setParentTask(taskParent);
        taskChild.setCreateDate(new Date());

        taskChild.setProject(taskParent.getProject());
        if (request.getPriority() > 4 || request.getPriority() <= 0) {
            throw new AppException(ErrorCode.PRIORITY_INVALID);
        }
        if (checkDuplicatePriority(parentTaskId, request.getPriority())) {
            throw new AppException(ErrorCode.PRIORITY_IS_DUPLICATE);
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
        taskRepository.save(taskChild);
        return taskMapper.toCreateResponse(taskChild);
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
    @PreAuthorize("hasRole('ADMIN') or hasAnyAuthority('HEAD_OF_ARCHITECTURAL_DESIGN_DEPARTMENT', 'HEAD_OF_STRUCTURAL_DESIGN_DEPARTMENT','HEAD_OF_MvE_DESIGN_DEPARTMENT','HEAD_OF_INTERIOR_DESIGN_DEPARTMENT')")
    public TaskParentCreateByHeadResponse createTaskParentByHead(TaskParentCreateByHeadRequest request) {
        Department department = checkDepartment(request.getDepartmentId());
        Project project = checkProject(request.getProjectId());
        Date beginDate = request.getBeginDate();
        Date endDate = request.getEndDate();
        if (endDate.before(beginDate)) throw new AppException(ErrorCode.WRONG_BEGINDATE_OR_ENDDATE);
        else {
            Task task = taskMapper.toTaskByHead(request);
            task.setProject(project);
            task.setDepartment(department);
            task.setCreateDate(new Date());
            task.setStatus(TaskStatus.NO_RECIPIENT.getMessage());
            taskRepository.save(task);
            return taskMapper.toTaskParentCreateByHeadResponse(task);
        }
    }

    //create task child by head
    @PreAuthorize("hasRole('ADMIN') or hasAnyAuthority('HEAD_OF_ARCHITECTURAL_DESIGN_DEPARTMENT', 'HEAD_OF_STRUCTURAL_DESIGN_DEPARTMENT','HEAD_OF_MvE_DESIGN_DEPARTMENT','HEAD_OF_INTERIOR_DESIGN_DEPARTMENT')")
    public TaskChildCreateByHeadResponse createTaskChildByHead(UUID parentTaskId, TaskChildCreateByHeadRequest request) {
        Task taskParent = checkTask(parentTaskId);
        Department department = checkDepartment(request.getDepartmentId());
        Project project = checkProject(request.getProjectId());
        Date beginDate = request.getBeginDate();
        Date endDate = request.getEndDate();
        if (endDate.before(beginDate)) throw new AppException(ErrorCode.WRONG_BEGINDATE_OR_ENDDATE);
        Task taskChild = taskMapper.toTaskByHead_2(request);
        taskChild.setParentTask(taskParent);
        taskChild.setDepartment(department);
        taskChild.setProject(project);
        taskChild.setPriority(request.getPriority());
        taskChild.setCreateDate(new Date());
        taskChild.setStatus(TaskStatus.INACTIVE.name());
        taskParent.setStatus(TaskStatus.PROCESSING.name());
        taskRepository.save(taskChild);
        return taskMapper.toTaskChildCreateByHeadResponse(taskChild);
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
    @PreAuthorize("hasRole('ADMIN') or hasAnyAuthority('HEAD_OF_ARCHITECTURAL_DESIGN_DEPARTMENT', 'HEAD_OF_STRUCTURAL_DESIGN_DEPARTMENT','HEAD_OF_MvE_DESIGN_DEPARTMENT','HEAD_OF_INTERIOR_DESIGN_DEPARTMENT')")
    public TaskParentUpdateByAdminResponse updateStatusTaskParent(UUID parentTaskId, String status) {
        Task taskParent = checkTask(parentTaskId);
        checkStatusTask(status, taskParent);
        return taskMapper.toTaskParentUpdateByAdminResponse(taskParent);
    }
    @PreAuthorize("hasRole('ADMIN') or hasAnyAuthority('HEAD_OF_ARCHITECTURAL_DESIGN_DEPARTMENT', 'HEAD_OF_STRUCTURAL_DESIGN_DEPARTMENT','HEAD_OF_MvE_DESIGN_DEPARTMENT','HEAD_OF_INTERIOR_DESIGN_DEPARTMENT')")
    public TaskChildUpdateByAdminResponse updateStatusTaskChild(UUID childTaskId, String status) {
        Task taskChild = checkTask(childTaskId);
        checkStatusTask(status, taskChild);
        return taskMapper.toTaskChildUpdateByAdminResponse(taskChild);
    }
    private Task checkTask(UUID taskId){
        return taskRepository.findById(taskId).orElseThrow(
                () -> new AppException(ErrorCode.TASK_PARENT_NOT_FOUND));
    }
    private Project checkProject(UUID projectId){
        return projectRepository.findById(projectId).orElseThrow(
                () -> new AppException(ErrorCode.PROJECT_NOT_FOUND)
        );
    }
    private Department checkDepartment(UUID departmentId){
        return departmentRepository.findById(departmentId).orElseThrow(
                () -> new AppException(ErrorCode.DEPARTMENT_NOT_FOUND));
    }
    private void checkStatusTask(String status, Task taskParent) {
        if (!status.equals(TaskStatus.NO_RECIPIENT.name())
                && !status.equals(TaskStatus.ACTIVE.name())
                && !status.equals(TaskStatus.INACTIVE.name())
                && !status.equals(TaskStatus.PROCESSING.name())
                && !status.equals(TaskStatus.DONE.name()))
            throw new AppException(ErrorCode.UNDEFINED_STATUS_TASK);
        taskParent.setStatus(status);
    }
    @PreAuthorize("hasRole('ADMIN')")
    public List<Task> getAll() {
        return taskRepository.findAll();
    }
    //thiếu getAll task những task mà 1 head đã giao

    @PreAuthorize("hasRole('ADMIN')")
    public List<Task> getAllParentTask() {
        return taskRepository.findByParentTaskIsNull();
    }
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteTask(UUID taskId) {
        taskRepository.deleteById(taskId);
    }


}
