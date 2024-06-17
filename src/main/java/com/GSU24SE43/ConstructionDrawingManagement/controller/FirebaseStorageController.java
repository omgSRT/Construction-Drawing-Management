package com.GSU24SE43.ConstructionDrawingManagement.controller;

import com.GSU24SE43.ConstructionDrawingManagement.dto.response.ApiResponse;
import com.GSU24SE43.ConstructionDrawingManagement.exception.AppException;
import com.GSU24SE43.ConstructionDrawingManagement.exception.ErrorCode;
import com.GSU24SE43.ConstructionDrawingManagement.service.FirebaseStorageService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/firebase")
@Slf4j
public class FirebaseStorageController {
    final FirebaseStorageService firebaseStorageService;

    @PostMapping("/upload")
    public ApiResponse<List<String>> uploadFile(@RequestParam @NonNull MultipartFile[] files,
                                                @RequestParam(required = false) String folderName) {
        try {
            List<String> fileUrl = firebaseStorageService.uploadFiles(files, folderName);
            return ApiResponse.<List<String>>builder()
                    .entity(fileUrl)
                    .build();
        } catch (IOException e) {
            throw new AppException(ErrorCode.UPLOAD_FAILED);
        }
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable String fileName) {
        try {
            byte[] fileContent = firebaseStorageService.downloadFile(fileName);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentDispositionFormData("attachment", fileName);
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(fileContent);
        } catch (IOException e) {
            throw new AppException(ErrorCode.DOWNLOAD_FAILED);
        }
    }
}
