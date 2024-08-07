package com.GSU24SE43.ConstructionDrawingManagement.repository;

import com.GSU24SE43.ConstructionDrawingManagement.entity.DepartmentProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DepartmentProjectRepository extends JpaRepository<DepartmentProject, UUID> {
    List<DepartmentProject> findByDepartmentDepartmentId(UUID departmentId);

    List<DepartmentProject> findByProjectId(UUID projectId);
}
