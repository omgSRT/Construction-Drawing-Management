package com.GSU24SE43.ConstructionDrawingManagement.service;

import com.GSU24SE43.ConstructionDrawingManagement.exception.AppException;
import com.GSU24SE43.ConstructionDrawingManagement.exception.ErrorCode;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.StorageClient;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
@Service
public class FirebaseStorageService {
    final static String bucketName = "drawing-management.appspot.com";
    final static String imageLinkFormat = "https://firebasestorage.googleapis.com/v0/b/" +bucketName+ "/o/%s?alt=media";

    @EventListener
    public void initFirebaseApp(ApplicationReadyEvent event) {
        try {
//            String filePath = "D:/Capstone/ConstructionDrawingManagement/src/main/resources/serviceAccountKey.json";
//            FileInputStream serviceAccount = new FileInputStream(filePath);
            InputStream serviceAccount = getClass().getClassLoader().getResourceAsStream("serviceAccountKey.json");

            // Configure FirebaseOptions with the provided credentials and Storage bucket
            assert serviceAccount != null;
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setStorageBucket(bucketName)  // Specify Firebase Storage bucket
                    .build();

            // Initialize FirebaseApp with the provided options
            FirebaseApp.initializeApp(options);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('HEAD_OF_ARCHITECTURAL_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_STRUCTURAL_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_MvE_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_INTERIOR_DESIGN_DEPARTMENT') or hasRole('DESIGNER')")
    public List<String> uploadFiles(MultipartFile[] files, String folderName) throws IOException {
        Bucket bucket = StorageClient.getInstance().bucket();
        List<String> imageUrls = new ArrayList<>();
        String imageUrl = "";

        for (MultipartFile file : files) {
            String originalFilename = file.getOriginalFilename();
            String name;

            if (folderName != null && !folderName.isEmpty()) {
                name = generateFirebaseStoragePath(originalFilename, folderName);
            } else {
                name = generateFileName(originalFilename);
            }

            // Upload file to Firebase Storage
            bucket.create(name, file.getBytes(), file.getContentType());
            name = name.replaceAll("/", "%2F");
            imageUrl = String.format(imageLinkFormat, name);

            imageUrls.add(imageUrl);
        }

        return imageUrls;
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('HEAD_OF_ARCHITECTURAL_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_STRUCTURAL_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_MvE_DESIGN_DEPARTMENT') or hasRole('HEAD_OF_INTERIOR_DESIGN_DEPARTMENT') or hasRole('DESIGNER') or hasRole('COMMANDER')")
    public byte[] downloadFile(String name) throws IOException {
        Bucket bucket = StorageClient.getInstance().bucket();

        Blob blob = bucket.get(name);

        if (blob == null) {
            throw new AppException(ErrorCode.FILE_NOT_FOUND);
        }

        return blob.getContent();
    }

    public void deleteFirebaseFile(String name) throws IOException {

        Bucket bucket = StorageClient.getInstance().bucket();

        if (name.isEmpty()) {
            throw new AppException(ErrorCode.INVALID_FILE_NAME);
        }

        Blob blob = bucket.get(name);

        if (blob == null) {
            throw new AppException(ErrorCode.FILE_NOT_FOUND);
        }

        blob.delete();
    }

    public String getNameWithoutExtension(String originalFileName){
        return StringUtils.stripFilenameExtension(originalFileName);
    }

    public String generateFirebaseStoragePath(String originalFileName, String folderName) {
        String generateFilename = UUID.randomUUID() +"_"+ getNameWithoutExtension(originalFileName);
        return folderName +"/"+ generateFilename;
    }

    public String generateFileName(String originalFileName) {
        return UUID.randomUUID() +"_"+ getNameWithoutExtension(originalFileName);
    }

    byte[] getByteArrays(BufferedImage bufferedImage, String format) throws IOException {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {

            ImageIO.write(bufferedImage, format, baos);

            baos.flush();

            return baos.toByteArray();

        } catch (IOException e) {
            throw e;
        } finally {
            baos.close();
        }
    }
}
