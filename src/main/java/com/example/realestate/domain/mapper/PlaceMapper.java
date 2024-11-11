package com.example.realestate.domain.mapper;
/*
 * @author HongAnh
 * @created 10 / 11 / 2024 - 10:17 CH
 * @project real-estate
 * @social Github: https://github.com/lehonganh0201
 * @social Facebook: https://www.facebook.com/profile.php?id=100047152174225
 */

import com.example.realestate.domain.dto.request.PlaceRequest;
import com.example.realestate.domain.dto.response.PlaceResponse;
import com.example.realestate.domain.entity.Place;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface PlaceMapper {
    PlaceMapper INSTANCE = Mappers.getMapper(PlaceMapper.class);

    @Mapping(source = "category", target = "category", qualifiedByName = "uppercaseCategory")
    Place toPlace(PlaceRequest request);

    PlaceResponse toPlaceResponse(Place place);

    void updatePlace(PlaceRequest request, @MappingTarget Place place);

    @Named("uppercaseCategory")
    default String uppercaseCategory(String category) {
        return category != null ? category.toUpperCase() : null;
    }
}
