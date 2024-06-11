package com.GSU24SE43.ConstructionDrawingManagement.repository;

import com.GSU24SE43.ConstructionDrawingManagement.entity.Subfolder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SubfolderRepository extends JpaRepository<Subfolder, UUID> {
    boolean existsByName(String name);

    List<Subfolder> findByNameContaining(String name);

    List<Subfolder> findByProjectId(UUID projectId);
}
