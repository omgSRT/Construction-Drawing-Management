package com.GSU24SE43.ConstructionDrawingManagement.service;

import com.GSU24SE43.ConstructionDrawingManagement.dto.request.ProjectRequest;
import com.GSU24SE43.ConstructionDrawingManagement.entity.Department;
import com.GSU24SE43.ConstructionDrawingManagement.entity.Folder;
import com.GSU24SE43.ConstructionDrawingManagement.entity.Project;
import com.GSU24SE43.ConstructionDrawingManagement.entity.Account;
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

    public Project createProject(ProjectRequest request){
        if(folderRepository.existsByName(request.getName())){
            throw new AppException(ErrorCode.NAME_EXISTED);
        }
        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new AppException(ErrorCode.DEPARTMENT_NOT_FOUND));
        Account account = accountRepository.findById(request.getAccountId())
                .orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));
        Folder folder = folderRepository.findById(request.getFolderId())
                .orElseThrow(() -> new AppException(ErrorCode.FOLDER_NOT_FOUND));

        ValidateProjectDate(request.getCreationDate(), request.getEndDate());

        Project newProject = projectMapper.toProject(request);
        newProject.setCreationDate(new Date());

        return projectRepository.save(newProject);
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public void deleteProjectById(UUID id){
        var folder = projectRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PROJECT_NOT_FOUND));
        projectRepository.delete(folder);
    }

    public Project findProjectById(UUID id){
        return projectRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PROJECT_NOT_FOUND));
    }

    public Project updateProjectById(UUID id, ProjectRequest request){
        var project = projectRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PROJECT_NOT_FOUND));

        //to-do: validate request
        projectMapper.updateProject(project, request);

        return projectRepository.save(project);
    }

    public List<Project> findProjectByNameContaining(String name){
        List<Project> projects = projectRepository.findByNameContaining(name);
        if(projects.isEmpty()){
            throw new AppException(ErrorCode.EMPTY_LIST);
        }
        return projects;
    }

    private void ValidateProjectDate(Date createdDate, Date endDate){
        int result = createdDate.compareTo(endDate);
        int result1 = createdDate.compareTo(new Date());
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
