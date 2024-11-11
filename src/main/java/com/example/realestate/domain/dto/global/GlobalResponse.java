package com.example.realestate.domain.dto.global;
/*
 * @author HongAnh
 * @created 06 / 11 / 2024 - 11:55 SA
 * @project real-estate
 * @social Github: https://github.com/lehonganh0201
 * @social Facebook: https://www.facebook.com/profile.php?id=100047152174225
 */

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GlobalResponse<Meta, Data> {
    Meta meta;
    Data data;
}
