package com.example.realestate.controller;
/*
 * @author HongAnh
 * @created 08 / 11 / 2024 - 1:45 CH
 * @project real-estate
 * @social Github: https://github.com/lehonganh0201
 * @social Facebook: https://www.facebook.com/profile.php?id=100047152174225
 */

import com.example.realestate.constant.Endpoint;
import com.example.realestate.domain.dto.global.GlobalResponse;
import com.example.realestate.domain.dto.global.Meta;
import com.example.realestate.domain.dto.request.ProjectRequest;
import com.example.realestate.domain.dto.response.ProjectResponse;
import com.example.realestate.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
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
public class ProjectController {
    ProjectService projectService;

    @Operation(
            summary = "Create a new project",
            description = "Create a new project with the provided project details.",
            tags = {"Project"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Project created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = GlobalResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized",
                    content = @Content(mediaType = "application/json")
            )
    })
    @PostMapping(Endpoint.V1.Project.CREATE_PROJECT)
    public ResponseEntity<GlobalResponse<Meta, ProjectResponse>> createProject(
            @AuthenticationPrincipal UserDetails userDetails, @RequestBody ProjectRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(projectService.createProject(userDetails.getUsername(), request));
    }

    @SneakyThrows
    @Operation(
            summary = "Import projects",
            description = "Import multiple projects into the system.",
            tags = {"Project"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Projects imported successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = GlobalResponse.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(mediaType = "application/json")
            )
    })
    @PostMapping(Endpoint.V1.Project.IMPORT_PROJECT)
    public ResponseEntity<GlobalResponse<Meta, List<ProjectResponse>>> importProject() {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(projectService.importProject());
    }

    @Operation(
            summary = "Get all projects",
            description = "Retrieve a list of all projects in the system.",
            tags = {"Project"}
    )
    @ApiResponse(
            responseCode = "200",
            description = "List of projects retrieved successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = GlobalResponse.class))
    )
    @GetMapping(Endpoint.V1.Project.GET_PROJECT)
    public ResponseEntity<GlobalResponse<Meta, List<ProjectResponse>>> getProjects(@RequestParam(defaultValue = "0") int page,
                                                                                   @RequestParam(defaultValue = "1000") int size) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(projectService.getProjects(page, size));
    }

    @Operation(
            summary = "Get a project by ID",
            description = "Retrieve a specific project using its unique ID.",
            tags = {"Project"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Project retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = GlobalResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Project not found",
                    content = @Content(mediaType = "application/json")
            )
    })
    @GetMapping(Endpoint.V1.Project.GET_PROJECT_BY_ID)
    public ResponseEntity<GlobalResponse<Meta, ProjectResponse>> getProject(
            @PathVariable(name = "projectId") UUID id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(projectService.getProject(id));
    }

    @Operation(
            summary = "Delete a project",
            description = "Delete a specific project using its unique ID.",
            tags = {"Project"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Project deleted successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = GlobalResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Project not found",
                    content = @Content(mediaType = "application/json")
            )
    })
    @DeleteMapping(Endpoint.V1.Project.DELETE_PROJECT)
    public ResponseEntity<GlobalResponse<Meta, String>> deleteProject(
            @PathVariable(name = "projectId") UUID id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(projectService.deleteProject(id));
    }

    @Operation(
            summary = "Update an existing project",
            description = "Update the details of an existing project.",
            tags = {"Project"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Project updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = GlobalResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Project not found",
                    content = @Content(mediaType = "application/json")
            )
    })
    @PutMapping(Endpoint.V1.Project.UPDATE_PROJECT)
    public ResponseEntity<GlobalResponse<Meta, ProjectResponse>> updateProject(
            @PathVariable(name = "projectId") UUID id, @RequestBody ProjectRequest request) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(projectService.updateProject(id, request));
    }
}
