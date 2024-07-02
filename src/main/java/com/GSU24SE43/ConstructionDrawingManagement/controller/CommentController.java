package com.GSU24SE43.ConstructionDrawingManagement.controller;

import com.GSU24SE43.ConstructionDrawingManagement.dto.request.CommentRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.request.CommentUpdateRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.ApiResponse;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.CommentResponse;
import com.GSU24SE43.ConstructionDrawingManagement.enums.SuccessReturnMessage;
import com.GSU24SE43.ConstructionDrawingManagement.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/comment")
@Slf4j
public class CommentController {
    final CommentService commentService;

    @Operation(summary = "Create Comment", description = "Create A Brand New Comment")
    @PostMapping(path = "/create")
    public ApiResponse<CommentResponse> createComment(@RequestBody @Valid CommentRequest request){
        return ApiResponse.<CommentResponse>builder()
                .message(SuccessReturnMessage.CREATE_SUCCESS.getMessage())
                .entity(commentService.createComment(request))
                .build();
    }

    @Operation(summary = "Get All Comments", description = "Get All Folders")
    @GetMapping(path = "/getall")
    public ApiResponse<List<CommentResponse>> getAllComments(@RequestParam(defaultValue = "1") int page,
                                                             @RequestParam(defaultValue = "10") int perPage,
                                                             @RequestParam(required = false) String status){
        return ApiResponse.<List<CommentResponse>>builder()
                .entity(commentService.getAllComments(page, perPage, status))
                .build();
    }

    @Operation(summary = "Get Comment", description = "Get A Comment by ID")
    @GetMapping(path = "/get/{id}")
    public ApiResponse<CommentResponse> getCommentById(@PathVariable UUID id){
        return ApiResponse.<CommentResponse>builder()
                .message(SuccessReturnMessage.SEARCH_SUCCESS.getMessage())
                .entity(commentService.findCommentById(id))
                .build();
    }

    @Operation(summary = "Update Comment", description = "Update A Comment by ID")
    @PutMapping(path = "/update/{id}")
    public ApiResponse<CommentResponse> updateCommentById(@PathVariable UUID id, @RequestBody @Valid CommentUpdateRequest request){
        return ApiResponse.<CommentResponse>builder()
                .message(SuccessReturnMessage.UPDATE_SUCCESS.getMessage())
                .entity(commentService.updateCommentById(id, request))
                .build();
    }

    @Operation(summary = "Delete Comment", description = "Delete Comment by ID")
    @DeleteMapping(path = "/delete/{id}")
    public ApiResponse<CommentResponse> deleteComment(@PathVariable UUID id){
        CommentResponse commentResponse = commentService.findCommentById(id);
        commentService.deleteCommentById(id);
        return ApiResponse.<CommentResponse>builder()
                .message(SuccessReturnMessage.DELETE_SUCCESS.getMessage())
                .entity(commentResponse)
                .build();
    }

    @Operation(summary = "Get Comments", description = "Get All Comments by Task ID")
    @GetMapping(path = "/getall/task/{taskId}")
    public ApiResponse<List<CommentResponse>> getCommentsByTaskId(@RequestParam(defaultValue = "1") int page,
                                                                  @RequestParam(defaultValue = "10") int perPage,
                                                                  @RequestParam UUID taskId,
                                                                  @RequestParam(required = false) String status){
        return ApiResponse.<List<CommentResponse>>builder()
                .entity(commentService.findCommentsByTaskId(taskId, status, page, perPage))
                .build();
    }

    @Operation(summary = "Get Comments", description = "Get All Comments by Staff ID")
    @GetMapping(path = "/getall/staff/{staffId}")
    public ApiResponse<List<CommentResponse>> getCommentsByStaffId(@RequestParam(defaultValue = "1") int page,
                                                                  @RequestParam(defaultValue = "10") int perPage,
                                                                  @RequestParam UUID staffId,
                                                                   @RequestParam(required = false) String status){
        return ApiResponse.<List<CommentResponse>>builder()
                .entity(commentService.findCommentsByStaffId(staffId, status, page, perPage))
                .build();
    }

    @Operation(summary = "Change Comment Status", description = "Change Comment Status To Hidden")
    @PutMapping(path = "/change/status/{commentId}")
    public ApiResponse<CommentResponse> changeCommentStatusToHidden(@PathVariable UUID commentId){
        return ApiResponse.<CommentResponse>builder()
                .message(SuccessReturnMessage.CHANGE_SUCCESS.getMessage())
                .entity(commentService.changeCommentStatusToHidden(commentId))
                .build();
    }
}
