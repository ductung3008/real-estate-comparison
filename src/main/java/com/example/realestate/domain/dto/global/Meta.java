package com.example.realestate.domain.dto.global;
/*
 * @author HongAnh
 * @created 06 / 11 / 2024 - 11:56 SA
 * @project real-estate
 * @social Github: https://github.com/lehonganh0201
 * @social Facebook: https://www.facebook.com/profile.php?id=100047152174225
 */

import com.example.realestate.constant.Status;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Meta {
    Status status;
    String message;
}