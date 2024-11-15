package com.example.realestate.service;
/*
 * @author HongAnh
 * @created 10 / 11 / 2024 - 9:04 CH
 * @project real-estate
 * @social Github: https://github.com/lehonganh0201
 * @social Facebook: https://www.facebook.com/profile.php?id=100047152174225
 */

import com.example.realestate.domain.dto.global.GlobalResponse;
import com.example.realestate.domain.dto.global.Meta;
import com.example.realestate.domain.dto.request.PlaceRequest;
import com.example.realestate.domain.dto.response.PlaceResponse;

import java.util.List;
import java.util.UUID;

public interface PlaceService {
    GlobalResponse<Meta, PlaceResponse> createPlace(UUID id, PlaceRequest request);

//    GlobalResponse<Meta, List<PlaceResponse>> importPlace();

    GlobalResponse<Meta, List<PlaceResponse>> getPlaces(UUID id, int page, int size);

    GlobalResponse<Meta, PlaceResponse> getPlace(UUID projectId, UUID placeId);

    GlobalResponse<Meta, String> deletePlace(UUID placeId);

    GlobalResponse<Meta, PlaceResponse> updatePlace(UUID placeId, PlaceRequest request);
}
