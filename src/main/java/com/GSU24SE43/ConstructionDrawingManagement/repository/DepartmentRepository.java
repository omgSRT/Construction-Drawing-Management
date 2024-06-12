package com.GSU24SE43.ConstructionDrawingManagement.repository;

import com.GSU24SE43.ConstructionDrawingManagement.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, UUID> {
boolean existsByDepartmentId(UUID id);


}
