package com.GSU24SE43.ConstructionDrawingManagement.service;

import com.GSU24SE43.ConstructionDrawingManagement.Utils.PaginationUtils;
import com.GSU24SE43.ConstructionDrawingManagement.dto.request.VersionCreateRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.request.VersionUpdateRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.VersionResponse;
import com.GSU24SE43.ConstructionDrawingManagement.entity.*;
import com.GSU24SE43.ConstructionDrawingManagement.enums.VersionStatus;
import com.GSU24SE43.ConstructionDrawingManagement.exception.AppException;
import com.GSU24SE43.ConstructionDrawingManagement.exception.ErrorCode;
import com.GSU24SE43.ConstructionDrawingManagement.mapper.VersionMapper;
import com.GSU24SE43.ConstructionDrawingManagement.repository.DepartmentRepository;
import com.GSU24SE43.ConstructionDrawingManagement.repository.DrawingRepository;
import com.GSU24SE43.ConstructionDrawingManagement.repository.TaskRepository;
import com.GSU24SE43.ConstructionDrawingManagement.repository.VersionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
@Service
public class VersionService {
    final VersionRepository versionRepository;
    final VersionMapper versionMapper;
    final DrawingRepository drawingRepository;
    final DepartmentRepository departmentRepository;
    final TaskRepository taskRepository;
    final PaginationUtils paginationUtils = new PaginationUtils();

