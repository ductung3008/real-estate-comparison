package com.example.realestate.controller;

import com.example.realestate.constant.Endpoint;
import com.example.realestate.domain.dto.global.GlobalResponse;
import com.example.realestate.domain.dto.global.Meta;
import com.example.realestate.domain.dto.request.AuthRequest;
import com.example.realestate.domain.dto.response.AuthResponse;
import com.example.realestate.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthController {

    AuthService authService;

    @Operation(
            summary = "User Login",
            description = "Authenticate the user and return the authentication token.",
            tags = {"Authentication"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully logged in",
                    content = @Content(mediaType = "application/json",
                                       schema = @Schema(implementation = GlobalResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request, missing or incorrect parameters",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized, invalid credentials",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal Server Error",
                    content = @Content(mediaType = "application/json")
            )
    })
    @PostMapping(Endpoint.V1.Auth.LOGIN)
    public ResponseEntity<GlobalResponse<Meta, AuthResponse>> login(@RequestBody AuthRequest request) {
        GlobalResponse<Meta, AuthResponse> response = authService.login(request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
}
