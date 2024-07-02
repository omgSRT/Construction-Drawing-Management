package com.GSU24SE43.ConstructionDrawingManagement.controller;
//
import com.GSU24SE43.ConstructionDrawingManagement.dto.request.AuthenticationRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.request.IntrospectRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.request.LogoutRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.ApiResponse;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.AuthenticationResponse;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.IntrospectResponse;
import com.GSU24SE43.ConstructionDrawingManagement.repository.AccountRepository;
import com.GSU24SE43.ConstructionDrawingManagement.service.AuthenticateService;
import com.nimbusds.jose.JOSEException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.text.ParseException;

@RestController
@RequestMapping(path = "/test")
@Slf4j
public class AuthenticationController {
    @Autowired
    private AuthenticateService authenticateService;
    @Autowired
    private AccountRepository accountRepository;

    @PostMapping("/login")
    public ApiResponse<AuthenticationResponse> login(@RequestBody AuthenticationRequest request){
        return ApiResponse.<AuthenticationResponse>builder()
                .entity(authenticateService.authenticated(request))
                .build();
    }
    @PostMapping("/introspect")
    public ApiResponse<IntrospectResponse> authenicated(@RequestBody IntrospectRequest token) throws ParseException, JOSEException {
        var result = authenticateService.introspectJWT(token);
        return ApiResponse.<IntrospectResponse>builder()
                .entity(result)
                .build();
    }

    @PostMapping("/logout")
    public ApiResponse<Void> logout(@RequestBody LogoutRequest request) throws ParseException, JOSEException {
        authenticateService.logout(request);
        return ApiResponse.<Void>builder().build();
    }

    @GetMapping("/randumbs")
    public String testReturn() {
        String filePath = "D:/Capstone/ConstructionDrawingManagement/src/main/resources/serviceAccountKey.json";
        try (InputStream serviceAccount = new FileInputStream(filePath);
             ByteArrayOutputStream result = new ByteArrayOutputStream()) {

            byte[] buffer = new byte[1024];
            int length;
            while ((length = serviceAccount.read(buffer)) != -1) {
                result.write(buffer, 0, length);
            }
            return result.toString("UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return "An error occurred while reading the file: " + e.getMessage();
        }
    }
}
