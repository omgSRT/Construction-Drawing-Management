package com.GSU24SE43.ConstructionDrawingManagement.repository;

import com.GSU24SE43.ConstructionDrawingManagement.entity.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LogRepository extends JpaRepository<Log, UUID> {

}
