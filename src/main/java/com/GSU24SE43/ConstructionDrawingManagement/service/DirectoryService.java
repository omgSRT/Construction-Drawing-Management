package com.GSU24SE43.ConstructionDrawingManagement.service;

import com.GSU24SE43.ConstructionDrawingManagement.Utils.PaginationUtils;
import com.GSU24SE43.ConstructionDrawingManagement.dto.request.DirectoryRequest;
import com.GSU24SE43.ConstructionDrawingManagement.entity.Directory;
import com.GSU24SE43.ConstructionDrawingManagement.exception.AppException;
import com.GSU24SE43.ConstructionDrawingManagement.exception.ErrorCode;
import com.GSU24SE43.ConstructionDrawingManagement.mapper.DirectoryMapper;
import com.GSU24SE43.ConstructionDrawingManagement.repository.DirectoryRepository;
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
public class DirectoryService {
    final DirectoryRepository directoryRepository;
    final DirectoryMapper directoryMapper;
    final PaginationUtils paginationUtils = new PaginationUtils();

    //@PreAuthorize("hasRole('ADMIN')")
    public Directory createDirectory(DirectoryRequest request){
        if(directoryRepository.existsByName(request.getName())){
            throw new AppException(ErrorCode.NAME_EXISTED);
        }

        Directory newDirectory = directoryMapper.toFolder(request);
        newDirectory.setCreationDate(new Date());
        directoryRepository.save(newDirectory);

        return newDirectory;
    }

    public List<Directory> getAllDirectories(int page, int perPage) {
        try {
            return paginationUtils.convertListToPage(page, perPage, directoryRepository.findAll());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //@PreAuthorize("hasRole('ADMIN')")
    public void deleteDirectoryById(UUID id){
        var folder = directoryRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.FOLDER_NOT_FOUND));
        directoryRepository.delete(folder);
    }

    //@PreAuthorize("hasRole('ADMIN')")
    public Directory findDirectoryById(UUID id){
        return directoryRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.FOLDER_NOT_FOUND));
    }

    //@PreAuthorize("hasRole('ADMIN')")
    public Directory updateDirectoryById(UUID id, DirectoryRequest request){
        var folder = directoryRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.FOLDER_NOT_FOUND));

        directoryMapper.updateFolder(folder, request);

        return directoryRepository.save(folder);
    }

    public List<Directory> findDirectoryByNameContaining(String name, int page, int perPage){
        try {
            return paginationUtils.convertListToPage(page, perPage, directoryRepository.findByNameContaining(name));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
