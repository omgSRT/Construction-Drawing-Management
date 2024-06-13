package com.GSU24SE43.ConstructionDrawingManagement.configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.FileInputStream;
import java.io.IOException;

@Configuration
public class FirebaseConfiguration {
//    @Bean
//    public FirebaseApp firebaseApp() throws IOException {
//        String filePath = "src/main/resources/serviceAccountKey.json";
//        FileInputStream serviceAccount
//                = new FileInputStream(filePath);
//
//        FirebaseOptions options = FirebaseOptions.builder()
//                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
//                .setStorageBucket("drawing-management.appspot.com")  // Specify Firebase Storage bucket
//                .build();
//
//        // Initialize FirebaseApp with the provided options
//        return FirebaseApp.initializeApp(options);
//    }
    @Bean
    public FirebaseApp firebaseApp() throws IOException {
        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(new ClassPathResource("serviceAccountKey.json").getInputStream()))
                .setStorageBucket("drawing-management.appspot.com")
                .build();

        return FirebaseApp.initializeApp(options);
    }
}
