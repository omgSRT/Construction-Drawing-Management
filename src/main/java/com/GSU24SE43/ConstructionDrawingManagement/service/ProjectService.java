package com.GSU24SE43.ConstructionDrawingManagement.service;

import com.GSU24SE43.ConstructionDrawingManagement.Utils.PaginationUtils;
import com.GSU24SE43.ConstructionDrawingManagement.dto.request.ProjectChangeStatusRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.request.ProjectRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.request.ProjectUpdateRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.ProjectResponse;
import com.GSU24SE43.ConstructionDrawingManagement.entity.Department;
import com.GSU24SE43.ConstructionDrawingManagement.entity.Project;
import com.GSU24SE43.ConstructionDrawingManagement.entity.Account;
import com.GSU24SE43.ConstructionDrawingManagement.enums.ProjectStatus;
import com.GSU24SE43.ConstructionDrawingManagement.enums.Role;
import com.GSU24SE43.ConstructionDrawingManagement.exception.AppException;
import com.GSU24SE43.ConstructionDrawingManagement.exception.ErrorCode;
import com.GSU24SE43.ConstructionDrawingManagement.mapper.ProjectMapper;
import com.GSU24SE43.ConstructionDrawingManagement.repository.*;
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
public class ProjectService {
    final ProjectRepository projectRepository;
    final ProjectMapper projectMapper;
    final AccountRepository accountRepository;
    final DepartmentRepository departmentRepository;
    final StaffRepository staffRepository;
    final PaginationUtils paginationUtils = new PaginationUtils();

