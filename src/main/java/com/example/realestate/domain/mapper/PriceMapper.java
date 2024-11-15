package com.example.realestate.domain.mapper;

import com.example.realestate.domain.dto.request.PriceRequest;
import com.example.realestate.domain.dto.response.PriceResponse;
import com.example.realestate.domain.entity.Price;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PriceMapper {
    PriceMapper INSTANCE = Mappers.getMapper(PriceMapper.class);

    PriceResponse toPriceResponse(Price price);

    Price toPrice(PriceRequest request);
}
