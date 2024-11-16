package com.example.realestate.service.impl;

import com.example.realestate.constant.Status;
import com.example.realestate.domain.dto.global.GlobalResponse;
import com.example.realestate.domain.dto.global.Meta;
import com.example.realestate.domain.dto.stat.AreaCategoryStats;
import com.example.realestate.domain.dto.stat.DistrictStats;
import com.example.realestate.domain.dto.stat.ParkingStats;
import com.example.realestate.domain.dto.stat.PriceRangeStats;
import com.example.realestate.repository.ProjectRepository;
import com.example.realestate.service.StatsService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Log4j2
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StatsServiceImpl implements StatsService {
    ProjectRepository projectRepository;

    @Override
    public GlobalResponse<Meta, List<PriceRangeStats>> priceStat() {
        List<Object[]> results = projectRepository.findPriceRangeStats();
        List<PriceRangeStats> stats = new ArrayList<>();

        for (Object[] result : results) {
            String priceRange = (String) result[0];
            Long count = (Long) result[1];
            stats.add(new PriceRangeStats(priceRange, count));
        }

        List<String> customOrder = Arrays.asList("<2", "2-3", "3-4", "4-5", "5-6", "6-7", "7-8", "8-9", "9-10", ">10");

        stats.sort(Comparator.comparing(item -> customOrder.indexOf(item.getPriceRange())));

        return GlobalResponse.<Meta, List<PriceRangeStats>>builder()
                .meta(Meta.builder().status(Status.SUCCESS).build())
                .meta(Meta.builder().status(Status.SUCCESS).build())
                .data(stats)
                .build();
    }

    @Override
    public GlobalResponse<Meta, List<DistrictStats>> districtStat() {
        List<Object[]> results = projectRepository.findDistrictStats();
        List<DistrictStats> stats = new ArrayList<>();

        for (Object[] result : results) {
            String district = (String) result[0];
            Long count = ((Number) result[1]).longValue();
            stats.add(new DistrictStats(district, count));
        }

        for(var item : stats) {
            if (Objects.isNull(item.getDistrict())) {
                item.setDistrict("Other");
            }
        }

        return GlobalResponse.<Meta, List<DistrictStats>>builder()
                .meta(Meta.builder().status(Status.SUCCESS).build())
                .data(stats)
                .build();
    }

    @Override
    public GlobalResponse<Meta, List<AreaCategoryStats>> areaStat() {
        List<Object[]> results = projectRepository.findAreaCategoryStats();
        List<AreaCategoryStats> stats = new ArrayList<>();

        for (Object[] result : results) {
            String areaCategory = (String) result[0];
            Long projectCount = ((Number) result[1]).longValue();
            stats.add(new AreaCategoryStats(areaCategory, projectCount));
        }

        return GlobalResponse.<Meta, List<AreaCategoryStats>>builder()
                .meta(Meta.builder().status(Status.SUCCESS).build())
                .data(stats)
                .build();
    }

    @Override
    public GlobalResponse<Meta, List<ParkingStats>> parkingStat() {
        List<Object[]> results = projectRepository.findParkingStats();
        List<ParkingStats> stats = new ArrayList<>();

        for (Object[] result : results) {
            String bikeParkingCategory = (String) result[0];
            String carParkingCategory = (String) result[1];
            Long projectCount = ((Number) result[2]).longValue();

            stats.add(new ParkingStats(bikeParkingCategory, carParkingCategory, projectCount));
        }

        return GlobalResponse.<Meta, List<ParkingStats>>builder()
                .meta(Meta.builder().status(Status.SUCCESS).build())
                .data(stats)
                .build();
    }
}
