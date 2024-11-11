package com.example.realestate.domain.dto.request;
/*
 * @author HongAnh
 * @created 08 / 11 / 2024 - 1:48 CH
 * @project real-estate
 * @social Github: https://github.com/lehonganh0201
 * @social Facebook: https://www.facebook.com/profile.php?id=100047152174225
 */

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true, allowGetters = true, allowSetters = true)
public class ProjectRequest {
    String     code;

    String     name;

    String     address;

    String     developerName;

    String     masterPlanUrl;

    String     infrastructureMapUrl;

    LocalDate  constructionStartDateFrom;

    LocalDate  handoverDate;

    String     rank;

    BigDecimal totalArea;

    BigDecimal ctsnDens;

    Integer    totalProperty;

    BigDecimal minSellingPrice;

    BigDecimal maxSellingPrice;

    BigDecimal minUnitPrice;

    BigDecimal maxUnitPrice;

    Integer    blocks;

    Integer    numberEle;

    Integer    numberLivingFloor;

    Integer    numberBasement;

    Integer    minPropPerFloor;

    Integer    maxPropPerFloor;

    Integer    bikeParkingMonthly;

    Integer    carParkingMonthly;

    BigDecimal latitude;

    BigDecimal longitude;

    List<PropertyTypeRequest> properties;
}
