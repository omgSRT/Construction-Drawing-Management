package com.GSU24SE43.ConstructionDrawingManagement.service;


import com.GSU24SE43.ConstructionDrawingManagement.dto.request.TaskChildCreateRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.request.TaskParentCreateRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.TaskChildCreateResponse;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.TaskParentCreateResponse;
import com.GSU24SE43.ConstructionDrawingManagement.entity.Department;
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
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TaskService {

    TaskRepository taskRepository;
    ProjectRepository projectRepository;
    DepartmentRepository departmentRepository;
    TaskMapper taskMapper;

    public TaskParentCreateResponse createTaskParentByAdmin(TaskParentCreateRequest request) {
//        Project project = projectRepository.findById(request.getProjectId()).orElseThrow(
//                () -> new AppException(ErrorCode.PROJECT_NOT_FOUND)
//        );
        Date beginDate = request.getBeginDate();
        Date endDate = request.getEndDate();
        if (endDate.before(beginDate)) throw new AppException(ErrorCode.WRONG_BEGINDATE_OR_ENDDATE);
        else {
            Task task = taskMapper.toTask(request);
//        task.setProject(project);
            task.setCreateDate(new Date());
            task.setStatus(TaskStatus.NO_RECIPIENT.getMessage());
            taskRepository.save(task);

            return taskMapper.toTaskParentCreateResponse(task);
        }
    }

    public TaskChildCreateResponse createChildTaskByAdmin(UUID parentTaskId, TaskChildCreateRequest request) {
        Task taskParent = taskRepository.findById(parentTaskId).orElseThrow(
                () -> new AppException(ErrorCode.TASK_PARENT_NOT_FOUND));
        Department department = departmentRepository.findById(request.getDepartmentId()).orElseThrow(
                () -> new AppException(ErrorCode.DEPARTMENT_NOT_FOUND)
        );
//        Project project = projectRepository.findById(request.getProjectId()).orElseThrow(
//                () -> new AppException(ErrorCode.PROJECT_NOT_FOUND)
//        );
        Date beginDate = request.getBeginDate();
        Date endDate = request.getEndDate();
        if (endDate.before(beginDate)) throw new AppException(ErrorCode.WRONG_BEGINDATE_OR_ENDDATE);

        Task taskChild = taskMapper.toTask_2(request);
        taskChild.setParentTask(taskParent);
        taskChild.setCreateDate(new Date());
        taskChild.setDepartment(department);
        //        taskChild.setProject(project);
        boolean check = checkDuplicate(parentTaskId, request.getPriority());

        taskChild.setPriority(request.getPriority());
        if (request.getPriority() > 4 || request.getPriority() <= 0){
            throw new AppException(ErrorCode.PRIORITY_INVALID);
        }else if (check) {
            throw new AppException(ErrorCode.PRIORITY_IS_DUPLICATE);
        }
        else if (request.getPriority() == 1) {
            taskChild.setStatus(TaskStatus.ACTIVE.name());
            taskParent.setStatus(TaskStatus.PROCESSING.name());
        } else taskChild.setStatus(TaskStatus.INACTIVE.name());
        taskRepository.save(taskChild);
        return taskMapper.toCreateResponse(taskChild);
    }

    public boolean checkDuplicate(UUID taskParentId, int priority){
        boolean check = false;
        Task parentTask = taskRepository.findById(taskParentId).orElseThrow(
                () -> new AppException(ErrorCode.TASK_PARENT_NOT_FOUND));
        for (int i = 1; i < parentTask.getTasks().size(); i++){
            if(parentTask.getTasks().get(i).getPriority() == priority)
            check = true;
        }
        return check;
    }
    



    public List<Task> getAll() {

        return taskRepository.findAll();
    }

    public List<Task> getAllParentTask() {
        return taskRepository.findByParentTaskIsNull();
    }


}
