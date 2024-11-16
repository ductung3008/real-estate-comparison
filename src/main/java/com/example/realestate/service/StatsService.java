package com.example.realestate.service;

import com.example.realestate.domain.dto.global.GlobalResponse;
import com.example.realestate.domain.dto.global.Meta;
import com.example.realestate.domain.dto.stat.AreaCategoryStats;
import com.example.realestate.domain.dto.stat.DistrictStats;
import com.example.realestate.domain.dto.stat.ParkingStats;
import com.example.realestate.domain.dto.stat.PriceRangeStats;

import java.util.List;

public interface StatsService {
    GlobalResponse<Meta, List<PriceRangeStats>> priceStat();

    GlobalResponse<Meta, List<DistrictStats>> districtStat();

    GlobalResponse<Meta, List<AreaCategoryStats>> areaStat();

    GlobalResponse<Meta, List<ParkingStats>> parkingStat();
}
