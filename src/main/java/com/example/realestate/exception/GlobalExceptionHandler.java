package com.example.realestate.exception;
/*
 * @author HongAnh
 * @created 11 / 11 / 2024 - 3:22 CH
 * @project real-estate
 * @social Github: https://github.com/lehonganh0201
 * @social Facebook: https://www.facebook.com/profile.php?id=100047152174225
 */

import com.example.realestate.constant.Status;
import com.example.realestate.domain.dto.global.BlankData;
import com.example.realestate.domain.dto.global.GlobalResponse;
import com.example.realestate.domain.dto.global.Meta;
import com.example.realestate.util.MessageSourceUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Log4j2
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GlobalExceptionHandler {
    MessageSource     messageSource;
    MessageSourceUtil messageSourceUtils;

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<GlobalResponse<Meta, BlankData>> handlerBadRequestException(BadRequestException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(GlobalResponse
                        .<Meta, BlankData>builder()
                        .meta(Meta.builder()
                                  .status(Status.ERROR)
                                  .message(messageSourceUtils.getLocalizedMessage(ex.getMessage()))
                                  .build()
                        )
                        .build()
                );
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<GlobalResponse<Meta, BlankData>> handlerNotFoundException(NotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(GlobalResponse
                        .<Meta, BlankData>builder()
                        .meta(Meta.builder()
                                  .status(Status.ERROR)
                                  .message(messageSourceUtils.getLocalizedMessage(ex.getMessage()))
                                  .build()
                        )
                        .build()
                );
    }
}
