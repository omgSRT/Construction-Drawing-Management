package com.GSU24SE43.ConstructionDrawingManagement.mapper;

import com.GSU24SE43.ConstructionDrawingManagement.dto.request.CommentRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.request.CommentUpdateRequest;
import com.GSU24SE43.ConstructionDrawingManagement.entity.Comment;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.CommentResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    Comment toComment(CommentRequest request);

    @Mapping(source = "staff.staffId", target = "staffId")
    @Mapping(source = "staff.fullName", target = "staffFullName")
    @Mapping(source = "staff.email", target = "staffEmail")
    CommentResponse toCommentResponse(Comment comment);

    @Mapping(target = "staff", ignore = true)
    @Mapping(target = "task", ignore = true)
    void updateComment(@MappingTarget Comment comment, CommentUpdateRequest request);
}
