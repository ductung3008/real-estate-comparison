package com.example.realestate.service.impl;
/*
 * @author HongAnh
 * @created 10 / 11 / 2024 - 2:55 CH
 * @project real-estate
 * @social Github: https://github.com/lehonganh0201
 * @social Facebook: https://www.facebook.com/profile.php?id=100047152174225
 */

import com.example.realestate.constant.ErrorMessage;
import com.example.realestate.constant.Status;
import com.example.realestate.domain.dto.global.GlobalResponse;
import com.example.realestate.domain.dto.global.Meta;
import com.example.realestate.domain.dto.request.PropertyTypeRequest;
import com.example.realestate.domain.dto.response.PropertyTypeResponse;
import com.example.realestate.domain.entity.Project;
import com.example.realestate.domain.entity.PropertyType;
import com.example.realestate.domain.mapper.PropertyTypeMapper;
import com.example.realestate.exception.NotFoundException;
import com.example.realestate.repository.ProjectRepository;
import com.example.realestate.repository.PropertyTypeRepository;
import com.example.realestate.service.PropertyTypeService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PropertyTypeServiceImpl implements PropertyTypeService {
    PropertyTypeRepository propertyTypeRepository;
    ProjectRepository projectRepository;

    @Override
    public GlobalResponse<Meta, PropertyTypeResponse> createProperty(UUID uuid, PropertyTypeRequest request) {
        Project project = projectRepository.findById(uuid)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Project.ERR_NOT_FOUND_BY_ID));

        PropertyType propertyType = PropertyTypeMapper.INSTANCE.toPropertyType(request);
        propertyType.setProject(project);

        propertyType = propertyTypeRepository.save(propertyType);

        PropertyTypeResponse response = PropertyTypeMapper.INSTANCE.PropertyTypeResponse(propertyType);

        return GlobalResponse.<Meta, PropertyTypeResponse>builder()
                .meta(Meta.builder().status(Status.SUCCESS).build())
                .data(response)
                .build();
    }

    @Override
    public GlobalResponse<Meta, List<PropertyTypeResponse>> getProperties(UUID id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Project.ERR_NOT_FOUND_BY_ID));

        List<PropertyTypeResponse> responses = project.getTypes()
                .stream().map(PropertyTypeMapper.INSTANCE::PropertyTypeResponse)
                .collect(Collectors.toList());

        return GlobalResponse.<Meta, List<PropertyTypeResponse>>builder()
                .meta(Meta.builder().status(Status.SUCCESS).build())
                .data(responses)
                .build();
    }

    @Override
    public GlobalResponse<Meta, PropertyTypeResponse> getProperty(UUID projectId, UUID propertyID) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Project.ERR_NOT_FOUND_BY_ID));

        for (var item : project.getTypes()) {
            if (item.getId().equals(propertyID)) {
                PropertyTypeResponse response = PropertyTypeMapper.INSTANCE.PropertyTypeResponse(item);
                return GlobalResponse.<Meta, PropertyTypeResponse>builder()
                        .meta(Meta.builder().status(Status.SUCCESS).build())
                        .data(response)
                        .build();
            }
        }
        throw new NotFoundException(ErrorMessage.PropertyType.ERR_NOT_FOUND_IN_PROJECT);
    }

    @Override
    public GlobalResponse<Meta, String> deleteProperty(UUID propertyID) {
        PropertyType propertyType = propertyTypeRepository.findById(propertyID)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.PropertyType.ERR_NOT_FOUND_BY_ID));

        propertyTypeRepository.deleteById(propertyID);

        return GlobalResponse.<Meta, String>builder()
                .meta(Meta.builder().status(Status.SUCCESS).build())
                .data("Delete property success!")
                .build();
    }

    @Override
    public GlobalResponse<Meta, PropertyTypeResponse> updateProperty(UUID propertyID, PropertyTypeRequest request) {
        PropertyType propertyType = propertyTypeRepository.findById(propertyID)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.PropertyType.ERR_NOT_FOUND_BY_ID));

        PropertyTypeMapper.INSTANCE.updateProperty(request, propertyType);

        propertyType = propertyTypeRepository.save(propertyType);

        PropertyTypeResponse response = PropertyTypeMapper.INSTANCE.PropertyTypeResponse(propertyType);


        return GlobalResponse.<Meta, PropertyTypeResponse>builder()
                .meta(Meta.builder().status(Status.SUCCESS).build())
                .data(response)
                .build();
    }
}
