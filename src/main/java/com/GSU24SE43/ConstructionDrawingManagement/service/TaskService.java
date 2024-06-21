package com.GSU24SE43.ConstructionDrawingManagement.service;


import com.GSU24SE43.ConstructionDrawingManagement.dto.request.TaskCreateRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.request.TaskParentCreateRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.TaskCreateResponse;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.TaskParentCreateResponse;
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

    public TaskParentCreateResponse createTaskParent(TaskParentCreateRequest request){

        Department department = departmentRepository.findById(request.getDepartmentId()).orElseThrow(
                () -> new AppException(ErrorCode.DEPARTMENT_NOT_FOUND)
        );
//        Project project = projectRepository.findById(request.getProjectId()).orElseThrow(
//                () -> new AppException(ErrorCode.PROJECT_NOT_FOUND)
//        );

        Task task = taskMapper.toTask(request);
        task.setDepartment(department);
//        task.setProject(project);
        task.setCreateDate(new Date());
        task.setStatus(TaskStatus.NO_RECIPIENT.getMessage());
        taskRepository.save(task);
        return taskMapper.toTaskParentCreateResponse(task);
    }

    public TaskCreateResponse createChildTask(UUID parentTaskId, TaskCreateRequest request){
        Task taskParent = taskRepository.findById(parentTaskId).orElseThrow(
                () -> new AppException(ErrorCode.TASK_PARENT_NOT_FOUND));
        Department department = departmentRepository.findById(request.getDepartmentId()).orElseThrow(
                () -> new AppException(ErrorCode.DEPARTMENT_NOT_FOUND)
        );
//        Project project = projectRepository.findById(request.getProjectId()).orElseThrow(
//                () -> new AppException(ErrorCode.PROJECT_NOT_FOUND)
//        );
        Task taskChild = taskMapper.toTask_2(request);
        taskChild.setParentTask(taskParent);
        taskChild.setCreateDate(new Date());
        taskChild.setDepartment(department);
//        taskChild.setProject(project);
        taskRepository.save(taskChild);
        return taskMapper.toCreateResponse(taskChild);
    }

    public List<Task> getAll(){
        return taskRepository.findAll();
    }

}
