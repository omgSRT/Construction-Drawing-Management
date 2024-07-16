package com.GSU24SE43.ConstructionDrawingManagement.controller;

import com.GSU24SE43.ConstructionDrawingManagement.dto.request.CommentRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.request.CommentUpdateRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.ApiResponse;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.CommentResponse;
import com.GSU24SE43.ConstructionDrawingManagement.enums.CommentStatus;
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

    @Operation(summary = "Get All Comments", description = "Get All Comments by Status")
    @GetMapping(path = "/getallByStatus")
    public ApiResponse<List<CommentResponse>> getAllCommentsByStatus(@RequestParam(defaultValue = "1") int page,
                                                             @RequestParam(defaultValue = "10") int perPage,
                                                             @RequestParam CommentStatus status){
        return ApiResponse.<List<CommentResponse>>builder()
                .entity(commentService.getAllCommentsWithStatus(page, perPage, status))
                .build();
    }

    @Operation(summary = "Get All Comments", description = "Get All Comments")
    @GetMapping(path = "/getall")
    public ApiResponse<List<CommentResponse>> getAllComments(@RequestParam(defaultValue = "1") int page,
                                                                     @RequestParam(defaultValue = "10") int perPage){
        return ApiResponse.<List<CommentResponse>>builder()
                .entity(commentService.getAllComments(page, perPage))
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

    @Operation(summary = "Get Comment", description = "Get A Comment by ID and Status")
    @GetMapping(path = "/getByStatus/{id}")
    public ApiResponse<CommentResponse> getCommentByIdAndStatus(@PathVariable UUID id,
                                                                @RequestParam CommentStatus status){
        return ApiResponse.<CommentResponse>builder()
                .message(SuccessReturnMessage.SEARCH_SUCCESS.getMessage())
                .entity(commentService.findCommentByIdAndStatus(id, status))
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

    @Operation(summary = "Get Comments", description = "Get All Comments by Task ID and Status")
    @GetMapping(path = "/getallByTaskIdAndStatus/{taskId}")
    public ApiResponse<List<CommentResponse>> getCommentsByTaskIdAndStatus(@RequestParam(defaultValue = "1") int page,
                                                                  @RequestParam(defaultValue = "10") int perPage,
                                                                  @PathVariable UUID taskId,
                                                                  @RequestParam CommentStatus status){
        return ApiResponse.<List<CommentResponse>>builder()
                .entity(commentService.findCommentsByTaskIdAndStatus(taskId, status, page, perPage))
                .build();
    }

    @Operation(summary = "Get Comments", description = "Get All Comments by Task ID")
    @GetMapping(path = "/getallByTaskId/{taskId}")
    public ApiResponse<List<CommentResponse>> getCommentsByTaskId(@RequestParam(defaultValue = "1") int page,
                                                                           @RequestParam(defaultValue = "10") int perPage,
                                                                           @PathVariable UUID taskId){
        return ApiResponse.<List<CommentResponse>>builder()
                .entity(commentService.findCommentsByTaskId(taskId, page, perPage))
                .build();
    }

    @Operation(summary = "Get Comments", description = "Get All Comments by Staff ID and Status")
    @GetMapping(path = "/getallByStaffIdAndStatus/{staffId}")
    public ApiResponse<List<CommentResponse>> getCommentsByStaffIdAndStatus(@RequestParam(defaultValue = "1") int page,
                                                                  @RequestParam(defaultValue = "10") int perPage,
                                                                  @PathVariable UUID staffId,
                                                                   @RequestParam CommentStatus status){
        return ApiResponse.<List<CommentResponse>>builder()
                .entity(commentService.findCommentsByStaffIdAndStatus(staffId, status, page, perPage))
                .build();
    }

    @Operation(summary = "Get Comments", description = "Get All Comments by Staff ID")
    @GetMapping(path = "/getallByStaffId/{staffId}")
    public ApiResponse<List<CommentResponse>> getCommentsByStaffId(@RequestParam(defaultValue = "1") int page,
                                                                            @RequestParam(defaultValue = "10") int perPage,
                                                                            @PathVariable UUID staffId){
        return ApiResponse.<List<CommentResponse>>builder()
                .entity(commentService.findCommentsByStaffId(staffId, page, perPage))
                .build();
    }

    @Operation(summary = "Change Comment Status", description = "Change Comment Status")
    @PutMapping(path = "/change/status/{commentId}")
    public ApiResponse<CommentResponse> changeCommentStatusToHidden(@PathVariable UUID commentId){
        return ApiResponse.<CommentResponse>builder()
                .message(SuccessReturnMessage.CHANGE_SUCCESS.getMessage())
                .entity(commentService.changeCommentStatus(commentId))
                .build();
    }
}
