package com.GSU24SE43.ConstructionDrawingManagement.service;

import com.GSU24SE43.ConstructionDrawingManagement.Utils.PaginationUtils;
import com.GSU24SE43.ConstructionDrawingManagement.dto.request.ProjectChangeStatusRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.request.ProjectRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.request.ProjectUpdateRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.ProjectResponse;
import com.GSU24SE43.ConstructionDrawingManagement.entity.Department;
import com.GSU24SE43.ConstructionDrawingManagement.entity.Folder;
import com.GSU24SE43.ConstructionDrawingManagement.entity.Project;
import com.GSU24SE43.ConstructionDrawingManagement.entity.Account;
import com.GSU24SE43.ConstructionDrawingManagement.enums.ProjectStatus;
import com.GSU24SE43.ConstructionDrawingManagement.exception.AppException;
import com.GSU24SE43.ConstructionDrawingManagement.exception.ErrorCode;
import com.GSU24SE43.ConstructionDrawingManagement.mapper.ProjectMapper;
import com.GSU24SE43.ConstructionDrawingManagement.repository.AccountRepository;
import com.GSU24SE43.ConstructionDrawingManagement.repository.DepartmentRepository;
import com.GSU24SE43.ConstructionDrawingManagement.repository.FolderRepository;
import com.GSU24SE43.ConstructionDrawingManagement.repository.ProjectRepository;
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
public class ProjectService {
    final ProjectRepository projectRepository;
    final ProjectMapper projectMapper;
    final FolderRepository folderRepository;
    final AccountRepository accountRepository;
    final DepartmentRepository departmentRepository;
    final PaginationUtils paginationUtils = new PaginationUtils();

    public ProjectResponse createProject(ProjectRequest request){
        if(projectRepository.existsByName(request.getName())){
            throw new AppException(ErrorCode.NAME_EXISTED);
        }
        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new AppException(ErrorCode.DEPARTMENT_NOT_FOUND));
        Account account = accountRepository.findById(request.getAccountId())
                .orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));
        Folder folder = folderRepository.findById(request.getFolderId())
                .orElseThrow(() -> new AppException(ErrorCode.FOLDER_NOT_FOUND));

        ValidateProjectDate(request.getStartDate(), request.getEndDate());

        Project newProject = projectMapper.toProject(request);
        newProject.setCreationDate(new Date());
        newProject.setDepartment(department);
        newProject.setAccount(account);
        newProject.setFolder(folder);
        newProject.setStatus(ProjectStatus.ACTIVE.name());

        return projectMapper.toProjectResponse(projectRepository.save(newProject));
    }

    public List<ProjectResponse> getAllProjects(int page, int perPage) {
        try {
            List<ProjectResponse> projectResponses = projectRepository.findAll().stream().map(projectMapper::toProjectResponse).toList();
            projectResponses = paginationUtils.convertListToPage(page, perPage, projectResponses);
            return projectResponses;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<ProjectResponse> getAllActiveProjects(int page, int perPage) {
        try {
            List<ProjectResponse> projectResponses = projectRepository.findByStatus("ACTIVE")
                    .stream().map(projectMapper::toProjectResponse).toList();
            projectResponses = paginationUtils.convertListToPage(page, perPage, projectResponses);
            return projectResponses;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteProjectById(UUID id){
        var project = projectRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PROJECT_NOT_FOUND));
        projectRepository.delete(project);
    }

    public ProjectResponse findProjectById(UUID id){
        return projectMapper.toProjectResponse(projectRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PROJECT_NOT_FOUND)));
    }

    public ProjectResponse findActiveProjectById(UUID id){
        var project = projectRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PROJECT_NOT_FOUND));
        if(!project.getStatus().equals("ACTIVE")){
            project = null;
        }
        return projectMapper.toProjectResponse(project);
    }

    public ProjectResponse updateProjectById(UUID id, ProjectUpdateRequest request){
        var project = projectRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PROJECT_NOT_FOUND));

        ValidateProjectDate(request.getStartDate(), request.getEndDate());

        projectMapper.updateProject(project, request);

        return projectMapper.toProjectResponse(projectRepository.save(project));
    }

    public List<ProjectResponse> findProjectByNameContainingAndStatus(String name, String status, int page, int perPage){
        try {
            List<ProjectResponse> projectResponses
                    = projectRepository.findByNameContainingAndStatus(name, status).stream().map(projectMapper::toProjectResponse).toList();
            projectResponses = paginationUtils.convertListToPage(page, perPage, projectResponses);
            return projectResponses;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<ProjectResponse> findProjectByNameContaining(String name, int page, int perPage){
        try {
            List<ProjectResponse> projectResponses
                    = projectRepository.findByNameContaining(name).stream().map(projectMapper::toProjectResponse).toList();
            projectResponses = paginationUtils.convertListToPage(page, perPage, projectResponses);
            return projectResponses;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<ProjectResponse> findProjectByDepartmentNameAndStatus(String departmentName, String status,
                                                                      int page, int perPage){
        try {
            List<ProjectResponse> projectResponses
                    = projectRepository.findByDepartmentNameAndStatus(departmentName, status)
                    .stream().map(projectMapper::toProjectResponse).toList();
            projectResponses = paginationUtils.convertListToPage(page, perPage, projectResponses);
            return projectResponses;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<ProjectResponse> findProjectByDepartmentName(String departmentName, int page, int perPage){
        try {
            List<ProjectResponse> projectResponses
                    = projectRepository.findByDepartmentName(departmentName)
                    .stream().map(projectMapper::toProjectResponse).toList();
            projectResponses = paginationUtils.convertListToPage(page, perPage, projectResponses);
            return projectResponses;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ProjectResponse changeProjectStatus(ProjectChangeStatusRequest request){
        ProjectStatus projectStatus;
        try {
            projectStatus = ProjectStatus.valueOf(request.getStatusName().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new AppException(ErrorCode.INVALID_STATUS);
        }

        var project = projectRepository.findById(request.getProjectId())
                .orElseThrow(() -> new AppException(ErrorCode.PROJECT_NOT_FOUND));

        project.setStatus(request.getStatusName());

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
