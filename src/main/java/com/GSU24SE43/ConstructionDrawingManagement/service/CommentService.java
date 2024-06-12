package com.GSU24SE43.ConstructionDrawingManagement.service;

import com.GSU24SE43.ConstructionDrawingManagement.Utils.PaginationUtils;
import com.GSU24SE43.ConstructionDrawingManagement.dto.request.CommentRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.request.FolderRequest;
import com.GSU24SE43.ConstructionDrawingManagement.entity.Comment;
import com.GSU24SE43.ConstructionDrawingManagement.entity.CommentResponse;
import com.GSU24SE43.ConstructionDrawingManagement.entity.Folder;
import com.GSU24SE43.ConstructionDrawingManagement.exception.AppException;
import com.GSU24SE43.ConstructionDrawingManagement.exception.ErrorCode;
import com.GSU24SE43.ConstructionDrawingManagement.mapper.CommentMapper;
import com.GSU24SE43.ConstructionDrawingManagement.repository.CommentRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.parameters.P;
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

    public List<CommentResponse> getAllCommentss(int page, int perPage) {
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

    public CommentResponse updateCommentById(UUID id, CommentRequest request){
        var comment = commentRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.COMMENT_NOT_FOUND));

        commentMapper.updateComment(comment, request);

        return commentMapper.toCommentResponse(commentRepository.save(comment));
    }

    public List<CommentResponse> findCommentsByStaffId(UUID staffId, int page, int perPage){
        try {
            var comments = commentRepository.findByStaffId(staffId).stream().map(commentMapper::toCommentResponse).toList();
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
