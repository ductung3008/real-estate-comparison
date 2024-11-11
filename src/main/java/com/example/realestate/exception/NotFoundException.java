package com.example.realestate.exception;
/*
 * @author HongAnh
 * @created 08 / 11 / 2024 - 1:08 CH
 * @project real-estate
 * @social Github: https://github.com/lehonganh0201
 * @social Facebook: https://www.facebook.com/profile.php?id=100047152174225
 */

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NotFoundException extends RuntimeException {
    String message;
    HttpStatus status;

    public NotFoundException(String message) {
        this.message = message;
        status = HttpStatus.BAD_REQUEST;
    }
}