    public VersionResponse createVersion(VersionCreateRequest request) {
        Drawing drawing = drawingRepository.findById(request.getDrawingId())
                .orElseThrow(() -> new AppException(ErrorCode.DRAWING_NOT_FOUND));

        List<Version> existingVersions = versionRepository.findByDrawingId(request.getDrawingId());
        String newVersionNumber = getNextVersionNumber(existingVersions);

        //set newest version url to drawing
        drawing.setUrl(request.getUrl());

        Version newVersion = versionMapper.toVersion(request);
        newVersion.setVersionNumber(newVersionNumber);
        newVersion.setUploadDate(new Date());
        newVersion.setStatus(VersionStatus.ACTIVE.name());
        newVersion.setDrawing(drawing);

        drawingRepository.save(drawing);
        return versionMapper.toVersionResponse(versionRepository.save(newVersion));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<VersionResponse> getAllVersionsByStatus(int page, int perPage, VersionStatus status) {
        String stringStatus = status.name();
        var versionResponses = versionRepository.findByStatus(stringStatus).stream()
                .map(versionMapper::toVersionResponse).toList();
        versionResponses = paginationUtils.convertListToPage(page, perPage, versionResponses);
        return versionResponses;
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<VersionResponse> getAllVersions(int page, int perPage) {
        var versionResponses = versionRepository.findAll().stream()
                .map(versionMapper::toVersionResponse).toList();
        versionResponses = paginationUtils.convertListToPage(page, perPage, versionResponses);
        return versionResponses;
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('HEAD_OF_ARCHITECTURAL_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_STRUCTURAL_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_MvE_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_INTERIOR_DESIGN_DEPARTMENT') or hasRole('DESIGNER') or hasRole('COMMANDER')")
    public List<VersionResponse> getAllVersionsByDrawingIdAndStatus(int page, int perPage, VersionStatus status, UUID drawingId) {
        Drawing drawing = drawingRepository.findById(drawingId)
                .orElseThrow(() -> new AppException(ErrorCode.DRAWING_NOT_FOUND));

        String stringStatus = status.name();
        var versionResponses = versionRepository.findByDrawingIdAndStatus(drawingId, stringStatus).stream()
                .map(versionMapper::toVersionResponse).toList();
        versionResponses = paginationUtils.convertListToPage(page, perPage, versionResponses);
        return versionResponses;
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('HEAD_OF_ARCHITECTURAL_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_STRUCTURAL_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_MvE_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_INTERIOR_DESIGN_DEPARTMENT') or hasRole('DESIGNER') or hasRole('COMMANDER')")
    public List<VersionResponse> getAllVersionsByDrawingId(int page, int perPage, UUID drawingId) {
        Drawing drawing = drawingRepository.findById(drawingId)
                .orElseThrow(() -> new AppException(ErrorCode.DRAWING_NOT_FOUND));

        var versionResponses = versionRepository.findByDrawingId(drawingId).stream()
                .map(versionMapper::toVersionResponse).toList();
        versionResponses = paginationUtils.convertListToPage(page, perPage, versionResponses);
        return versionResponses;
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteVersionById(UUID versionId){
        var version = versionRepository.findById(versionId)
                .orElseThrow(() -> new AppException(ErrorCode.VERSION_NOT_FOUND));
        versionRepository.delete(version);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public VersionResponse updateVersionById(UUID versionId, VersionUpdateRequest request){
        var version = versionRepository.findById(versionId)
                .orElseThrow(() -> new AppException(ErrorCode.VERSION_NOT_FOUND));
        versionMapper.updateVersion(version, request);

        return versionMapper.toVersionResponse(versionRepository.save(version));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public VersionResponse findVersionById(UUID versionId){
        return versionMapper.toVersionResponse(versionRepository.findById(versionId)
                .orElseThrow(() -> new AppException(ErrorCode.VERSION_NOT_FOUND)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public VersionResponse changeVersionStatus(UUID versionId){
        var version = versionRepository.findById(versionId)
                .orElseThrow(() -> new AppException(ErrorCode.VERSION_NOT_FOUND));
        version.setStatus(
                VersionStatus.ACTIVE.name().equalsIgnoreCase(version.getStatus())
                        ? VersionStatus.INACTIVE.name()
                        : VersionStatus.ACTIVE.name()
        );

        return versionMapper.toVersionResponse(versionRepository.save(version));
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('HEAD_OF_ARCHITECTURAL_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_STRUCTURAL_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_MvE_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_INTERIOR_DESIGN_DEPARTMENT') or hasRole('DESIGNER') or hasRole('COMMANDER')")
    public List<VersionResponse> getAllVersionsDepartmentIdAndStatus(int page, int perPage, UUID departmentId, VersionStatus status) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new AppException(ErrorCode.DEPARTMENT_NOT_FOUND));

        String stringStatus = status.name();
        var versionResponses = versionRepository.findByDrawingTaskDepartmentAndStatus(department, stringStatus).stream()
                .map(versionMapper::toVersionResponse).toList();
        versionResponses = paginationUtils.convertListToPage(page, perPage, versionResponses);
        return versionResponses;
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('HEAD_OF_ARCHITECTURAL_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_STRUCTURAL_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_MvE_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_INTERIOR_DESIGN_DEPARTMENT') or hasRole('DESIGNER') or hasRole('COMMANDER')")
    public List<VersionResponse> getAllVersionsByDepartmentId(int page, int perPage, UUID departmentId) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new AppException(ErrorCode.DEPARTMENT_NOT_FOUND));

        var versionResponses = versionRepository.findByDrawingTaskDepartment(department).stream()
                .map(versionMapper::toVersionResponse).toList();
        versionResponses = paginationUtils.convertListToPage(page, perPage, versionResponses);
        return versionResponses;
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('HEAD_OF_ARCHITECTURAL_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_STRUCTURAL_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_MvE_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_INTERIOR_DESIGN_DEPARTMENT') or hasRole('DESIGNER') or hasRole('COMMANDER')")
    public List<VersionResponse> getAllVersionsTaskIdAndStatus(int page, int perPage, UUID taskId, VersionStatus status) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new AppException(ErrorCode.TASK_NOT_FOUND));

        String stringStatus = status.name();
        var versionResponses = versionRepository.findByDrawingTaskAndStatus(task, stringStatus).stream()
                .map(versionMapper::toVersionResponse).toList();
        versionResponses = paginationUtils.convertListToPage(page, perPage, versionResponses);
        return versionResponses;
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('HEAD_OF_ARCHITECTURAL_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_STRUCTURAL_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_MvE_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_INTERIOR_DESIGN_DEPARTMENT') or hasRole('DESIGNER') or hasRole('COMMANDER')")
    public List<VersionResponse> getAllVersionsByTaskId(int page, int perPage, UUID taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new AppException(ErrorCode.TASK_NOT_FOUND));

        var versionResponses = versionRepository.findByDrawingTask(task).stream()
                .map(versionMapper::toVersionResponse).toList();
        versionResponses = paginationUtils.convertListToPage(page, perPage, versionResponses);
        return versionResponses;
    }

    private String getNextVersionNumber(List<Version> existingVersions) {
        if (existingVersions.isEmpty()) {
            return "0.0.1";
        }

        // Find the latest version number
        Version latestVersion = existingVersions.stream()
                .max(Comparator.comparing(Version::getVersionNumber))
                .orElseThrow(() -> new AppException(ErrorCode.VERSION_NOT_FOUND));

        // Increment the version number
        String[] versionParts = latestVersion.getVersionNumber().split("\\.");
        int major = Integer.parseInt(versionParts[0]);
        int minor = Integer.parseInt(versionParts[1]);
        int patch = Integer.parseInt(versionParts[2]);

        patch++;
        if (patch == 10) {
            patch = 0;
            minor++;
            if (minor == 10) {
                minor = 0;
                major++;
            }
        }

        return String.format("%d.%d.%d", major, minor, patch);
    }
}
