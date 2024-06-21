package com.GSU24SE43.ConstructionDrawingManagement.service;

import com.GSU24SE43.ConstructionDrawingManagement.dto.request.LogCreateRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.request.AccessUpdateRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.AccessCreateResponse;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.AccessResponse;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.AccessUpdateResponse;
import com.GSU24SE43.ConstructionDrawingManagement.entity.Log;
import com.GSU24SE43.ConstructionDrawingManagement.entity.Staff;
import com.GSU24SE43.ConstructionDrawingManagement.entity.Version;
import com.GSU24SE43.ConstructionDrawingManagement.exception.AppException;
import com.GSU24SE43.ConstructionDrawingManagement.exception.ErrorCode;
import com.GSU24SE43.ConstructionDrawingManagement.mapper.LogMapper;
import com.GSU24SE43.ConstructionDrawingManagement.repository.LogRepository;
import com.GSU24SE43.ConstructionDrawingManagement.repository.StaffRepository;
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
    StaffRepository staffRepository;
    LogMapper logMapper;

    public AccessCreateResponse create(LogCreateRequest request) {
        Date now = new Date();
        UUID staffId = request.getStaffId();

        Staff staff = staffRepository.findById(staffId).orElseThrow(
                () -> new AppException(ErrorCode.STAFF_NOT_FOUND));

        Log log = new Log();


//        access.setVersion(version);
        log.setAccessDateTime(now);
//        access.setURLLong("Ngày " + now + " người dùng: " + staff.getFullName() + " đã được cấp quyền" );

        log = logRepository.save(log);
        AccessCreateResponse response = logMapper.toAccessCreateResponse(log);

//        response.setVersionId(access.getVersion().getId());

        return response;
    }

    public AccessUpdateResponse updateAccess(UUID accessId, AccessUpdateRequest request) {
        Log log = logRepository.findById(accessId).orElseThrow(
                () -> new AppException(ErrorCode.ACCESS_NOT_FOUND)
        );

//        boolean checkVersion = versionRepository.existsById(request.getVersionId());

//        if (!checkVersion) throw new AppException(ErrorCode.VERSION_NOT_FOUND);

//        Permission permission = permissionRepository.findById(request.getPermissionId()).orElseThrow(
//                () -> new AppException(ErrorCode.PERMISSION_NOT_FOUND)
//        );
        Version version = versionRepository.findById(request.getVersionId()).orElseThrow(
                () -> new AppException(ErrorCode.VERSION_NOT_FOUND)
        );
//        access.setPermission(permission);
        log.setVersion(version);
        logRepository.save(log);
        return logMapper.toAccessUpdateResponse(log);

    }

    public void delete(UUID accessId) {
        logRepository.deleteById(accessId);
    }

    public List<Log> getAllAccess() {
        return logRepository.findAll();
    }

    public List<AccessResponse> getAll(){
        return logMapper.toAccessResponseList(getAllAccess());
    }

    public AccessResponse getById(UUID accessId) {
        Log log = logRepository.findById(accessId).orElseThrow(() -> new AppException(ErrorCode.ACCESS_NOT_FOUND));
        AccessResponse response = logMapper.toAccessResponse(log);
//        response.setPermissionId(access.getPermission().getId());

        return response;
    }


}
