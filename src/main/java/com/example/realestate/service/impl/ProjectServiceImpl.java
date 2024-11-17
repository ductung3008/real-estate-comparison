package com.example.realestate.service.impl;
/*
 * @author HongAnh
 * @created 08 / 11 / 2024 - 1:52 CH
 * @project real-estate
 * @social Github: https://github.com/lehonganh0201
 * @social Facebook: https://www.facebook.com/profile.php?id=100047152174225
 */

import com.example.realestate.constant.ErrorMessage;
import com.example.realestate.constant.Status;
import com.example.realestate.domain.dto.global.GlobalResponse;
import com.example.realestate.domain.dto.global.Meta;
import com.example.realestate.domain.dto.request.ProjectRequest;
import com.example.realestate.domain.dto.request.PropertyTypeRequest;
import com.example.realestate.domain.dto.response.ProjectResponse;
import com.example.realestate.domain.entity.Project;
import com.example.realestate.domain.entity.PropertyType;
import com.example.realestate.domain.entity.User;
import com.example.realestate.domain.mapper.PlaceMapper;
import com.example.realestate.domain.mapper.ProjectMapper;
import com.example.realestate.domain.mapper.PropertyTypeMapper;
import com.example.realestate.exception.NotFoundException;
import com.example.realestate.repository.ProjectRepository;
import com.example.realestate.repository.UserRepository;
import com.example.realestate.service.ProjectService;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.StreamReadFeature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProjectServiceImpl implements ProjectService {
    ProjectRepository projectRepository;
    UserRepository    userRepository;

    @Override
    public GlobalResponse<Meta, ProjectResponse> createProject(String username, ProjectRequest request) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(()-> new NotFoundException(ErrorMessage.User.ERR_NOT_FOUND_BY_USERNAME));

        Project project = ProjectMapper.INSTANCE.toProject(request);
        project.setCreatedBy(user);

        project = projectRepository.save(project);

        ProjectResponse response = ProjectMapper.INSTANCE.toProjectResponse(project);

        return GlobalResponse.<Meta, ProjectResponse>builder()
                             .meta(Meta.builder().status(Status.SUCCESS).build())
                             .data(response)
                             .build();
    }

    @Override
    public GlobalResponse<Meta, List<ProjectResponse>> importProject() throws JsonProcessingException, IOException {
        JsonFactory jsonFactory = JsonFactory.builder()
                                             .enable(StreamReadFeature.INCLUDE_SOURCE_IN_LOCATION)
                                             .build();

        ObjectMapper objectMapper = new ObjectMapper(jsonFactory);
        objectMapper.enable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS);
        objectMapper.registerModule(new JavaTimeModule());

        File file = new File("D:\\workspace\\Java\\real-estate-comparison\\data\\realestate-db.json");
        List<ProjectRequest> requests = getProjectRequests(file);

        List<Project> projects = requests.stream()
                                       .map(request -> {
                                           Project project = ProjectMapper.INSTANCE.toProject(request);

                                           for (var item : request.getProperties()) {
                                               PropertyType type = PropertyTypeMapper.INSTANCE.toPropertyType(item);
                                               project.addProperty(type);
                                           }

                                           return project;
                                       })
                                       .collect(Collectors.toList());

        projects = projectRepository.saveAll(projects);

        List<ProjectResponse> responses = projects.stream()
                                                  .map(project -> {
                                                      ProjectResponse response = ProjectMapper.INSTANCE.toProjectResponse(project);
                                                      if (!Objects.isNull(project.getTypes())) {
                                                          response.setProperties(project.getTypes()
                                                                                        .stream()
                                                                                        .map(PropertyTypeMapper.INSTANCE::PropertyTypeResponse)
                                                                                        .collect(Collectors.toList())
                                                          );
                                                      }

                                                      return response;
                                                  })
                                                  .collect(Collectors.toList());


        // Trả về GlobalResponse với dữ liệu đã được import
        return GlobalResponse.<Meta, List<ProjectResponse>>builder()
                             .meta(Meta.builder().status(Status.SUCCESS).build())
                             .data(responses)
                             .build();
    }

    @Override
    public GlobalResponse<Meta, List<ProjectResponse>> getProjects(int page, int size) {
        int limit = size;
        int offset = page * size;
        List<Project> projects = projectRepository.findProjectsWithPagination(limit, offset);

        List<ProjectResponse> responses = projects.stream()
                                                  .map(project -> {
                                                      ProjectResponse response = ProjectMapper.INSTANCE.toProjectResponse(project);
//                                                      if (!Objects.isNull(project.getTypes())) {
//                                                          response.setProperties(project.getTypes()
//                                                                                        .stream()
//                                                                                        .map(PropertyTypeMapper.INSTANCE::PropertyTypeResponse)
//                                                                                        .collect(Collectors.toList())
//                                                          );
//                                                      }
//                                                      if (!Objects.isNull(project.getPlaces())) {
//                                                          response.setPlaces(project.getPlaces()
//                                                          .stream().map(PlaceMapper.INSTANCE::toPlaceResponse)
//                                                          .collect(Collectors.toList()));
//                                                      }

                                                      return response;
                                                  })
                                                  .collect(Collectors.toList());


        return GlobalResponse.<Meta, List<ProjectResponse>>builder()
                             .meta(Meta.builder().status(Status.SUCCESS).build())
                             .data(responses)
                             .build();
    }

    @Override
    public GlobalResponse<Meta, ProjectResponse> getProject(UUID id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Project.ERR_NOT_FOUND_BY_ID));

        ProjectResponse response = ProjectMapper.INSTANCE.toProjectResponse(project);
