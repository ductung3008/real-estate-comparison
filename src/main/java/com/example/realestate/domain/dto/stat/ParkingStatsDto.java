package com.example.realestate.domain.dto.stat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ParkingStatsDto {
    private List<BikeParkingCategoryDto> bikeParkingStats;
    private List<CarParkingCategoryDto> carParkingStats;
}
