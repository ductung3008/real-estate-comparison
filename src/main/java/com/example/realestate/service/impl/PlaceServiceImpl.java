package com.example.realestate.service.impl;
/*
 * @author HongAnh
 * @created 10 / 11 / 2024 - 10:15 CH
 * @project real-estate
 * @social Github: https://github.com/lehonganh0201
 * @social Facebook: https://www.facebook.com/profile.php?id=100047152174225
 */

import com.example.realestate.constant.ErrorMessage;
import com.example.realestate.constant.Status;
import com.example.realestate.domain.dto.global.GlobalResponse;
import com.example.realestate.domain.dto.global.Meta;
import com.example.realestate.domain.dto.request.PlaceRequest;
import com.example.realestate.domain.dto.response.PlaceResponse;
import com.example.realestate.domain.entity.Place;
import com.example.realestate.domain.entity.Project;
import com.example.realestate.domain.mapper.PlaceMapper;
import com.example.realestate.exception.NotFoundException;
import com.example.realestate.repository.PlaceRepository;
import com.example.realestate.repository.ProjectRepository;
import com.example.realestate.service.PlaceService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PlaceServiceImpl implements PlaceService {
    PlaceRepository   placeRepository;
    ProjectRepository projectRepository;

    @Override
    public GlobalResponse<Meta, PlaceResponse> createPlace(UUID id, PlaceRequest request) {
        Project project = projectRepository.findById(id)
                                           .orElseThrow(() -> new NotFoundException(ErrorMessage.Project.ERR_NOT_FOUND_BY_ID));

        Place place = PlaceMapper.INSTANCE.toPlace(request);
        place.addProject(project);

        place = placeRepository.save(place);

        PlaceResponse response = PlaceMapper.INSTANCE.toPlaceResponse(place);

        return GlobalResponse.<Meta, PlaceResponse>builder()
                             .meta(Meta.builder().status(Status.SUCCESS).build())
                             .data(response)
                             .build();
    }

    @Override
    public GlobalResponse<Meta, List<PlaceResponse>> importPlace() {
        List<PlaceRequest> placeRequests = getPlaces();

        List<Place> places = placeRequests.stream()
                .map(request -> {
                    Place place = PlaceMapper.INSTANCE.toPlace(request);
                    place.addProject(projectRepository.findById(request.getProjectId()).orElse(null));

                    return place;
                }).collect(Collectors.toList());

        places = placeRepository.saveAll(places);

        List<PlaceResponse> responses = places.stream()
                .map(PlaceMapper.INSTANCE::toPlaceResponse)
                .collect(Collectors.toList());

        return GlobalResponse.<Meta, List<PlaceResponse>>builder()
                             .meta(Meta.builder().status(Status.SUCCESS).build())
                             .data(responses)
                             .build();
    }

    @Override
    public GlobalResponse<Meta, List<PlaceResponse>> getPlaces(UUID id, int page, int size) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Project.ERR_NOT_FOUND_BY_ID));

        int limit = size;
        int offset = page * size;

        List<PlaceResponse> responses = placeRepository.findPlacesByProjectWithPagination(id, limit, offset).stream()
                .map(PlaceMapper.INSTANCE::toPlaceResponse)
                .collect(Collectors.toList());

        return GlobalResponse.<Meta, List<PlaceResponse>>builder()
                             .meta(Meta.builder().status(Status.SUCCESS).build())
                             .data(responses)
                             .build();
    }

    @Override
    public GlobalResponse<Meta, PlaceResponse> getPlace(UUID projectId, UUID placeId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Project.ERR_NOT_FOUND_BY_ID));

        for(var item : project.getPlaces()) {
            if (item.getId().equals(placeId)) {
                return GlobalResponse.<Meta, PlaceResponse>builder()
                                     .meta(Meta.builder().status(Status.SUCCESS).build())
                                     .data(PlaceMapper.INSTANCE.toPlaceResponse(item))
                                     .build();
            }
        }
        throw new NotFoundException(ErrorMessage.Place.ERR_NOT_FOUND_IN_PROJECT);
    }

    @Override
    public GlobalResponse<Meta, String> deletePlace(UUID projectId, UUID placeId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Project.ERR_NOT_FOUND_BY_ID));

        for(var item : project.getPlaces()) {
            if (item.getId().equals(placeId)) {
                project.getPlaces().remove(item);

                projectRepository.save(project);

                return GlobalResponse.<Meta, String>builder()
                                     .meta(Meta.builder().status(Status.SUCCESS).build())
                                     .data("Delete place success!")
                                     .build();
            }
        }
        throw new NotFoundException(ErrorMessage.Place.ERR_NOT_FOUND_IN_PROJECT);
    }

    @Override
    public GlobalResponse<Meta, PlaceResponse> updatePlace(UUID projectId, UUID placeId, PlaceRequest request) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Project.ERR_NOT_FOUND_BY_ID));

        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Place.ERR_NOT_FOUND_BY_ID));

        for(var item : project.getPlaces()) {
            if (item.getId().equals(placeId)) {
                PlaceMapper.INSTANCE.updatePlace(request, place);

                place = placeRepository.save(place);

                return GlobalResponse.<Meta, PlaceResponse>builder()
                                     .meta(Meta.builder().status(Status.SUCCESS).build())
                                     .data(PlaceMapper.INSTANCE.toPlaceResponse(place))
                                     .build();
            }
        }
        throw new NotFoundException(ErrorMessage.Place.ERR_NOT_FOUND_IN_PROJECT);

    }

    private List<PlaceRequest> getPlaces() {
        ObjectMapper objectMapper = new ObjectMapper();
        List<PlaceRequest> places = new ArrayList<>();

        try {
            JsonNode rootNode = objectMapper.readTree(new File("D:\\workspace\\Java\\real-estate-comparison\\data\\places.json"));
            JsonNode dataNode = rootNode.path("data");
            String category = dataNode.path("category").asText().toUpperCase(Locale.ROOT);

            for (JsonNode detailNode : dataNode.path("details")) {
                PlaceRequest place = PlaceRequest.builder()
                                                .projectId(UUID.fromString(detailNode.path("project_id").asText()))
                                                 .name(detailNode.path("name").asText())
                                                 .latitude(new BigDecimal(detailNode.path("latitude").asText()))
                                                 .longitude(new BigDecimal(detailNode.path("longitude").asText()))
                                                 .distance(new BigDecimal(detailNode.path("distance").asText()))
//                                                 .rating(new BigDecimal(detailNode.path("rating").asText()))
                                                 .category(category)
                                                 .build();

                places.add(place);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return places;
    }
}