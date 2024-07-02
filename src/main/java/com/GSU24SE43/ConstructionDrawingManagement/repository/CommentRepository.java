package com.GSU24SE43.ConstructionDrawingManagement.repository;

import com.GSU24SE43.ConstructionDrawingManagement.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CommentRepository extends JpaRepository<Comment, UUID> {
    List<Comment> findByStaffStaffId(UUID staffId);

    List<Comment> findByStaffStaffIdAndStatus(UUID staffId, String status);

    List<Comment> findByTaskId(UUID taskId);

    List<Comment> findByTaskIdAndStatus(UUID taskId, String status);

    List<Comment> findByStatus(String status);
}
