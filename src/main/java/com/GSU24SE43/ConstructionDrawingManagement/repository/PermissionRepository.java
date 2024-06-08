package com.GSU24SE43.ConstructionDrawingManagement.repository;

import com.GSU24SE43.ConstructionDrawingManagement.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, UUID> {
}
