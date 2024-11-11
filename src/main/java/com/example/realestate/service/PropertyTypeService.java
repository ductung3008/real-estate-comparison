package com.example.realestate.service;
/*
 * @author HongAnh
 * @created 10 / 11 / 2024 - 2:50 CH
 * @project real-estate
 * @social Github: https://github.com/lehonganh0201
 * @social Facebook: https://www.facebook.com/profile.php?id=100047152174225
 */

import com.example.realestate.domain.dto.global.GlobalResponse;
import com.example.realestate.domain.dto.global.Meta;
import com.example.realestate.domain.dto.request.PropertyTypeRequest;
import com.example.realestate.domain.dto.response.PropertyTypeResponse;

import java.util.List;
import java.util.UUID;

public interface PropertyTypeService {
    GlobalResponse<Meta, PropertyTypeResponse> createProperty(UUID uuid, PropertyTypeRequest request);

    GlobalResponse<Meta, List<PropertyTypeResponse>> getProperties(UUID id);

    GlobalResponse<Meta, PropertyTypeResponse> getProperty(UUID projectId, UUID propertyID);

    GlobalResponse<Meta, String> deleteProperty(UUID projectId, UUID propertyID);

    GlobalResponse<Meta, PropertyTypeResponse> updateProperty(UUID projectId, UUID propertyID, PropertyTypeRequest request);
}
