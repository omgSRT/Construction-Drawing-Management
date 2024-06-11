package com.GSU24SE43.ConstructionDrawingManagement.service;

import com.GSU24SE43.ConstructionDrawingManagement.Utils.PaginationUtils;
import com.GSU24SE43.ConstructionDrawingManagement.dto.request.ProjectRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.request.ProjectUpdateRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.request.SubfolderRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.request.SubfolderUpdateRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.ProjectResponse;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.SubfolderResponse;
import com.GSU24SE43.ConstructionDrawingManagement.entity.*;
import com.GSU24SE43.ConstructionDrawingManagement.enums.ProjectStatus;
import com.GSU24SE43.ConstructionDrawingManagement.exception.AppException;
import com.GSU24SE43.ConstructionDrawingManagement.exception.ErrorCode;
import com.GSU24SE43.ConstructionDrawingManagement.mapper.SubfolderMapper;
import com.GSU24SE43.ConstructionDrawingManagement.repository.ProjectRepository;
import com.GSU24SE43.ConstructionDrawingManagement.repository.SubfolderRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
@Service
public class SubfolderService {
    final SubfolderRepository subfolderRepository;
    final SubfolderMapper subfolderMapper;
    final ProjectRepository projectRepository;
    final PaginationUtils paginationUtils = new PaginationUtils();

    public SubfolderResponse createSubfolder(SubfolderRequest request){
        if(subfolderRepository.existsByName(request.getName())){
            throw new AppException(ErrorCode.NAME_EXISTED);
        }
        Project project = projectRepository.findById(request.getProjectId())
                        .orElseThrow(() -> new AppException(ErrorCode.PROJECT_NOT_FOUND));

        Subfolder newSubfolder = subfolderMapper.toSubfolder(request);
        newSubfolder.setCreateDate(new Date());
        newSubfolder.setProject(project);

        return subfolderMapper.toSubfolderResponse(subfolderRepository.save(newSubfolder));
    }

    public List<SubfolderResponse> getAllSubfolders(int page, int perPage) {
        try {
            List<SubfolderResponse> subfolderResponses
                    = subfolderRepository.findAll().stream().map(subfolderMapper::toSubfolderResponse).toList();
            subfolderResponses = paginationUtils.convertListToPage(page, perPage, subfolderResponses);
            return subfolderResponses;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Subfolder> getAllSubfolders() {
        return subfolderRepository.findAll();
    }

    public void deleteSubfolderById(UUID id){
        var subfolder = subfolderRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.SUBFOLDER_NOT_FOUND));
        subfolderRepository.delete(subfolder);
    }

    public SubfolderResponse findSubfolderById(UUID id){
        return subfolderMapper.toSubfolderResponse(subfolderRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.SUBFOLDER_NOT_FOUND)));
    }

    public SubfolderResponse updateSubfolderById(UUID id, SubfolderUpdateRequest request){
        var subfolder = subfolderRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.SUBFOLDER_NOT_FOUND));

        subfolderMapper.updateSubfolder(subfolder, request);

        return subfolderMapper.toSubfolderResponse(subfolderRepository.save(subfolder));
    }

    public List<SubfolderResponse> findSubfolderByNameContaining(String name, int page, int perPage){
        try {
            List<SubfolderResponse> subfolderResponses
                    = subfolderRepository.findByNameContaining(name).stream().map(subfolderMapper::toSubfolderResponse).toList();
            subfolderResponses = paginationUtils.convertListToPage(page, perPage, subfolderResponses);
            return subfolderResponses;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<SubfolderResponse> getSubfoldersByProjectId(int page, int perPage, UUID projectId){
        try {
            List<SubfolderResponse> subfolderResponses
                    = subfolderRepository.findByProjectId(projectId).stream().map(subfolderMapper::toSubfolderResponse).toList();
            subfolderResponses = paginationUtils.convertListToPage(page, perPage, subfolderResponses);
            return subfolderResponses;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
