package com.GSU24SE43.ConstructionDrawingManagement.repository;

import com.GSU24SE43.ConstructionDrawingManagement.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProjectRepository extends JpaRepository<Project, UUID> {
    boolean existsByName(String name);

    List<Project> findByNameContaining(String name);

    List<Project> findByNameContainingAndStatus(String name, String status);

    List<Project> findByDepartmentNameAndStatus(String departmentName, String status);

    List<Project> findByDepartmentName(String departmentName);

    List<Project> findByStatus(String status);
}
