package com.example.realestate.service;

import com.example.realestate.domain.dto.global.GlobalResponse;
import com.example.realestate.domain.dto.global.Meta;
import com.example.realestate.domain.dto.response.PriceResponse;

import java.util.List;
import java.util.UUID;

public interface PriceService {
    GlobalResponse<Meta, List<PriceResponse>> getPriceByProject(UUID id);

    GlobalResponse<Meta, List<PriceResponse>> importPrice(String path);
}
