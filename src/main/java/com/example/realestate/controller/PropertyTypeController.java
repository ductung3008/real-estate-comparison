package com.example.realestate.controller;
/*
 * @author HongAnh
 * @created 10 / 11 / 2024 - 2:46 CH
 * @project real-estate
 * @social Github: https://github.com/lehonganh0201
 * @social Facebook: https://www.facebook.com/profile.php?id=100047152174225
 */

import com.example.realestate.constant.Endpoint;
import com.example.realestate.domain.dto.global.GlobalResponse;
import com.example.realestate.domain.dto.global.Meta;
import com.example.realestate.domain.dto.request.PropertyTypeRequest;
import com.example.realestate.domain.dto.response.PropertyTypeResponse;
import com.example.realestate.service.PropertyTypeService;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Log4j2
@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PropertyTypeController {
    PropertyTypeService propertyTypeService;

    @Operation(
            summary = "Create a new property type",
            description = "Create a new property type under the specified project.",
            tags = {"Property Type"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Property type created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = GlobalResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data",
                    content = @Content(mediaType = "application/json")
            )
    })
    @PostMapping(Endpoint.V1.PropertyType.CREATE_PROPERTY)
    public ResponseEntity<GlobalResponse<Meta, PropertyTypeResponse>> createProperty(
            @PathVariable(name = "projectId") UUID uuid, @RequestBody PropertyTypeRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(propertyTypeService.createProperty(uuid, request));
    }

    @Operation(
            summary = "Get all property types for a project",
            description = "Retrieve all property types associated with the given project ID.",
            tags = {"Property Type"}
    )
    @ApiResponse(
            responseCode = "200",
            description = "List of property types retrieved successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = GlobalResponse.class))
    )
    @GetMapping(Endpoint.V1.PropertyType.GET_PROPERTIES)
    public ResponseEntity<GlobalResponse<Meta, List<PropertyTypeResponse>>> getProperties(
            @PathVariable(name = "projectId") UUID id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(propertyTypeService.getProperties(id));
    }

    @Operation(
            summary = "Get a specific property type by ID",
            description = "Retrieve the details of a specific property type by its ID in a given project.",
            tags = {"Property Type"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Property type retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = GlobalResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Property type not found",
                    content = @Content(mediaType = "application/json")
            )
    })
    @GetMapping(Endpoint.V1.PropertyType.GET_PROPERTY)
    public ResponseEntity<GlobalResponse<Meta, PropertyTypeResponse>> getProperty(
            @PathVariable(name = "projectId") UUID projectId,
            @PathVariable(name = "propertyId") UUID propertyID) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(propertyTypeService.getProperty(projectId, propertyID));
    }

    @Operation(
            summary = "Delete a property type",
            description = "Delete a specific property type by its ID within a project.",
            tags = {"Property Type"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Property type deleted successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = GlobalResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Property type not found",
                    content = @Content(mediaType = "application/json")
            )
    })
    @DeleteMapping(Endpoint.V1.PropertyType.DELETE_PROPERTY)
    public ResponseEntity<GlobalResponse<Meta, String>> deleteProperty(
            @PathVariable(name = "propertyId") UUID propertyID) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(propertyTypeService.deleteProperty(propertyID));
    }

    @Operation(
            summary = "Update a property type",
            description = "Update the details of an existing property type in the specified project.",
            tags = {"Property Type"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Property type updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = GlobalResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Property type not found",
                    content = @Content(mediaType = "application/json")
            )
    })
    @PutMapping(Endpoint.V1.PropertyType.UPDATE_PROPERTY)
    public ResponseEntity<GlobalResponse<Meta, PropertyTypeResponse>> updateProperty(
            @PathVariable(name = "propertyId") UUID propertyID,
            @RequestBody PropertyTypeRequest request) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(propertyTypeService.updateProperty(propertyID, request));
    }
}
