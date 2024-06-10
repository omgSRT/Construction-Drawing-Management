package com.GSU24SE43.ConstructionDrawingManagement.service;

import com.GSU24SE43.ConstructionDrawingManagement.Utils.PaginationUtils;
import com.GSU24SE43.ConstructionDrawingManagement.dto.request.PermissionRequest;
import com.GSU24SE43.ConstructionDrawingManagement.entity.Permission;
import com.GSU24SE43.ConstructionDrawingManagement.exception.AppException;
import com.GSU24SE43.ConstructionDrawingManagement.exception.ErrorCode;
import com.GSU24SE43.ConstructionDrawingManagement.mapper.PermissionMapper;
import com.GSU24SE43.ConstructionDrawingManagement.repository.PermissionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
@Service
public class PermissionService {
    final PermissionRepository permissionRepository;
    final PermissionMapper permissionMapper;
    final PaginationUtils paginationUtils = new PaginationUtils();

    public Permission createPermission(PermissionRequest request){
        if(permissionRepository.existsByName(request.getName())){
            throw new AppException(ErrorCode.NAME_EXISTED);
        }

        Permission newPermission = permissionMapper.toPermission(request);

        return permissionRepository.save(newPermission);
    }

    public List<Permission> getAllPermissions(int page, int perPage){
        try {
            return paginationUtils.convertListToPage(page, perPage, permissionRepository.findAll());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void deletePermissionById(UUID id){
        var folder = permissionRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PERMISSION_NOT_FOUND));
        permissionRepository.delete(folder);
    }

    public Permission findPermissionById(UUID id){
        return permissionRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.FOLDER_NOT_FOUND));
    }

    public Permission updatePermissionById(UUID id, PermissionRequest request){
        var permission = permissionRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PERMISSION_NOT_FOUND));

        permissionMapper.updatePermission(permission, request);

        return permissionRepository.save(permission);
    }
}
