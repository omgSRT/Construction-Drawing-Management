package com.GSU24SE43.ConstructionDrawingManagement.repository;

import com.GSU24SE43.ConstructionDrawingManagement.entity.Version;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface VersionRepository extends JpaRepository<Version, UUID> {
    List<Version> findByDrawingId(UUID drawingId);

    List<Version> findByDrawingIdAndStatus(UUID drawingId, String status);

    List<Version> findByStatus(String status);
}
