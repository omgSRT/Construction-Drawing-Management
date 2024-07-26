package com.GSU24SE43.ConstructionDrawingManagement.service;

import com.GSU24SE43.ConstructionDrawingManagement.Utils.PaginationUtils;
import com.GSU24SE43.ConstructionDrawingManagement.dto.request.*;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.DrawingResponse;
import com.GSU24SE43.ConstructionDrawingManagement.entity.Drawing;
import com.GSU24SE43.ConstructionDrawingManagement.entity.Folder;
import com.GSU24SE43.ConstructionDrawingManagement.entity.Project;
import com.GSU24SE43.ConstructionDrawingManagement.entity.Task;
import com.GSU24SE43.ConstructionDrawingManagement.enums.DrawingStatus;
import com.GSU24SE43.ConstructionDrawingManagement.enums.ProjectStatus;
import com.GSU24SE43.ConstructionDrawingManagement.exception.AppException;
import com.GSU24SE43.ConstructionDrawingManagement.exception.ErrorCode;
import com.GSU24SE43.ConstructionDrawingManagement.mapper.DrawingMapper;
import com.GSU24SE43.ConstructionDrawingManagement.repository.DrawingRepository;
import com.GSU24SE43.ConstructionDrawingManagement.repository.FolderRepository;
import com.GSU24SE43.ConstructionDrawingManagement.repository.ProjectRepository;
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
    ProjectRepository projectRepository;
    PaginationUtils paginationUtils = new PaginationUtils();

    @PreAuthorize("hasRole('ADMIN') or hasRole('HEAD_OF_ARCHITECTURAL_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_STRUCTURAL_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_MvE_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_INTERIOR_DESIGN_DEPARTMENT') or hasRole('DESIGNER')")
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
    public List<DrawingResponse> getAllDrawingsByStatus(int page, int perPage, DrawingStatus status){
        String stringStatus = status.name();
        var drawingResponses = drawingRepository.findByStatus(stringStatus).stream()
                .map(drawingMapper::toDrawingResponse).toList();
        drawingResponses = paginationUtils.convertListToPage(page, perPage, drawingResponses);
        return drawingResponses;
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<DrawingResponse> getAllDrawings(int page, int perPage){
        var drawingResponses = drawingRepository.findAll().stream()
                .map(drawingMapper::toDrawingResponse).toList();
        drawingResponses = paginationUtils.convertListToPage(page, perPage, drawingResponses);
        return drawingResponses;
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('HEAD_OF_ARCHITECTURAL_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_STRUCTURAL_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_MvE_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_INTERIOR_DESIGN_DEPARTMENT') or hasRole('DESIGNER') or hasRole('COMMANDER')")
    public List<DrawingResponse> findDrawingsByNameContainingAndStatus(String name, DrawingStatus status, int page, int perPage){
        String stringStatus = status.name();
        var drawingResponses = drawingRepository.findByNameContainingAndStatus(name, stringStatus).stream()
                .map(drawingMapper::toDrawingResponse).toList();
        drawingResponses = paginationUtils.convertListToPage(page, perPage, drawingResponses);
        return drawingResponses;
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('HEAD_OF_ARCHITECTURAL_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_STRUCTURAL_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_MvE_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_INTERIOR_DESIGN_DEPARTMENT') or hasRole('DESIGNER') or hasRole('COMMANDER')")
    public List<DrawingResponse> findDrawingsByNameContaining(String name, int page, int perPage){
        var drawingResponses = drawingRepository.findByNameContaining(name).stream()
                .map(drawingMapper::toDrawingResponse).toList();
        drawingResponses = paginationUtils.convertListToPage(page, perPage, drawingResponses);
        return drawingResponses;
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('HEAD_OF_ARCHITECTURAL_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_STRUCTURAL_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_MvE_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_INTERIOR_DESIGN_DEPARTMENT') or hasRole('DESIGNER') or hasRole('COMMANDER')")
    public List<DrawingResponse> findDrawingsByFolderAndStatus(UUID folderId, DrawingStatus status, int page, int perPage){
        Folder folder = folderRepository.findById(folderId)
                .orElseThrow(() -> new AppException(ErrorCode.FOLDER_NOT_FOUND));

        String stringStatus = status.name();
        var drawingResponses = drawingRepository.findByFolderAndStatus(folder, stringStatus).stream()
                .map(drawingMapper::toDrawingResponse).toList();
        drawingResponses = paginationUtils.convertListToPage(page, perPage, drawingResponses);
        return drawingResponses;
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('HEAD_OF_ARCHITECTURAL_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_STRUCTURAL_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_MvE_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_INTERIOR_DESIGN_DEPARTMENT')")
    public List<DrawingResponse> findProcessingDrawingsByFolder(UUID folderId, int page, int perPage){
        Folder folder = folderRepository.findById(folderId)
                .orElseThrow(() -> new AppException(ErrorCode.FOLDER_NOT_FOUND));

        var drawingResponses = drawingRepository.findByFolderAndStatus(folder, DrawingStatus.PROCESSING.name()).stream()
                .map(drawingMapper::toDrawingResponse).toList();
        drawingResponses = paginationUtils.convertListToPage(page, perPage, drawingResponses);
        return drawingResponses;
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('HEAD_OF_ARCHITECTURAL_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_STRUCTURAL_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_MvE_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_INTERIOR_DESIGN_DEPARTMENT')")
    public List<DrawingResponse> findProcessingDrawingsByProject(UUID projectId, int page, int perPage){
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new AppException(ErrorCode.PROJECT_NOT_FOUND));

        var drawingResponses = drawingRepository.findByFolderProjectAndStatus(project, DrawingStatus.PROCESSING.name()).stream()
                .map(drawingMapper::toDrawingResponse).toList();
        drawingResponses = paginationUtils.convertListToPage(page, perPage, drawingResponses);
        return drawingResponses;
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('HEAD_OF_ARCHITECTURAL_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_STRUCTURAL_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_MvE_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_INTERIOR_DESIGN_DEPARTMENT')")
    public DrawingResponse approveDrawing(UUID drawingId){
        Drawing drawing = drawingRepository.findById(drawingId)
                .orElseThrow(() -> new AppException(ErrorCode.DRAWING_NOT_FOUND));
        drawing.setStatus(DrawingStatus.DONE.name());

        return drawingMapper.toDrawingResponse(drawingRepository.save(drawing));
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('HEAD_OF_ARCHITECTURAL_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_STRUCTURAL_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_MvE_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_INTERIOR_DESIGN_DEPARTMENT') or hasRole('DESIGNER') or hasRole('COMMANDER')")
    public List<DrawingResponse> findDrawingsByFolder(UUID folderId, int page, int perPage){
        Folder folder = folderRepository.findById(folderId)
                .orElseThrow(() -> new AppException(ErrorCode.FOLDER_NOT_FOUND));

        var drawingResponses = drawingRepository.findByFolder(folder).stream()
                .map(drawingMapper::toDrawingResponse).toList();
        drawingResponses = paginationUtils.convertListToPage(page, perPage, drawingResponses);
        return drawingResponses;
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('HEAD_OF_ARCHITECTURAL_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_STRUCTURAL_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_MvE_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_INTERIOR_DESIGN_DEPARTMENT')")
    public void deleteDrawingById(UUID id){
        var drawing = drawingRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PERMISSION_NOT_FOUND));
        drawingRepository.delete(drawing);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('HEAD_OF_ARCHITECTURAL_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_STRUCTURAL_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_MvE_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_INTERIOR_DESIGN_DEPARTMENT') or hasRole('DESIGNER') or hasRole('COMMANDER')")
    public DrawingResponse findDrawingById(UUID id){
        return drawingMapper.toDrawingResponse(drawingRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.FOLDER_NOT_FOUND)));
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('HEAD_OF_ARCHITECTURAL_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_STRUCTURAL_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_MvE_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_INTERIOR_DESIGN_DEPARTMENT') or hasRole('DESIGNER') or hasRole('COMMANDER')")
    public DrawingResponse findDrawingByIdAndStatus(UUID id, DrawingStatus status){
        String stringStatus = status.name();
        return drawingMapper.toDrawingResponse(drawingRepository.findByIdAndStatus(id, stringStatus)
                .orElseThrow(() -> new AppException(ErrorCode.FOLDER_NOT_FOUND)));
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('HEAD_OF_ARCHITECTURAL_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_STRUCTURAL_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_MvE_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_INTERIOR_DESIGN_DEPARTMENT') or hasRole('DESIGNER')")
    public DrawingResponse updateDrawingById(UUID drawingId, DrawingUpdateRequest request){
        var drawing = drawingRepository.findById(drawingId)
                        .orElseThrow(() -> new AppException(ErrorCode.DRAWING_NOT_FOUND));

        drawingMapper.updateDrawing(drawing, request);

        return drawingMapper.toDrawingResponse(drawingRepository.save(drawing));
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('HEAD_OF_ARCHITECTURAL_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_STRUCTURAL_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_MvE_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_INTERIOR_DESIGN_DEPARTMENT')")
    public DrawingResponse changeDrawingStatus(DrawingStatusChangeRequest request, DrawingStatus status){
        String stringStatus = status.name();
        var drawing = drawingRepository.findById(request.getDrawingId())
                .orElseThrow(() -> new AppException(ErrorCode.DRAWING_NOT_FOUND));
        drawing.setStatus(stringStatus);

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
