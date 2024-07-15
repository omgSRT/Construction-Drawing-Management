package com.GSU24SE43.ConstructionDrawingManagement.repository;

import com.GSU24SE43.ConstructionDrawingManagement.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProjectRepository extends JpaRepository<Project, UUID> {
    boolean existsByName(String name);

    Optional<Project> findByIdAndStatus(UUID projectId, String status);

    List<Project> findByNameContaining(String name);

    List<Project> findByNameContainingAndStatus(String name, String status);

    List<Project> findByDepartmentProjectsDepartmentNameAndStatus(String departmentName, String status);

    List<Project> findByDepartmentProjectsDepartmentName(String departmentName);

    List<Project> findByStatus(String status);
}
