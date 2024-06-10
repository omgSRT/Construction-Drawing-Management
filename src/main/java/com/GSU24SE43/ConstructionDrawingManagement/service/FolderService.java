package com.GSU24SE43.ConstructionDrawingManagement.service;

import com.GSU24SE43.ConstructionDrawingManagement.Utils.PaginationUtils;
import com.GSU24SE43.ConstructionDrawingManagement.dto.request.FolderRequest;
import com.GSU24SE43.ConstructionDrawingManagement.entity.Folder;
import com.GSU24SE43.ConstructionDrawingManagement.exception.AppException;
import com.GSU24SE43.ConstructionDrawingManagement.exception.ErrorCode;
import com.GSU24SE43.ConstructionDrawingManagement.mapper.FolderMapper;
import com.GSU24SE43.ConstructionDrawingManagement.repository.FolderRepository;
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
public class FolderService {
    final FolderRepository folderRepository;
    final FolderMapper folderMapper;
    final PaginationUtils paginationUtils = new PaginationUtils();

    //@PreAuthorize("hasRole('ADMIN')")
    public Folder createFolder(FolderRequest request){
        if(folderRepository.existsByName(request.getName())){
            throw new AppException(ErrorCode.NAME_EXISTED);
        }

        Folder newFolder = folderMapper.toFolder(request);
        newFolder.setCreationDate(new Date());
        folderRepository.save(newFolder);

        return newFolder;
    }

    public List<Folder> getAllFolders(int page, int perPage) {
        try {
            return paginationUtils.convertListToPage(page, perPage, folderRepository.findAll());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
    public Folder updateFolderById(UUID id, FolderRequest request){
        var folder = folderRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.FOLDER_NOT_FOUND));

        folderMapper.updateFolder(folder, request);

        return folderRepository.save(folder);
    }

    public List<Folder> findFolderByNameContaining(String name, int page, int perPage){
        try {
            return paginationUtils.convertListToPage(page, perPage, folderRepository.findByNameContaining(name));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
