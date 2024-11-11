package com.example.realestate.domain.mapper;
/*
 * @author HongAnh
 * @created 10 / 11 / 2024 - 3:41 CH
 * @project real-estate
 * @social Github: https://github.com/lehonganh0201
 * @social Facebook: https://www.facebook.com/profile.php?id=100047152174225
 */

import com.example.realestate.domain.dto.request.PropertyTypeRequest;
import com.example.realestate.domain.dto.response.PropertyTypeResponse;
import com.example.realestate.domain.entity.PropertyType;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PropertyTypeMapper {
    PropertyTypeMapper INSTANCE = Mappers.getMapper(PropertyTypeMapper.class);

    PropertyType toPropertyType(PropertyTypeRequest request);

    PropertyTypeResponse PropertyTypeResponse(PropertyType propertyType);

    void updateProperty(PropertyTypeRequest request, @MappingTarget PropertyType propertyType);
}
