package com.example.demo.controller;

import com.example.demo.dto.ApiResponseDTO;
import com.example.demo.utils.ApiResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.AuthenticationRequest;
import com.example.demo.dto.AuthenticationResponse;
import com.example.demo.service.AuthenticationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Operation(description = "Endpoint for User Login")
    @PostMapping("/authenticate")
    public ResponseEntity<ApiResponseDTO<AuthenticationResponse>> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ApiResponseUtil.success(authenticationService.authenticate(request), "Authentication successful");
    }
}
