package com.example.realestate.controller;

import com.example.realestate.constant.Endpoint;
import com.example.realestate.domain.dto.global.GlobalResponse;
import com.example.realestate.domain.dto.global.Meta;
import com.example.realestate.domain.dto.stat.AreaCategoryStats;
import com.example.realestate.domain.dto.stat.DistrictStats;
import com.example.realestate.domain.dto.stat.ParkingStats;
import com.example.realestate.domain.dto.stat.PriceRangeStats;
import com.example.realestate.service.StatsService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Log4j2
@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StatsController {
    StatsService statsService;

    @GetMapping(Endpoint.V1.Stat.PRICE)
    public ResponseEntity<GlobalResponse<Meta, List<PriceRangeStats>>> priceStat() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(statsService.priceStat());
    }

    @GetMapping(Endpoint.V1.Stat.DISTRICT)
    public ResponseEntity<GlobalResponse<Meta, List<DistrictStats>>> districtStat() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(statsService.districtStat());
    }

    @GetMapping(Endpoint.V1.Stat.AREA)
    public ResponseEntity<GlobalResponse<Meta, List<AreaCategoryStats>>> areaStat() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(statsService.areaStat());
    }

    @GetMapping(Endpoint.V1.Stat.PARKING)
    public ResponseEntity<GlobalResponse<Meta, List<ParkingStats>>> parkingStat() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(statsService.parkingStat());
    }
}
