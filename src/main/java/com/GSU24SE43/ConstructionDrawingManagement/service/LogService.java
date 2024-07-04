package com.GSU24SE43.ConstructionDrawingManagement.service;

import com.GSU24SE43.ConstructionDrawingManagement.dto.request.LogCreateRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.request.AccessUpdateRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.AccessCreateResponse;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.AccessResponse;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.AccessUpdateResponse;
import com.GSU24SE43.ConstructionDrawingManagement.entity.DetailTask;
import com.GSU24SE43.ConstructionDrawingManagement.entity.Log;
import com.GSU24SE43.ConstructionDrawingManagement.entity.Version;
import com.GSU24SE43.ConstructionDrawingManagement.exception.AppException;
import com.GSU24SE43.ConstructionDrawingManagement.exception.ErrorCode;
import com.GSU24SE43.ConstructionDrawingManagement.mapper.LogMapper;
import com.GSU24SE43.ConstructionDrawingManagement.repository.DetailTaskRepository;
import com.GSU24SE43.ConstructionDrawingManagement.repository.LogRepository;
import com.GSU24SE43.ConstructionDrawingManagement.repository.VersionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LogService {
    LogRepository logRepository;
    VersionRepository versionRepository;
    DetailTaskRepository detailTaskRepository;
    LogMapper logMapper;

    public AccessCreateResponse create(LogCreateRequest request) {
        Date now = new Date();
        Version version = checkVersion(request.getVersionId());
        DetailTask detailTask = checkDetailTask(request.getDetailTaskId());

        Log log = Log.builder()
                .version(version)
                .detailTask(detailTask)
                .accessDateTime(now)
                .descriptionLog(
                        "Ngày "+ now + "\nNhân viên: " + detailTask.getStaff().getFullName()
                        + "\nMã ID: " + detailTask.getStaff().getStaffId() + "\nĐã được cấp quyền: "
                        + detailTask.getPermissions()
                )
                .build();

        logRepository.save(log);
        return logMapper.toAccessCreateResponse(log);
    }

    public AccessUpdateResponse updateLog(UUID logId, AccessUpdateRequest request) {
        Log log = checkLog(logId);
        Version version = checkVersion(request.getVersionId());
        DetailTask detailTask = checkDetailTask(request.getDetailTaskId());
        log.setVersion(version);
        log.setDescriptionLog(request.getDescriptionLog());
        log.setDetailTask(detailTask);
        logRepository.save(log);
        return logMapper.toAccessUpdateResponse(log);

    }
    public Version checkVersion(UUID versionId){
        return versionRepository.findById(versionId).orElseThrow(
                () -> new AppException(ErrorCode.VERSION_NOT_FOUND)
        );
    }
    public DetailTask checkDetailTask(UUID detailTaskId){
        return detailTaskRepository.findById(detailTaskId).orElseThrow(
                () -> new AppException(ErrorCode.DETAIL_TASK_NOT_FOUND)
        );
    }
    public Log checkLog(UUID logId){
        return logRepository.findById(logId).orElseThrow(
                () -> new AppException(ErrorCode.LOG_NOT_FOUND)
        );
    }

    public void delete(UUID accessId) {
        checkLog(accessId);
        logRepository.deleteById(accessId);
    }

    public List<Log> getAllLog() {
        return logRepository.findAll();
    }

    public List<AccessResponse> getAll(){
        return logMapper.toAccessResponseList(getAllLog());
    }

    public AccessResponse getById(UUID logId) {
        Log log = checkLog(logId);
        return logMapper.toAccessResponse(log);
    }


}
