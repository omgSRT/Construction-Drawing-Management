package com.GSU24SE43.ConstructionDrawingManagement.exception;

import com.GSU24SE43.ConstructionDrawingManagement.dto.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandle {
    @ExceptionHandler(Exception.class)
    ResponseEntity<ApiResponse> handleRuntimeException(RuntimeException exception){
        ApiResponse apiRespone = new ApiResponse();
        apiRespone.setCode(ErrorCode.UNDEFINED_EXCEPTION.getCode());
        apiRespone.setMessage(ErrorCode.UNDEFINED_EXCEPTION.getMessage());
        return ResponseEntity.badRequest().body(apiRespone);
    }

    @ExceptionHandler(AppException.class)
    ResponseEntity<ApiResponse> handleAppException(AppException exception){
        ErrorCode error = exception.getErrorCode();
        ApiResponse apiRespone = new ApiResponse();

        apiRespone.setCode(error.getCode());
        apiRespone.setMessage(error.getMessage());

        return ResponseEntity.badRequest().body(apiRespone);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception){
        String enumkey = exception.getFieldError().getDefaultMessage();
        ErrorCode errorCode = ErrorCode.INVALID_ERROR_MESSAGE_KEY;

        try {
            errorCode = ErrorCode.valueOf(enumkey);
        } catch (IllegalArgumentException e){}

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());

        return ResponseEntity.badRequest().body(apiResponse);
    }
}
