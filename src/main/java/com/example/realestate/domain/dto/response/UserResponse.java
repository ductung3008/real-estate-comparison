package com.example.realestate.domain.dto.response;
/*
 * @author HongAnh
 * @created 07 / 11 / 2024 - 10:42 CH
 * @project real-estate
 * @social Github: https://github.com/lehonganh0201
 * @social Facebook: https://www.facebook.com/profile.php?id=100047152174225
 */

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    UUID id;
    String username;
    String fullName;
}
