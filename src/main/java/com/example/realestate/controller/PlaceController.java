package com.example.realestate.controller;

import com.example.realestate.constant.Endpoint;
import com.example.realestate.domain.dto.global.GlobalResponse;
import com.example.realestate.domain.dto.global.Meta;
import com.example.realestate.domain.dto.request.PlaceRequest;
import com.example.realestate.domain.dto.response.PlaceResponse;
import com.example.realestate.service.PlaceService;
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
public class PlaceController {

    PlaceService placeService;

    @Operation(
            summary = "Create a new place",
            description = "Create a new place within a specific project.",
            tags = {"Place"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Place created successfully",
                    content = @Content(mediaType = "application/json",
                                       schema = @Schema(implementation = GlobalResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Project not found",
                    content = @Content(mediaType = "application/json")
            )
    })
    @PostMapping(Endpoint.V1.Place.CREATE_PLACE)
    public ResponseEntity<GlobalResponse<Meta, PlaceResponse>> createPlace(@PathVariable(name = "projectId") UUID id,
                                                                           @RequestBody PlaceRequest request) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(placeService.createPlace(id, request));
    }

//    @Operation(
//            summary = "Import places",
//            description = "Import places for a specific project.",
//            tags = {"Place"}
//    )
//    @ApiResponses(value = {
//            @ApiResponse(
//                    responseCode = "201",
//                    description = "Places imported successfully",
//                    content = @Content(mediaType = "application/json",
//                                       schema = @Schema(implementation = GlobalResponse.class))
//            ),
//            @ApiResponse(
//                    responseCode = "404",
//                    description = "Project not found",
//                    content = @Content(mediaType = "application/json")
//            )
//    })
//    @PostMapping(Endpoint.V1.Place.IMPORT_PLACE)
//    public ResponseEntity<GlobalResponse<Meta, List<PlaceResponse>>> importPlace() {
//        return ResponseEntity
//                .status(HttpStatus.CREATED)
//                .body(placeService.importPlace());
//    }

    @Operation(
            summary = "Get all places",
            description = "Retrieve all places for a specific project.",
            tags = {"Place"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "List of places retrieved successfully",
                    content = @Content(mediaType = "application/json",
                                       schema = @Schema(implementation = GlobalResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Project not found",
                    content = @Content(mediaType = "application/json")
            )
    })
    @GetMapping(Endpoint.V1.Place.GET_PLACES)
    public ResponseEntity<GlobalResponse<Meta, List<PlaceResponse>>> getPlaces(@PathVariable(name = "projectId") UUID id,
                                                                               @RequestParam(defaultValue = "0") int page,
                                                                               @RequestParam(defaultValue = "1000") int size) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(placeService.getPlaces(id, page, size));
    }

    @Operation(
            summary = "Get a specific place",
            description = "Retrieve details of a specific place within a project.",
            tags = {"Place"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Place details retrieved successfully",
                    content = @Content(mediaType = "application/json",
                                       schema = @Schema(implementation = GlobalResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Place or project not found",
                    content = @Content(mediaType = "application/json")
            )
    })
    @GetMapping(Endpoint.V1.Place.GET_PLACE)
    public ResponseEntity<GlobalResponse<Meta, PlaceResponse>> getPlace(@PathVariable(name = "projectId") UUID projectId,
                                                                        @PathVariable(name = "placeId") UUID placeId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(placeService.getPlace(projectId, placeId));
    }

    @Operation(
            summary = "Delete a place",
            description = "Delete a specific place within a project.",
            tags = {"Place"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Place deleted successfully",
                    content = @Content(mediaType = "application/json",
                                       schema = @Schema(implementation = GlobalResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Place or project not found",
                    content = @Content(mediaType = "application/json")
            )
    })
    @DeleteMapping(Endpoint.V1.Place.DELETE_PLACE)
    public ResponseEntity<GlobalResponse<Meta, String>> deletePlace(@PathVariable(name = "placeId") UUID placeId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(placeService.deletePlace(placeId));
    }

    @Operation(
            summary = "Update a place",
            description = "Update details of a specific place within a project.",
            tags = {"Place"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Place updated successfully",
                    content = @Content(mediaType = "application/json",
                                       schema = @Schema(implementation = GlobalResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Place or project not found",
                    content = @Content(mediaType = "application/json")
            )
    })
    @PutMapping(Endpoint.V1.Place.UPDATE_PLACE)
    public ResponseEntity<GlobalResponse<Meta, PlaceResponse>> updatePlace(@PathVariable(name = "placeId") UUID placeId,
                                                                           @RequestBody PlaceRequest request) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(placeService.updatePlace(placeId, request));
    }
}
