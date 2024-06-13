package com.GSU24SE43.ConstructionDrawingManagement.service;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
@Service
public class FirebaseStorageService {
    final Storage storage;
    final Bucket bucket;

    public FirebaseStorageService() {
        this.storage = StorageOptions.getDefaultInstance().getService();
        this.bucket = storage.get("drawing-management.appspot.com");
    }

    public String uploadFile(MultipartFile file) throws IOException {
        Blob blob = bucket.create(file.getOriginalFilename(), file.getInputStream(), file.getContentType());
        return blob.getMediaLink();
    }

    public byte[] downloadFile(String fileName) throws IOException {
        Blob blob = bucket.get(fileName);
        return blob.getContent();
    }
}
