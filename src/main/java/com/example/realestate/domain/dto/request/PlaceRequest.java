package com.example.realestate.domain.dto.request;
/*
 * @author HongAnh
 * @created 10 / 11 / 2024 - 9:06 CH
 * @project real-estate
 * @social Github: https://github.com/lehonganh0201
 * @social Facebook: https://www.facebook.com/profile.php?id=100047152174225
 */

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
public class PlaceRequest {
    String     name;
    BigDecimal latitude;
    BigDecimal longitude;
    BigDecimal distance;
    BigDecimal rating;
    String     category;
}
