package com.GSU24SE43.ConstructionDrawingManagement.service;

import com.GSU24SE43.ConstructionDrawingManagement.Utils.PaginationUtils;
import com.GSU24SE43.ConstructionDrawingManagement.dto.request.CommentRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.request.CommentUpdateRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.CommentResponse;
import com.GSU24SE43.ConstructionDrawingManagement.entity.Comment;
import com.GSU24SE43.ConstructionDrawingManagement.entity.Staff;
import com.GSU24SE43.ConstructionDrawingManagement.exception.AppException;
import com.GSU24SE43.ConstructionDrawingManagement.exception.ErrorCode;
import com.GSU24SE43.ConstructionDrawingManagement.mapper.CommentMapper;
import com.GSU24SE43.ConstructionDrawingManagement.repository.CommentRepository;
import com.GSU24SE43.ConstructionDrawingManagement.repository.StaffRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
@Service
public class CommentService {
    final CommentRepository commentRepository;
    final CommentMapper commentMapper;
    final PaginationUtils paginationUtils = new PaginationUtils();
    final StaffRepository staffRepository;
    //final TaskRepository taskRepository;

    public CommentResponse createComment(CommentRequest request){
        Staff staff = staffRepository.findById(request.getStaffId())
                .orElseThrow(() -> new AppException(ErrorCode.STAFF_NOT_FOUND));

        //Task task = taskRepository.findById(request.getTaskId())
        //            .orElseThrow(() -> new AppException(ErrorCode.TASK_NOT_FOUND))

        Comment newComment = commentMapper.toComment(request);
        newComment.setStaff(staff);

        return commentMapper.toCommentResponse(commentRepository.save(newComment));
    }

    public List<CommentResponse> getAllComments(int page, int perPage) {
        try {
            var comments = commentRepository.findAll().stream().map(commentMapper::toCommentResponse).toList();
            return paginationUtils.convertListToPage(page, perPage, comments);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteCommentById(UUID id){
        var comment = commentRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.COMMENT_NOT_FOUND));
        commentRepository.delete(comment);
    }

    public CommentResponse findCommentById(UUID id){
        return commentMapper.toCommentResponse(commentRepository.findById(id)
                                                .orElseThrow(() -> new AppException(ErrorCode.COMMENT_NOT_FOUND)));
    }

    public CommentResponse updateCommentById(UUID id, CommentUpdateRequest request){
        var comment = commentRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.COMMENT_NOT_FOUND));

        commentMapper.updateComment(comment, request);

        return commentMapper.toCommentResponse(commentRepository.save(comment));
    }

    public List<CommentResponse> findCommentsByStaffId(UUID staffId, int page, int perPage){
        try {
            var comments = commentRepository.findByStaffStaffId(staffId).stream().map(commentMapper::toCommentResponse).toList();
            return paginationUtils.convertListToPage(page, perPage, comments);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<CommentResponse> findCommentsByTaskId(UUID taskId, int page, int perPage){
        try {
            var comments = commentRepository.findByTaskId(taskId).stream().map(commentMapper::toCommentResponse).toList();
            return paginationUtils.convertListToPage(page, perPage, comments);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
