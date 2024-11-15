package com.example.realestate.domain.dto.response;

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
public class PriceResponse {
    Integer id;
    Integer unit;
    Integer year;
    BigDecimal percent;
    BigDecimal price;
    UUID projectID;
}
