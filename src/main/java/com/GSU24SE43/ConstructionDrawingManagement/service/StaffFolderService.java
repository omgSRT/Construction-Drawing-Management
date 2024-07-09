package com.GSU24SE43.ConstructionDrawingManagement.service;

import com.GSU24SE43.ConstructionDrawingManagement.dto.request.DetailTaskUpdateRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.request.StaffFolderCreateRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.request.StaffFolderUpdateRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.DetailTaskUpdateResponse;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.StaffFolderCreateResponse;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.StaffFolderUpdateResponse;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.StaffFolderViewResponse;
import com.GSU24SE43.ConstructionDrawingManagement.entity.*;
import com.GSU24SE43.ConstructionDrawingManagement.exception.AppException;
import com.GSU24SE43.ConstructionDrawingManagement.exception.ErrorCode;
import com.GSU24SE43.ConstructionDrawingManagement.mapper.StaffFolderMapper;
import com.GSU24SE43.ConstructionDrawingManagement.repository.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StaffFolderService {
    StaffFolderRepository staffFolderRepository;
    StaffRepository staffRepository;
    FolderRepository folderRepository;
    StaffFolderMapper staffFolderMapper;

    public StaffFolderCreateResponse createStaffFolder(StaffFolderCreateRequest request){
        Staff staff = staffRepository.findById(request.getStaffId()).orElseThrow(
                () -> new AppException(ErrorCode.STAFF_NOT_FOUND));
        Folder folder = folderRepository.findById(request.getFolderId()).orElseThrow(
                () -> new AppException(ErrorCode.FOLDER_NOT_FOUND));
        StaffFolder staffFolder = new StaffFolder();
        staffFolder.setStaff(staff);
        staffFolder.setFolder(folder);
        staffFolder.setPermissions(request.getPermissions());
        staffFolderRepository.save(staffFolder);
        return staffFolderMapper.toResponse(staffFolder);

    }

    public StaffFolderUpdateResponse staffFolderUpdate(UUID staffFolderId, StaffFolderUpdateRequest request){
            StaffFolder staffFolder = staffFolderRepository.findById(staffFolderId).orElseThrow(
                    () -> new AppException(ErrorCode.STAFF_FOLDER_NOT_FOUND));
            Staff staff = staffRepository.findById(request.getStaffId()).orElseThrow(
                    () -> new AppException(ErrorCode.STAFF_NOT_FOUND));
            Folder folder = folderRepository.findById(request.getFolderId()).orElseThrow(
                    () -> new AppException(ErrorCode.FOLDER_NOT_FOUND));
            staffFolder.setStaff(staff);
            staffFolder.setFolder(folder);
            staffFolder.setPermissions(request.getPermissions());
            staffFolderRepository.save(staffFolder);

            return staffFolderMapper.toUpdateResponse(staffFolder);
    }

    public List<StaffFolderViewResponse> getAll(){
        return staffFolderRepository.findAll().stream().map(staffFolderMapper::toStaffFolderViewResponse).toList();
    }

    public void delete(UUID staffFolderId){
        staffFolderRepository.deleteById(staffFolderId);
    }


}
