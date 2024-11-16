package com.example.realestate.domain.dto.stat;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PriceRangeStats {
    String priceRange;
    Long count;
}
