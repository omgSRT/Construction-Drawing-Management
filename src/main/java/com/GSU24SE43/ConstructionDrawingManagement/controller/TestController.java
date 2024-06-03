package com.GSU24SE43.ConstructionDrawingManagement.controller;

import com.GSU24SE43.ConstructionDrawingManagement.dto.response.ApiResponse;
import com.GSU24SE43.ConstructionDrawingManagement.entity.Account;
import com.GSU24SE43.ConstructionDrawingManagement.repository.AccountRepository;
import com.GSU24SE43.ConstructionDrawingManagement.service.AuthenticateService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/test")
@Slf4j
public class TestController {
    @Autowired
    private AuthenticateService authenticateService;
    private AccountRepository accountRepository;

    @Operation(summary = "Test JWT Creation")
    @PostMapping("/jwt")
    public ApiResponse<String> createJwt(String username, String password){
        var authen = SecurityContextHolder.getContext().getAuthentication();
        log.info(authen.getName());
        authen.getAuthorities().forEach(role -> log.info("$Role: {role}"));

        ApiResponse response = new ApiResponse<>();

        Account account = accountRepository.findByUsername(username);
        String jwt = authenticateService.generateToken(account);

        response.setMessage("Create Successfully");
        response.setEntity(jwt);

        return response;
    }
}
