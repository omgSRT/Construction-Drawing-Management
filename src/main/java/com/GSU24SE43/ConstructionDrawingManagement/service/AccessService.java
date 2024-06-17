package com.GSU24SE43.ConstructionDrawingManagement.service;

import com.GSU24SE43.ConstructionDrawingManagement.dto.request.AccessCreateRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.AccessCreateResponse;
import com.GSU24SE43.ConstructionDrawingManagement.entity.Access;
import com.GSU24SE43.ConstructionDrawingManagement.entity.Permission;
import com.GSU24SE43.ConstructionDrawingManagement.entity.Staff;
import com.GSU24SE43.ConstructionDrawingManagement.entity.Version;
import com.GSU24SE43.ConstructionDrawingManagement.exception.AppException;
import com.GSU24SE43.ConstructionDrawingManagement.exception.ErrorCode;
import com.GSU24SE43.ConstructionDrawingManagement.mapper.AccessMapper;
import com.GSU24SE43.ConstructionDrawingManagement.repository.AccessRepository;
import com.GSU24SE43.ConstructionDrawingManagement.repository.PermissionRepository;
import com.GSU24SE43.ConstructionDrawingManagement.repository.StaffRepository;
import com.GSU24SE43.ConstructionDrawingManagement.repository.VersionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccessService {
    AccessRepository accessRepository;
    PermissionRepository permissionRepository;
    VersionRepository versionRepository;
    StaffRepository staffRepository;
    AccessMapper accessMapper;

    public AccessCreateResponse create(AccessCreateRequest request){
        UUID staffId = request.getStaffId();
        UUID permissionId = request.getPermissonId();
//        UUID versionId = request.getVersionId();

        Staff staff = staffRepository.findById(staffId).orElseThrow(
                () -> new AppException(ErrorCode.STAFF_NOT_FOUND));
        Permission permission = permissionRepository.findById(permissionId).orElseThrow(
                () -> new AppException(ErrorCode.PERMISSION_NOT_FOUND));
//        Version version = versionRepository.findById(versionId).orElseThrow(
//                () -> new AppException(ErrorCode.PERMISSION_NOT_FOUND));

        Access access = new Access();
        access.setStaff(staff);
        access.setPermission(permission);
//        access.setVersion(version);
        access.setDateTime(new Date());

        access = accessRepository.save(access);

        return accessMapper.toAccessCreateResponse(access);
    }


}
