package com.GSU24SE43.ConstructionDrawingManagement.repository;

import com.GSU24SE43.ConstructionDrawingManagement.entity.Drawing;
import com.GSU24SE43.ConstructionDrawingManagement.entity.Folder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DrawingRepository extends JpaRepository<Drawing, UUID> {
    boolean existsByName(String name);

    List<Drawing> findByFolder(Folder folder);

    List<Drawing> findByFolderAndStatus(Folder folder, String status);

    List<Drawing> findByNameContaining(String name);

    List<Drawing> findByNameContainingAndStatus(String name, String status);

    List<Drawing> findByStatus(String status);
}
