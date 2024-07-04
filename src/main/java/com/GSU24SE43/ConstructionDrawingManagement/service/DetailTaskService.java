package com.GSU24SE43.ConstructionDrawingManagement.service;

import com.GSU24SE43.ConstructionDrawingManagement.dto.request.DetailTaskCreateRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.request.DetailTaskUpdateRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.DetailTaskCreateResponse;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.DetailTaskResponse;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.DetailTaskUpdateResponse;
import com.GSU24SE43.ConstructionDrawingManagement.entity.DetailTask;
import com.GSU24SE43.ConstructionDrawingManagement.entity.Staff;
import com.GSU24SE43.ConstructionDrawingManagement.entity.Task;
import com.GSU24SE43.ConstructionDrawingManagement.exception.AppException;
import com.GSU24SE43.ConstructionDrawingManagement.exception.ErrorCode;
import com.GSU24SE43.ConstructionDrawingManagement.mapper.DetailTaskMapper;
import com.GSU24SE43.ConstructionDrawingManagement.repository.DetailTaskRepository;
import com.GSU24SE43.ConstructionDrawingManagement.repository.StaffRepository;
import com.GSU24SE43.ConstructionDrawingManagement.repository.TaskRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DetailTaskService {
    DetailTaskRepository detailTaskRepository;
    StaffRepository staffRepository;
    TaskRepository taskRepository;
    DetailTaskMapper detailTaskMapper;

    public DetailTaskCreateResponse createDetailTask(DetailTaskCreateRequest request){
        Staff staff = staffRepository.findById(request.getStaffId()).orElseThrow(
                () -> new AppException(ErrorCode.STAFF_NOT_FOUND));
        Task task = taskRepository.findById(request.getTaskId()).orElseThrow(
                () -> new AppException(ErrorCode.TASK_PARENT_NOT_FOUND));

        DetailTask detailTask = DetailTask
                .builder()
                .task(task)
                .staff(staff)
                .permissions(request.getPermissionList())
                .build();
        detailTaskRepository.save(detailTask);
        return detailTaskMapper.toCreateResponse(detailTask);
    }


    public DetailTaskUpdateResponse detailTaskParentUpdate(UUID detailTaskId, DetailTaskUpdateRequest request){
        DetailTask detailTask = detailTaskRepository.findById(detailTaskId).orElseThrow(
                () -> new AppException(ErrorCode.DETAIL_TASK_NOT_FOUND)
        );
        Task task = taskRepository.findById(request.getTaskId()).orElseThrow(
                () -> new AppException(ErrorCode.TASK_PARENT_NOT_FOUND)
        );
        Staff staff = staffRepository.findById(request.getStaffId()).orElseThrow(
                () -> new AppException(ErrorCode.STAFF_NOT_FOUND)
        );

        detailTask.setTask(task);
        detailTask.setStaff(staff);
        detailTask.setPermissions(request.getPermissionList());
        detailTaskRepository.save(detailTask);
        return detailTaskMapper.toDetailTaskUpdateResponse(detailTask);
    }

    public List<DetailTaskResponse> getAll(){
        return detailTaskRepository.findAll().stream().map(detailTaskMapper::toDetailTaskResponse).toList();
    }

    public void delete(UUID detailTaskId){
        detailTaskRepository.deleteById(detailTaskId);
    }

}
