package com.example.realestate.domain.dto.request;
/*
 * @author HongAnh
 * @created 10 / 11 / 2024 - 2:54 CH
 * @project real-estate
 * @social Github: https://github.com/lehonganh0201
 * @social Facebook: https://www.facebook.com/profile.php?id=100047152174225
 */

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true, allowGetters = true, allowSetters = true)
public class PropertyTypeRequest {
    BigDecimal numberOfBedroom;

    BigDecimal minArea;

    BigDecimal maxArea;

    BigDecimal minPrice;

    BigDecimal maxPrice;
}
