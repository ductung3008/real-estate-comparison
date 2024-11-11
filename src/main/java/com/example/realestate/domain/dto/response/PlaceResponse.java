package com.example.realestate.domain.dto.response;
/*
 * @author HongAnh
 * @created 10 / 11 / 2024 - 9:06 CH
 * @project real-estate
 * @social Github: https://github.com/lehonganh0201
 * @social Facebook: https://www.facebook.com/profile.php?id=100047152174225
 */

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PlaceResponse {
    UUID       id;
    String     name;
    BigDecimal latitude;
    BigDecimal longitude;
    BigDecimal distance;
    BigDecimal rating;
    String     category;
}
