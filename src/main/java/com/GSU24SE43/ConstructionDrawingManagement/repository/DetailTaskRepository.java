package com.GSU24SE43.ConstructionDrawingManagement.repository;

import com.GSU24SE43.ConstructionDrawingManagement.entity.DetailTask;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DetailTaskRepository extends JpaRepository<DetailTask, UUID> {
}
