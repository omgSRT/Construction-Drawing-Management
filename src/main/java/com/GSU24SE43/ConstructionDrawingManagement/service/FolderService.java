package com.GSU24SE43.ConstructionDrawingManagement.service;

import com.GSU24SE43.ConstructionDrawingManagement.Utils.PaginationUtils;
import com.GSU24SE43.ConstructionDrawingManagement.dto.request.FolderRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.request.FolderUpdateRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.FolderResponse;
import com.GSU24SE43.ConstructionDrawingManagement.entity.*;
import com.GSU24SE43.ConstructionDrawingManagement.exception.AppException;
import com.GSU24SE43.ConstructionDrawingManagement.exception.ErrorCode;
import com.GSU24SE43.ConstructionDrawingManagement.mapper.FolderMapper;
import com.GSU24SE43.ConstructionDrawingManagement.repository.FolderRepository;
import com.GSU24SE43.ConstructionDrawingManagement.repository.ProjectRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
@Service
public class FolderService {
    final FolderRepository folderRepository;
    final FolderMapper folderMapper;
    final ProjectRepository projectRepository;
    final PaginationUtils paginationUtils = new PaginationUtils();

    @PreAuthorize("hasRole('ADMIN') or hasAnyAuthority('HEAD_OF_ARCHITECTURAL_DESIGN_DEPARTMENT', 'HEAD_OF_STRUCTURAL_DESIGN_DEPARTMENT', 'HEAD_OF_MvE_DESIGN_DEPARTMENT', 'HEAD_OF_INTERIOR_DESIGN_DEPARTMENT')")
    public FolderResponse createFolder(FolderRequest request){
        if(folderRepository.existsByName(request.getName())){
            throw new AppException(ErrorCode.NAME_EXISTED);
        }
        Project project = projectRepository.findById(request.getProjectId())
                        .orElseThrow(() -> new AppException(ErrorCode.PROJECT_NOT_FOUND));

        Folder newFolder = folderMapper.toFolder(request);
        newFolder.setCreateDate(new Date());
        newFolder.setProject(project);

        return folderMapper.toFolderResponse(folderRepository.save(newFolder));
    }

    @PreAuthorize("hasRole('ADMIN') or hasAnyAuthority('HEAD_OF_ARCHITECTURAL_DESIGN_DEPARTMENT', 'HEAD_OF_STRUCTURAL_DESIGN_DEPARTMENT', 'HEAD_OF_MvE_DESIGN_DEPARTMENT', 'HEAD_OF_INTERIOR_DESIGN_DEPARTMENT')")
    public List<FolderResponse> getAllFolders(int page, int perPage) {
        try {
            List<FolderResponse> folderResponses
                    = folderRepository.findAll().stream().map(folderMapper::toFolderResponse).toList();
            folderResponses = paginationUtils.convertListToPage(page, perPage, folderResponses);
            return folderResponses;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PreAuthorize("hasRole('ADMIN') or hasAnyAuthority('HEAD_OF_ARCHITECTURAL_DESIGN_DEPARTMENT', 'HEAD_OF_STRUCTURAL_DESIGN_DEPARTMENT', 'HEAD_OF_MvE_DESIGN_DEPARTMENT', 'HEAD_OF_INTERIOR_DESIGN_DEPARTMENT')")
    public void deleteFolderById(UUID id){
        var folder = folderRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.FOLDER_NOT_FOUND));
        folderRepository.delete(folder);
    }

    @PreAuthorize("hasRole('ADMIN') or hasAnyAuthority('HEAD_OF_ARCHITECTURAL_DESIGN_DEPARTMENT', 'HEAD_OF_STRUCTURAL_DESIGN_DEPARTMENT', 'HEAD_OF_MvE_DESIGN_DEPARTMENT', 'HEAD_OF_INTERIOR_DESIGN_DEPARTMENT')")
    public FolderResponse findFolderById(UUID id){
        return folderMapper.toFolderResponse(folderRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.FOLDER_NOT_FOUND)));
    }

    @PreAuthorize("hasRole('ADMIN') or hasAnyAuthority('HEAD_OF_ARCHITECTURAL_DESIGN_DEPARTMENT', 'HEAD_OF_STRUCTURAL_DESIGN_DEPARTMENT', 'HEAD_OF_MvE_DESIGN_DEPARTMENT', 'HEAD_OF_INTERIOR_DESIGN_DEPARTMENT')")
    public FolderResponse updateFolderById(UUID id, FolderUpdateRequest request){
        var folder = folderRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.FOLDER_NOT_FOUND));

        folderMapper.updateFolder(folder, request);

        return folderMapper.toFolderResponse(folderRepository.save(folder));
    }

    @PreAuthorize("hasRole('ADMIN') or hasAnyAuthority('HEAD_OF_ARCHITECTURAL_DESIGN_DEPARTMENT', 'HEAD_OF_STRUCTURAL_DESIGN_DEPARTMENT', 'HEAD_OF_MvE_DESIGN_DEPARTMENT', 'HEAD_OF_INTERIOR_DESIGN_DEPARTMENT', 'DESIGNER', 'COMMANDER')")
    public List<FolderResponse> findFolderByNameContaining(String name, int page, int perPage){
        try {
            List<FolderResponse> folderResponses
                    = folderRepository.findByNameContaining(name).stream().map(folderMapper::toFolderResponse).toList();
            folderResponses = paginationUtils.convertListToPage(page, perPage, folderResponses);
            return folderResponses;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PreAuthorize("hasRole('ADMIN') or hasAnyAuthority('HEAD_OF_ARCHITECTURAL_DESIGN_DEPARTMENT', 'HEAD_OF_STRUCTURAL_DESIGN_DEPARTMENT', 'HEAD_OF_MvE_DESIGN_DEPARTMENT', 'HEAD_OF_INTERIOR_DESIGN_DEPARTMENT', 'DESIGNER', 'COMMANDER')")
    public List<FolderResponse> getFoldersByProjectId(int page, int perPage, UUID projectId){
        try {
            List<FolderResponse> folderResponses
                    = folderRepository.findByProjectId(projectId).stream().map(folderMapper::toFolderResponse).toList();
            folderResponses = paginationUtils.convertListToPage(page, perPage, folderResponses);
            return folderResponses;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