    @PreAuthorize("hasRole('ADMIN')")
    public ProjectResponse createProject(ProjectRequest request){
        if(projectRepository.existsByName(request.getName())){
            throw new AppException(ErrorCode.NAME_EXISTED);
        }
        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new AppException(ErrorCode.DEPARTMENT_NOT_FOUND));
        Account account = null;
        if(request.getStaffId() == null && request.getAccountId() == null){
            throw new AppException(ErrorCode.STAFF_OR_ACCOUNT_NOT_FOUND);
        }
        if(request.getAccountId() != null){
            account = accountRepository.findById(request.getAccountId())
                    .orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));

            try {
                Role role = Role.valueOf(account.getRoleName());
                if(role != Role.ADMIN){
                    throw new AppException(ErrorCode.ONLY_ADMIN_CREATE_PROJECT);
                }
            } catch (IllegalArgumentException e) {
                throw new AppException(ErrorCode.INVALID_STATUS);
            }
        }

        ValidateProjectDate(request.getStartDate(), request.getEndDate());

        Project newProject = projectMapper.toProject(request);
        newProject.setCreationDate(new Date());
        newProject.setDepartment(department);
        newProject.setAccount(account);
        newProject.setStatus(ProjectStatus.ACTIVE.name());

        return projectMapper.toProjectResponse(projectRepository.save(newProject));
    }

    @PreAuthorize("hasRole('ADMIN') or hasAnyAuthority('HEAD_OF_ARCHITECTURAL_DESIGN_DEPARTMENT', 'HEAD_OF_STRUCTURAL_DESIGN_DEPARTMENT', 'HEAD_OF_MvE_DESIGN_DEPARTMENT', 'HEAD_OF_INTERIOR_DESIGN_DEPARTMENT', 'DESIGNER', 'COMMANDER')")
    public List<ProjectResponse> getAllProjects(int page, int perPage, String status) {
        try {
            List<ProjectResponse> projectResponses;
            if(status != null){
                ProjectStatus projectStatus;
                status = status.toUpperCase();
                try {
                    projectStatus = ProjectStatus.valueOf(status);
                } catch (IllegalArgumentException e) {
                    throw new AppException(ErrorCode.INVALID_STATUS);
                }
                projectResponses = projectRepository.findByStatus(status)
                        .stream().map(projectMapper::toProjectResponse).toList();
            }
            else{
                projectResponses = projectRepository.findAll().stream().map(projectMapper::toProjectResponse).toList();
            }

            projectResponses = paginationUtils.convertListToPage(page, perPage, projectResponses);
            return projectResponses;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteProjectById(UUID id){
        var project = projectRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PROJECT_NOT_FOUND));
        projectRepository.delete(project);
    }

    @PreAuthorize("hasRole('ADMIN') or hasAnyAuthority('HEAD_OF_ARCHITECTURAL_DESIGN_DEPARTMENT', 'HEAD_OF_STRUCTURAL_DESIGN_DEPARTMENT', 'HEAD_OF_MvE_DESIGN_DEPARTMENT', 'HEAD_OF_INTERIOR_DESIGN_DEPARTMENT')")
    public ProjectResponse findProjectByIdAndStatus(UUID id, String status){
        var project = projectRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PROJECT_NOT_FOUND));
        if(status != null && !project.getStatus().equals(status.toUpperCase())){
            project = null;
        }
        return projectMapper.toProjectResponse(project);
    }

    @PreAuthorize("hasRole('ADMIN') or hasAnyAuthority('HEAD_OF_ARCHITECTURAL_DESIGN_DEPARTMENT', 'HEAD_OF_STRUCTURAL_DESIGN_DEPARTMENT', 'HEAD_OF_MvE_DESIGN_DEPARTMENT', 'HEAD_OF_INTERIOR_DESIGN_DEPARTMENT')")
    public ProjectResponse findProjectById(UUID id){
        return projectMapper.toProjectResponse(projectRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PROJECT_NOT_FOUND)));
    }

    @PreAuthorize("hasRole('Admin')")
    public ProjectResponse updateProjectById(UUID id, ProjectUpdateRequest request){
        var project = projectRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PROJECT_NOT_FOUND));

        ValidateProjectDate(request.getStartDate(), request.getEndDate());

        projectMapper.updateProject(project, request);

        return projectMapper.toProjectResponse(projectRepository.save(project));
    }

    @PreAuthorize("hasRole('ADMIN') or hasAnyAuthority('HEAD_OF_ARCHITECTURAL_DESIGN_DEPARTMENT', 'HEAD_OF_STRUCTURAL_DESIGN_DEPARTMENT', 'HEAD_OF_MvE_DESIGN_DEPARTMENT', 'HEAD_OF_INTERIOR_DESIGN_DEPARTMENT', 'DESIGNER', 'COMMANDER')")
    public List<ProjectResponse> findProjectByNameContainingAndStatus(String name, String status, int page, int perPage){
        try {
            List<ProjectResponse> projectResponses;
            if(status != null){
                ProjectStatus projectStatus;
                status = status.toUpperCase();
                try {
                    projectStatus = ProjectStatus.valueOf(status);
                } catch (IllegalArgumentException e) {
                    throw new AppException(ErrorCode.INVALID_STATUS);
                }
                projectResponses = projectRepository.findByNameContainingAndStatus(name, status).stream().map(projectMapper::toProjectResponse).toList();
            }
            else{
                projectResponses
                        = projectRepository.findByNameContaining(name).stream().map(projectMapper::toProjectResponse).toList();
            }

            projectResponses = paginationUtils.convertListToPage(page, perPage, projectResponses);
            return projectResponses;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<ProjectResponse> findProjectByDepartmentNameAndStatus(String departmentName, String status,
                                                                      int page, int perPage){
        try {
            List<ProjectResponse> projectResponses;
            if(status != null){
                ProjectStatus projectStatus;
                status = status.toUpperCase();
                try {
                    projectStatus = ProjectStatus.valueOf(status);
                } catch (IllegalArgumentException e) {
                    throw new AppException(ErrorCode.INVALID_STATUS);
                }
                projectResponses = projectRepository.findByDepartmentNameAndStatus(departmentName, status)
                        .stream().map(projectMapper::toProjectResponse).toList();
            }
            else{
                projectResponses = projectRepository.findByDepartmentName(departmentName)
                        .stream().map(projectMapper::toProjectResponse).toList();
            }

            projectResponses = paginationUtils.convertListToPage(page, perPage, projectResponses);
            return projectResponses;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    public ProjectResponse changeProjectStatus(ProjectChangeStatusRequest request){
        ProjectStatus projectStatus;
        try {
            projectStatus = ProjectStatus.valueOf(request.getStatusName().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new AppException(ErrorCode.INVALID_STATUS);
        }

        var project = projectRepository.findById(request.getProjectId())
                .orElseThrow(() -> new AppException(ErrorCode.PROJECT_NOT_FOUND));

        project.setStatus(request.getStatusName().toUpperCase());

        return projectMapper.toProjectResponse(projectRepository.save(project));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public ProjectResponse changeProjectToInactiveStatus(UUID projectId){
        var project = projectRepository.findById(projectId)
                .orElseThrow(() -> new AppException(ErrorCode.PROJECT_NOT_FOUND));
        project.setStatus(ProjectStatus.INACTIVE.name());

        return projectMapper.toProjectResponse(projectRepository.save(project));
    }

    private void ValidateProjectDate(Date startDate, Date endDate){
        int result = startDate.compareTo(endDate);
        int result1 = startDate.compareTo(new Date());
        int result2 = endDate.compareTo(new Date());

        if(result >= 0){
            throw new AppException(ErrorCode.INVALID_CREATED_DATE_EARLIER_THAN_END_DATE);
        }
        if(result1 < 0){
            throw new AppException(ErrorCode.INVALID_CREATED_DATE_NOT_IN_FUTURE);
        }
        if(result2 <= 0){
            throw new AppException(ErrorCode.INVALID_END_DATE_NOT_IN_FUTURE);
        }
    }
}
