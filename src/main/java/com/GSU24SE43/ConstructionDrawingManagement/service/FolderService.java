package com.GSU24SE43.ConstructionDrawingManagement.service;

import com.GSU24SE43.ConstructionDrawingManagement.dto.request.FolderRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.FolderResponse;
import com.GSU24SE43.ConstructionDrawingManagement.entity.Folder;
import com.GSU24SE43.ConstructionDrawingManagement.exception.AppException;
import com.GSU24SE43.ConstructionDrawingManagement.exception.ErrorCode;
import com.GSU24SE43.ConstructionDrawingManagement.mapper.FolderMapper;
import com.GSU24SE43.ConstructionDrawingManagement.repository.FolderRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
@Service
public class FolderService {
    @Autowired
    FolderRepository folderRepository;
    FolderMapper folderMapper;

    //@PreAuthorize("hasRole('ADMIN')")
    public FolderResponse createFolder(FolderRequest request){
        Folder folder = folderRepository.findByName(request.getName())
                .orElseThrow(() -> new AppException(ErrorCode.NAME_EXISTED));

        Folder newFolder = folderMapper.toFolder(request);
        newFolder.setCreationDate(new Date());

        return folderMapper.toFolderReponse(newFolder);
    }

    public List<FolderResponse> getAllFolders() {
        return folderRepository.findAll().stream().map(folderMapper::toFolderReponse).toList();
    }

    //@PreAuthorize("hasRole('ADMIN')")
    public void deleteFolderById(UUID id){
        var folder = folderRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.FOLDER_NOT_FOUND));
        folderRepository.delete(folder);
    }

    //@PreAuthorize("hasRole('ADMIN')")
    public Folder findFolderById(UUID id){
        return folderRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.FOLDER_NOT_FOUND));
    }

    //@PreAuthorize("hasRole('ADMIN')")
    public FolderResponse updateFolderById(UUID id, FolderRequest request){
        var folder = folderRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.FOLDER_NOT_FOUND));

        folderMapper.updateFolder(folder, request);

        return folderMapper.toFolderReponse(folderRepository.save(folder));
    }
}
