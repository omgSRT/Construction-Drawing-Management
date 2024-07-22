package com.GSU24SE43.ConstructionDrawingManagement.repository;

import com.GSU24SE43.ConstructionDrawingManagement.entity.FloorDetail;
import com.GSU24SE43.ConstructionDrawingManagement.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
import java.util.List;

@Repository
public interface FloorDetailRepository extends JpaRepository<FloorDetail, UUID> {
    List<FloorDetail> findByProject(Project project);

    Optional<FloorDetail> findByProjectAndFloorNumber(Project project, int floorNumber);

    List<FloorDetail> findByProjectAndFloorNumberGreaterThan(Project project, int floorNumber);
}
