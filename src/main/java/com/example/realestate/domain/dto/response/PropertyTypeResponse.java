package com.example.realestate.domain.dto.response;
/*
 * @author HongAnh
 * @created 10 / 11 / 2024 - 2:53 CH
 * @project real-estate
 * @social Github: https://github.com/lehonganh0201
 * @social Facebook: https://www.facebook.com/profile.php?id=100047152174225
 */

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PropertyTypeResponse {
    UUID id;
    BigDecimal numberOfBedroom;
    BigDecimal minArea;
    BigDecimal maxArea;
    BigDecimal minPrice;
    BigDecimal maxPrice;
    Timestamp  createdAt;
}
