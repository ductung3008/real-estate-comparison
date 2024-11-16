package com.example.realestate.service;

import com.example.realestate.domain.dto.global.GlobalResponse;
import com.example.realestate.domain.dto.global.Meta;
import com.example.realestate.domain.dto.stat.*;

import java.util.List;
import java.util.Map;

public interface StatsService {
    GlobalResponse<Meta, List<PriceRangeStats>> priceStat();

    GlobalResponse<Meta, List<DistrictStats>> districtStat();

    GlobalResponse<Meta, List<AreaCategoryStats>> areaStat();

    GlobalResponse<Meta, ParkingStatsDto> parkingStat();
}
