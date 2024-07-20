package com.GSU24SE43.ConstructionDrawingManagement.repository;

import com.GSU24SE43.ConstructionDrawingManagement.entity.DetailTask;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DetailTaskRepository extends JpaRepository<DetailTask, UUID> {
    List<DetailTask> findByStaffStaffId(UUID staffId);
    List<DetailTask> findByStaff_StaffId(UUID staffId);
    List<DetailTask> findByTask_Id(UUID taskId);
}
