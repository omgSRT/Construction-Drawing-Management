package com.GSU24SE43.ConstructionDrawingManagement.repository;

import com.GSU24SE43.ConstructionDrawingManagement.entity.Directory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DirectoryRepository extends JpaRepository<Directory, UUID> {
    boolean existsByName(String name);

    List<Directory> findByNameContaining(String name);
}
