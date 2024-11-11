package com.example.realestate.security.jwt;
/*
 * @author HongAnh
 * @created 07 / 11 / 2024 - 11:06 CH
 * @project real-estate
 * @social Github: https://github.com/lehonganh0201
 * @social Facebook: https://www.facebook.com/profile.php?id=100047152174225
 */

import com.example.realestate.constant.ErrorMessage;
import com.example.realestate.constant.Status;
import com.example.realestate.domain.dto.global.GlobalResponse;
import com.example.realestate.domain.dto.global.Meta;
import com.example.realestate.util.JwtUtils;
import com.example.realestate.util.MessageSourceUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Log4j2
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JwtAuthEntryPoint implements AuthenticationEntryPoint {
    ObjectMapper      objectMapper;
    MessageSourceUtil messageSourceUtil;
    JwtUtils          jwtUtils;


    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        final String BEARER_PREFIX = "Bearer ";
        final String authHeader = request.getHeader("Authorization");

        String message = null;
        if (authHeader == null || authHeader.isBlank()) {
            message = ErrorMessage.Auth.ERR_NOT_LOGIN;
        }

        if (authHeader != null && !authHeader.startsWith(BEARER_PREFIX)) {
            message = ErrorMessage.Auth.ERR_MISSING_PREFIX;
        }

        if (message == null) {
            message = jwtUtils.checkToken(authHeader.substring(BEARER_PREFIX.length()));
        }

        if (message == null) {
            message = ErrorMessage.Auth.ERR_INVALID_TOKEN;
        }
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        GlobalResponse<Meta, Void> responseBody = GlobalResponse.<Meta, Void>builder()
                                                                .meta(Meta.builder()
                                                                          .status(Status.ERROR)
                                                                          .message(messageSourceUtil.getLocalizedMessage(message))
                                                                          .build()
                                                                )
                                                                .build();

        objectMapper.writeValue(response.getOutputStream(), responseBody);
    }
}
