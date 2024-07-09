package com.GSU24SE43.ConstructionDrawingManagement.service;

import com.GSU24SE43.ConstructionDrawingManagement.Utils.PaginationUtils;
import com.GSU24SE43.ConstructionDrawingManagement.dto.request.*;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.DrawingResponse;
import com.GSU24SE43.ConstructionDrawingManagement.entity.Drawing;
import com.GSU24SE43.ConstructionDrawingManagement.entity.Folder;
import com.GSU24SE43.ConstructionDrawingManagement.entity.Task;
import com.GSU24SE43.ConstructionDrawingManagement.enums.DrawingStatus;
import com.GSU24SE43.ConstructionDrawingManagement.enums.ProjectStatus;
import com.GSU24SE43.ConstructionDrawingManagement.exception.AppException;
import com.GSU24SE43.ConstructionDrawingManagement.exception.ErrorCode;
import com.GSU24SE43.ConstructionDrawingManagement.mapper.DrawingMapper;
import com.GSU24SE43.ConstructionDrawingManagement.repository.DrawingRepository;
import com.GSU24SE43.ConstructionDrawingManagement.repository.FolderRepository;
import com.GSU24SE43.ConstructionDrawingManagement.repository.TaskRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class DrawingService {
    DrawingRepository drawingRepository;
    DrawingMapper drawingMapper;
    FolderRepository folderRepository;
    TaskRepository taskRepository;
    PaginationUtils paginationUtils = new PaginationUtils();

    @PreAuthorize("hasRole('ADMIN') or hasAnyAuthority('HEAD_OF_ARCHITECTURAL_DESIGN_DEPARTMENT', 'HEAD_OF_STRUCTURAL_DESIGN_DEPARTMENT', 'HEAD_OF_MvE_DESIGN_DEPARTMENT', 'HEAD_OF_INTERIOR_DESIGN_DEPARTMENT', 'DESIGNER')")
    public DrawingResponse createDrawing(DrawingRequest request){
        Folder folder = folderRepository.findById(request.getFolderId())
                .orElseThrow(() -> new AppException(ErrorCode.FOLDER_NOT_FOUND));
        Task task = taskRepository.findById(request.getTaskId())
                .orElseThrow(() -> new AppException(ErrorCode.TASK_NOT_FOUND));

        Drawing newDrawing = drawingMapper.toDrawing(request);
        newDrawing.setFolder(folder);
        newDrawing.setTask(task);
        newDrawing.setStatus(DrawingStatus.PROCESSING.name());

        return drawingMapper.toDrawingResponse(drawingRepository.save(newDrawing));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<DrawingResponse> getAllDrawings(int page, int perPage, String status){
        try {
            List<DrawingResponse> drawingResponses;
            if(!status.isBlank()){
                DrawingStatus drawingStatus;
                status = status.toUpperCase();
                try {
                    drawingStatus = DrawingStatus.valueOf(status);
                } catch (IllegalArgumentException e) {
                    throw new AppException(ErrorCode.INVALID_STATUS);
                }
                drawingResponses = drawingRepository.findByStatus(status.toUpperCase())
                        .stream().map(drawingMapper::toDrawingResponse).toList();
            }
            else{
                drawingResponses = drawingRepository.findAll().stream().map(drawingMapper::toDrawingResponse).toList();
            }

            drawingResponses = paginationUtils.convertListToPage(page, perPage, drawingResponses);
            return drawingResponses;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PreAuthorize("hasRole('ADMIN') or hasAnyAuthority('HEAD_OF_ARCHITECTURAL_DESIGN_DEPARTMENT', 'HEAD_OF_STRUCTURAL_DESIGN_DEPARTMENT', 'HEAD_OF_MvE_DESIGN_DEPARTMENT', 'HEAD_OF_INTERIOR_DESIGN_DEPARTMENT', 'DESIGNER', 'COMMANDER')")
    public List<DrawingResponse> findDrawingsByNameContainingAndStatus(String name, String status, int page, int perPage){
        try {
            List<DrawingResponse> drawingResponses;
            if(!status.isBlank()){
                DrawingStatus drawingStatus;
                status = status.toUpperCase();
                try {
                    drawingStatus = DrawingStatus.valueOf(status);
                } catch (IllegalArgumentException e) {
                    throw new AppException(ErrorCode.INVALID_STATUS);
                }
                drawingResponses = drawingRepository.findByNameContainingAndStatus(name, status)
                        .stream().map(drawingMapper::toDrawingResponse).toList();
            }
            else{
                drawingResponses
                        = drawingRepository.findByNameContaining(name).stream().map(drawingMapper::toDrawingResponse).toList();
            }

            drawingResponses = paginationUtils.convertListToPage(page, perPage, drawingResponses);
            return drawingResponses;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PreAuthorize("hasRole('ADMIN') or hasAnyAuthority('HEAD_OF_ARCHITECTURAL_DESIGN_DEPARTMENT', 'HEAD_OF_STRUCTURAL_DESIGN_DEPARTMENT', 'HEAD_OF_MvE_DESIGN_DEPARTMENT', 'HEAD_OF_INTERIOR_DESIGN_DEPARTMENT', 'DESIGNER', 'COMMANDER')")
    public List<DrawingResponse> findDrawingsByFolderAndStatus(DrawingSearchByFolderRequest request, int page, int perPage){
        try {
            Folder folder = folderRepository.findById(request.getFolderId())
                    .orElseThrow(() -> new AppException(ErrorCode.FOLDER_NOT_FOUND));

            List<DrawingResponse> drawingResponses;
            String status = request.getStatus();
            if(!status.isBlank()){
                DrawingStatus drawingStatus;
                status = status.toUpperCase();
                try {
                    drawingStatus = DrawingStatus.valueOf(status);
                } catch (IllegalArgumentException e) {
                    throw new AppException(ErrorCode.INVALID_STATUS);
                }
                drawingResponses = drawingRepository.findByFolderAndStatus(folder, status)
                        .stream().map(drawingMapper::toDrawingResponse).toList();
            }
            else{
                drawingResponses
                        = drawingRepository.findByFolder(folder).stream().map(drawingMapper::toDrawingResponse).toList();
            }

            drawingResponses = paginationUtils.convertListToPage(page, perPage, drawingResponses);
            return drawingResponses;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PreAuthorize("hasRole('ADMIN') or hasAnyAuthority('HEAD_OF_ARCHITECTURAL_DESIGN_DEPARTMENT', 'HEAD_OF_STRUCTURAL_DESIGN_DEPARTMENT', 'HEAD_OF_MvE_DESIGN_DEPARTMENT', 'HEAD_OF_INTERIOR_DESIGN_DEPARTMENT')")
    public void deleteDrawingById(UUID id){
        var drawing = drawingRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PERMISSION_NOT_FOUND));
        drawingRepository.delete(drawing);
    }

    @PreAuthorize("hasRole('ADMIN') or hasAnyAuthority('HEAD_OF_ARCHITECTURAL_DESIGN_DEPARTMENT', 'HEAD_OF_STRUCTURAL_DESIGN_DEPARTMENT', 'HEAD_OF_MvE_DESIGN_DEPARTMENT', 'HEAD_OF_INTERIOR_DESIGN_DEPARTMENT', 'DESIGNER', 'COMMANDER')")
    public DrawingResponse findDrawingById(UUID id){
        return drawingMapper.toDrawingResponse(drawingRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.FOLDER_NOT_FOUND)));
    }

    @PreAuthorize("hasRole('ADMIN') or hasAnyAuthority('HEAD_OF_ARCHITECTURAL_DESIGN_DEPARTMENT', 'HEAD_OF_STRUCTURAL_DESIGN_DEPARTMENT', 'HEAD_OF_MvE_DESIGN_DEPARTMENT', 'HEAD_OF_INTERIOR_DESIGN_DEPARTMENT', 'DESIGNER')")
    public DrawingResponse updateDrawingById(UUID drawingId, DrawingUpdateRequest request){
        var drawing = drawingRepository.findById(drawingId)
                        .orElseThrow(() -> new AppException(ErrorCode.DRAWING_NOT_FOUND));

        drawingMapper.updateDrawing(drawing, request);

        return drawingMapper.toDrawingResponse(drawingRepository.save(drawing));
    }

    @PreAuthorize("hasRole('ADMIN') or hasAnyAuthority('HEAD_OF_ARCHITECTURAL_DESIGN_DEPARTMENT', 'HEAD_OF_STRUCTURAL_DESIGN_DEPARTMENT', 'HEAD_OF_MvE_DESIGN_DEPARTMENT', 'HEAD_OF_INTERIOR_DESIGN_DEPARTMENT')")
    public DrawingResponse changeDrawingStatus(DrawingStatusChangeRequest request){
        DrawingStatus drawingStatus;
        String status = request.getStatus().toUpperCase();
        try {
            drawingStatus = DrawingStatus.valueOf(status);
        } catch (IllegalArgumentException e) {
            throw new AppException(ErrorCode.INVALID_STATUS);
        }

        var drawing = drawingRepository.findById(request.getDrawingId())
                .orElseThrow(() -> new AppException(ErrorCode.DRAWING_NOT_FOUND));
        drawing.setStatus(status);

        return drawingMapper.toDrawingResponse(drawingRepository.save(drawing));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public DrawingResponse changeDrawingToInactiveStatus(UUID drawingId){
        var drawing = drawingRepository.findById(drawingId)
                .orElseThrow(() -> new AppException(ErrorCode.DRAWING_NOT_FOUND));
        drawing.setStatus(ProjectStatus.INACTIVE.name());

        return drawingMapper.toDrawingResponse(drawingRepository.save(drawing));
    }
}
