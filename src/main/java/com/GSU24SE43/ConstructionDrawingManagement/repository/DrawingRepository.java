package com.GSU24SE43.ConstructionDrawingManagement.repository;

import com.GSU24SE43.ConstructionDrawingManagement.entity.Drawing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DrawingRepository extends JpaRepository<Drawing, UUID> {
    boolean existsByName(String name);
}