//        if (!Objects.isNull(project.getTypes())) {
//            response.setProperties(project.getTypes()
//                                          .stream()
//                                          .map(PropertyTypeMapper.INSTANCE::PropertyTypeResponse)
//                                          .collect(Collectors.toList())
//            );
//        }
//        if (!Objects.isNull(project.getPlaces())) {
//            response.setPlaces(project.getPlaces()
//                                      .stream().map(PlaceMapper.INSTANCE::toPlaceResponse)
//                                      .collect(Collectors.toList()));
//        }

        return GlobalResponse.<Meta, ProjectResponse>builder()
                             .meta(Meta.builder().status(Status.SUCCESS).build())
                             .data(response)
                             .build();
    }

    @Override
    public GlobalResponse<Meta, String> deleteProject(UUID id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Project.ERR_NOT_FOUND_BY_ID));

        project.setTypes(new ArrayList<>());
        projectRepository.deleteById(id);

        return GlobalResponse.<Meta, String>builder()
                             .meta(Meta.builder().status(Status.SUCCESS).build())
                             .data("Delete project success!")
                             .build();
    }

    @Override
    public GlobalResponse<Meta, ProjectResponse> updateProject(UUID id, ProjectRequest request) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Project.ERR_NOT_FOUND_BY_ID));

        ProjectMapper.INSTANCE.updateProject(request, project);

        project = projectRepository.save(project);

        ProjectResponse response = ProjectMapper.INSTANCE.toProjectResponse(project);

        return GlobalResponse.<Meta, ProjectResponse>builder()
                             .meta(Meta.builder()
                                       .status(Status.SUCCESS)
                                       .build())
                             .data(response)
                             .build();
    }

    private List<ProjectRequest> getProjectRequests(File file) {
        JsonFactory jsonFactory = JsonFactory.builder()
                                             .enable(StreamReadFeature.INCLUDE_SOURCE_IN_LOCATION)
                                             .build();

        ObjectMapper objectMapper = new ObjectMapper(jsonFactory);
        objectMapper.enable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS);
        objectMapper.registerModule(new JavaTimeModule());

        List<ProjectRequest> projects = new ArrayList<>();

        try {
            JsonNode rootNode = objectMapper.readTree(file);
            JsonNode dataNode = rootNode.path("data");

            for (JsonNode projectNode : dataNode) {
                LocalDate handoverDate = LocalDate.now();
                String handoverDateStr = projectNode.path("handover_date").asText(null);
                if (handoverDateStr != null && !handoverDateStr.isEmpty()) {
                    try{
                        handoverDate = LocalDate.parse(handoverDateStr);
                    }catch (DateTimeParseException ex) {
                        handoverDate = LocalDate.now();
                    }
                }

                Integer carParkingMonthly = 0;
                JsonNode carParkingNode = projectNode.path("car_parking_monthly");
                if (carParkingNode.isArray() && carParkingNode.size() > 0) {
                    carParkingMonthly = carParkingNode.get(0).asInt();
                }

                Integer bikeParkingMonthly = 0;
                JsonNode bikeParkingNode = projectNode.path("bike_parking_monthly");
                if (bikeParkingNode.isArray() && bikeParkingNode.size() > 0) {
                    bikeParkingMonthly = bikeParkingNode.get(0).asInt();
                }

                BigDecimal minSellingPrice = BigDecimal.valueOf(0);
                BigDecimal maxSellingPrice = BigDecimal.valueOf(0);

                String minSellingPriceText = projectNode.path("min_selling_price").asText();
                if (!minSellingPriceText.isEmpty()) {
                    minSellingPrice = new BigDecimal(minSellingPriceText);
                }

                String maxSellingPriceText = projectNode.path("max_selling_price").asText();
                if (!maxSellingPriceText.isEmpty()) {
                    maxSellingPrice = new BigDecimal(maxSellingPriceText);
                }

                BigDecimal minUnitPrice;
                BigDecimal maxUnitPrice;

                String minUnitPriceText = projectNode.path("min_unit_price").asText();
                minUnitPrice = minUnitPriceText.isEmpty() ? BigDecimal.ZERO : new BigDecimal(minUnitPriceText);

                String maxUnitPriceText = projectNode.path("max_unit_price").asText();
                maxUnitPrice = maxUnitPriceText.isEmpty() ? BigDecimal.ZERO : new BigDecimal(maxUnitPriceText);

                BigDecimal totalArea;
                BigDecimal ctsnDens;

                String totalAreaText = projectNode.path("total_area").asText();
                totalArea = totalAreaText.isEmpty() ? BigDecimal.ZERO : new BigDecimal(totalAreaText);

                String ctsnDensText = projectNode.path("cstn_dens").asText();
                ctsnDens = ctsnDensText.isEmpty() ? BigDecimal.ZERO : new BigDecimal(ctsnDensText);

                LocalDate constructionStartDateFrom;
                String constructionStartDateFromText = projectNode.path("construction_start_date_from").asText();

                try {
                    if (constructionStartDateFromText.isEmpty()) {
                        constructionStartDateFrom = LocalDate.now();
                    } else {
                        constructionStartDateFrom = LocalDate.parse(constructionStartDateFromText);
                    }
                } catch (DateTimeParseException e) {
                    constructionStartDateFrom = LocalDate.now();
                }

                int numberBasement = 0;
                JsonNode numberBasementNode = projectNode.path("number_basement");

                if (numberBasementNode.isArray() && numberBasementNode.size() > 0) {
                    numberBasement = numberBasementNode.get(0).asInt();
                }

                BigDecimal latitude = new BigDecimal("0");
                BigDecimal longitude = new BigDecimal("0");

                JsonNode latNode = projectNode.path("lat_cdnt");
                JsonNode longNode = projectNode.path("long_cdnt");

                if (!latNode.isMissingNode() && !latNode.asText().isEmpty()) {
                    latitude = new BigDecimal(latNode.asText());
                }

                if (!longNode.isMissingNode() && !longNode.asText().isEmpty()) {
                    longitude = new BigDecimal(longNode.asText());
                }

                int numberEle = 0;
                JsonNode numberEleNode = projectNode.path("number_ele");

                if (numberEleNode.isArray() && numberEleNode.size() > 0) {
                    numberEle = numberEleNode.get(0).asInt();
                } else if (!numberEleNode.isMissingNode() && !numberEleNode.asText().isEmpty()) {
                    numberEle = numberEleNode.asInt();
                }

                ProjectRequest project = ProjectRequest.builder()
                                                        .id(UUID.fromString(projectNode.path("id").asText()))
                                                       .code(projectNode.path("code").asText())
                                                       .name(projectNode.path("name").asText())
                                                       .address(projectNode.path("address").asText())
                                                       .developerName(projectNode.path("developer_name").asText())
                                                       .masterPlanUrl(projectNode.path("master_plan").asText())
                                                       .infrastructureMapUrl(projectNode.path("infrastructure_map_url").asText())
                                                       .constructionStartDateFrom(constructionStartDateFrom)
                                                       .handoverDate(handoverDate)
                                                       .rank(projectNode.path("rank").asText())
                                                       .totalArea(totalArea)
                                                       .ctsnDens(ctsnDens)
                                                       .totalProperty(projectNode.path("total_property").asInt())
                                                       .minSellingPrice(minSellingPrice)
                                                       .maxSellingPrice(maxSellingPrice)
                                                       .minUnitPrice(minUnitPrice)
                                                       .maxUnitPrice(maxUnitPrice)
                                                       .blocks(projectNode.path("blocks").asInt())
                                                       .numberEle(numberEle)
                                                       .numberLivingFloor(projectNode.path("number_living_floor").asInt())
                                                       .numberBasement(numberBasement)
                                                       .minPropPerFloor(projectNode.path("min_prop_per_floor").asInt())
                                                       .maxPropPerFloor(projectNode.path("max_prop_per_floor").asInt())
                                                       .bikeParkingMonthly(bikeParkingMonthly)
                                                       .carParkingMonthly(carParkingMonthly)
                                                       .latitude(latitude)
                                                       .longitude(longitude)
                                                       .build();

                // Lấy danh sách properties
                List<PropertyTypeRequest> properties = new ArrayList<>();
                for (JsonNode propertyNode : projectNode.path("insight_by_bedroom")) {
                    BigDecimal minPrice = BigDecimal.valueOf(0);
                    BigDecimal maxPrice = BigDecimal.valueOf(0);

                    String minPriceText = propertyNode.path("min_price").asText();
                    if (!minPriceText.isEmpty()) {
                        minPrice = new BigDecimal(minPriceText);
                    }

                    String maxPriceText = propertyNode.path("max_price").asText();
                    if (!maxPriceText.isEmpty()) {
                        maxPrice = new BigDecimal(maxPriceText);
                    }


                    PropertyTypeRequest property = PropertyTypeRequest.builder()
                                                                      .numberOfBedroom(new BigDecimal(propertyNode.path("number_of_bedroom").asText()))
                                                                      .minArea(new BigDecimal(propertyNode.path("min_carpet_area").asText()))
                                                                      .maxArea(new BigDecimal(propertyNode.path("max_carpet_area").asText()))
                                                                      .minPrice(minPrice)
                                                                      .maxPrice(maxPrice)
                                                                      .build();
                    properties.add(property);
                }
                project.setProperties(properties);

                projects.add(project);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return projects;
    }
}
