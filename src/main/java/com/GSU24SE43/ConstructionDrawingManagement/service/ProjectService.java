package com.GSU24SE43.ConstructionDrawingManagement.service;

import com.GSU24SE43.ConstructionDrawingManagement.Utils.PaginationUtils;
import com.GSU24SE43.ConstructionDrawingManagement.dto.request.ProjectChangeStatusRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.request.ProjectRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.request.ProjectUpdateRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.ProjectResponse;
import com.GSU24SE43.ConstructionDrawingManagement.entity.*;
import com.GSU24SE43.ConstructionDrawingManagement.enums.ProjectStatus;
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
    final DepartmentProjectRepository departmentProjectRepository;
    final PaginationUtils paginationUtils = new PaginationUtils();

    @PreAuthorize("hasRole('ADMIN')")
    public ProjectResponse createProject(ProjectRequest request) {
        if (projectRepository.existsByName(request.getName())) {
            throw new AppException(ErrorCode.NAME_EXISTED);
        }

        List<Department> departmentList = request.getDepartmentIds().stream()
                .map(departmentId -> departmentRepository.findById(departmentId)
                        .orElseThrow(() -> new AppException(ErrorCode.DEPARTMENT_NOT_FOUND)))
                .toList();

        Account account = accountRepository.findByUsername("admin")
                .orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));

        validateProjectDate(request.getStartDate(), request.getEndDate());

        Project project = projectMapper.toProject(request);
        project.setCreationDate(new Date());
        project.setAccount(account);
        project.setStatus(ProjectStatus.ACTIVE.name());

        Project newProject = projectRepository.save(project);
        newProject.setDepartmentProjects(
                departmentList.stream()
                        .map(department -> {
                            DepartmentProject newDepartmentProject = new DepartmentProject();
                            newDepartmentProject.setDepartment(department);
                            newDepartmentProject.setProject(newProject);
                            departmentProjectRepository.save(newDepartmentProject);
                            return newDepartmentProject;
                        })
                        .toList()
        );

        return projectMapper.toProjectResponse(newProject);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('HEAD_OF_ARCHITECTURAL_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_STRUCTURAL_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_MvE_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_INTERIOR_DESIGN_DEPARTMENT') or hasRole('DESIGNER') or hasRole('COMMANDER')")
    public List<ProjectResponse> getAllProjectsByStatus(int page, int perPage, ProjectStatus status) {
        String stringStatus = status.name();
        var projectResponses = projectRepository.findByStatus(stringStatus).stream()
                .map(projectMapper::toProjectResponse).toList();
        projectResponses = paginationUtils.convertListToPage(page, perPage, projectResponses);
        return projectResponses;
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('HEAD_OF_ARCHITECTURAL_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_STRUCTURAL_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_MvE_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_INTERIOR_DESIGN_DEPARTMENT') or hasRole('DESIGNER') or hasRole('COMMANDER')")
    public List<ProjectResponse> getAllProjects(int page, int perPage) {
        var projectResponses = projectRepository.findAll().stream()
                .map(projectMapper::toProjectResponse).toList();
        projectResponses = paginationUtils.convertListToPage(page, perPage, projectResponses);
        return projectResponses;
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteProjectById(UUID id){
        var project = projectRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PROJECT_NOT_FOUND));
        projectRepository.delete(project);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('HEAD_OF_ARCHITECTURAL_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_STRUCTURAL_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_MvE_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_INTERIOR_DESIGN_DEPARTMENT')")
    public ProjectResponse findProjectByIdAndStatus(UUID id, ProjectStatus status){
        String stringStatus = status.name();
        var project = projectRepository.findByIdAndStatus(id, stringStatus)
                .orElseThrow(() -> new AppException(ErrorCode.PROJECT_NOT_FOUND));
        return projectMapper.toProjectResponse(project);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('HEAD_OF_ARCHITECTURAL_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_STRUCTURAL_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_MvE_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_INTERIOR_DESIGN_DEPARTMENT')")
    public ProjectResponse findProjectById(UUID id){
        return projectMapper.toProjectResponse(projectRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PROJECT_NOT_FOUND)));
    }

    @PreAuthorize("hasRole('Admin')")
    public ProjectResponse updateProjectById(UUID id, ProjectUpdateRequest request){
        var project = projectRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PROJECT_NOT_FOUND));

        validateProjectDate(request.getStartDate(), request.getEndDate());

        projectMapper.updateProject(project, request);

        return projectMapper.toProjectResponse(projectRepository.save(project));
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('HEAD_OF_ARCHITECTURAL_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_STRUCTURAL_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_MvE_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_INTERIOR_DESIGN_DEPARTMENT') or hasRole('DESIGNER') or hasRole('COMMANDER')")
    public List<ProjectResponse> findProjectByNameContainingAndStatus(String name, ProjectStatus status, int page, int perPage){
        String stringStatus = status.name();
        var projectResponses = projectRepository.findByNameContainingAndStatus(name, stringStatus).stream()
                .map(projectMapper::toProjectResponse).toList();
        projectResponses = paginationUtils.convertListToPage(page, perPage, projectResponses);
        return projectResponses;
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('HEAD_OF_ARCHITECTURAL_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_STRUCTURAL_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_MvE_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_INTERIOR_DESIGN_DEPARTMENT') or hasRole('DESIGNER') or hasRole('COMMANDER')")
    public List<ProjectResponse> findProjectByNameContaining(String name, int page, int perPage){
        var projectResponses = projectRepository.findByNameContaining(name).stream()
                .map(projectMapper::toProjectResponse).toList();
        projectResponses = paginationUtils.convertListToPage(page, perPage, projectResponses);
        return projectResponses;
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('HEAD_OF_ARCHITECTURAL_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_STRUCTURAL_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_MvE_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_INTERIOR_DESIGN_DEPARTMENT')")
    public List<ProjectResponse> findProjectByDepartmentNameAndStatus(String departmentName, ProjectStatus status,
                                                                      int page, int perPage){
        String stringStatus = status.toString();
        var projectResponses = projectRepository.findByDepartmentProjectsDepartmentNameAndStatus(departmentName, stringStatus).stream()
                .map(projectMapper::toProjectResponse).toList();
        projectResponses = paginationUtils.convertListToPage(page, perPage, projectResponses);
        return projectResponses;
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('HEAD_OF_ARCHITECTURAL_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_STRUCTURAL_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_MvE_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_INTERIOR_DESIGN_DEPARTMENT')")
    public List<ProjectResponse> findProjectByDepartmentName(String departmentName, int page, int perPage){
        var projectResponses = projectRepository.findByDepartmentProjectsDepartmentName(departmentName).stream()
                .map(projectMapper::toProjectResponse).toList();
        projectResponses = paginationUtils.convertListToPage(page, perPage, projectResponses);
        return projectResponses;
    }

    @PreAuthorize("hasRole('ADMIN')")
    public ProjectResponse changeProjectStatus(ProjectChangeStatusRequest request, ProjectStatus status){
        String stringStatus = status.name();
        var project = projectRepository.findById(request.getProjectId())
                .orElseThrow(() -> new AppException(ErrorCode.PROJECT_NOT_FOUND));
        project.setStatus(stringStatus);

        return projectMapper.toProjectResponse(projectRepository.save(project));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public ProjectResponse changeProjectToInactiveStatus(UUID projectId){
        var project = projectRepository.findById(projectId)
                .orElseThrow(() -> new AppException(ErrorCode.PROJECT_NOT_FOUND));
        project.setStatus(ProjectStatus.INACTIVE.name());

        return projectMapper.toProjectResponse(projectRepository.save(project));
    }

    private void validateProjectDate(Date startDate, Date endDate){
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
