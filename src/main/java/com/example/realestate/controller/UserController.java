package com.example.realestate.controller;
/*
 * @author HongAnh
 * @created 06 / 11 / 2024 - 11:53 SA
 * @project real-estate
 * @social Github: https://github.com/lehonganh0201
 * @social Facebook: https://www.facebook.com/profile.php?id=100047152174225
 */

import com.example.realestate.constant.Endpoint;
import com.example.realestate.domain.dto.global.GlobalResponse;
import com.example.realestate.domain.dto.global.Meta;
import com.example.realestate.domain.dto.request.UserRequest;
import com.example.realestate.domain.dto.response.UserResponse;
import com.example.realestate.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Log4j2
@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    UserService userService;

    @Operation(
            summary = "Create a new user",
            description = "Register a new user in the system.",
            tags = {"User"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "User successfully created",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = GlobalResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid user data",
                    content = @Content(mediaType = "application/json")
            )
    })
    @PostMapping(Endpoint.V1.User.CREATE_USER)
    public ResponseEntity<GlobalResponse<Meta, UserResponse>> register(@RequestBody UserRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.createUser(request));
    }

    @Operation(
            summary = "Get the logged-in user",
            description = "Retrieve details of the currently authenticated user.",
            tags = {"User"}
    )
    @ApiResponse(
            responseCode = "200",
            description = "User details retrieved successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = GlobalResponse.class))
    )
    @GetMapping(Endpoint.V1.User.GET_MY_USER)
    public ResponseEntity<GlobalResponse<Meta, UserResponse>> getUser(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.getUser(userDetails.getUsername()));
    }

    @Operation(
            summary = "Get all users",
            description = "Retrieve a list of all users in the system.",
            tags = {"User"}
    )
    @ApiResponse(
            responseCode = "200",
            description = "Users retrieved successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = GlobalResponse.class))
    )
    @GetMapping(Endpoint.V1.User.GET_ALL_USER)
    public ResponseEntity<GlobalResponse<Meta, List<UserResponse>>> getUsers() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.getUsers());
    }

    @Operation(
            summary = "Get user by ID",
            description = "Retrieve user details by their unique ID.",
            tags = {"User"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = GlobalResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found",
                    content = @Content(mediaType = "application/json")
            )
    })
    @GetMapping(Endpoint.V1.User.GET_USER)
    public ResponseEntity<GlobalResponse<Meta, UserResponse>> getUser(@PathVariable(name = "userId") UUID id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.getUser(id));
    }

    @Operation(
            summary = "Update user details",
            description = "Update the details of an existing user by their unique ID.",
            tags = {"User"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = GlobalResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid user data",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found",
                    content = @Content(mediaType = "application/json")
            )
    })
    @PutMapping(Endpoint.V1.User.UPDATE_USER)
    public ResponseEntity<GlobalResponse<Meta, UserResponse>> updateUser(@PathVariable(name = "userId") UUID uuid,
                                                                         @RequestBody UserRequest request) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.updateUser(uuid, request));
    }

    @Operation(
            summary = "Delete a user",
            description = "Delete a user from the system by their unique ID.",
            tags = {"User"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User deleted successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = GlobalResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found",
                    content = @Content(mediaType = "application/json")
            )
    })
    @DeleteMapping(Endpoint.V1.User.DELETE_USER)
    public ResponseEntity<GlobalResponse<Meta, String>> deleteUser(@PathVariable(name = "userId") UUID id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.deleteUser(id));
    }
}
