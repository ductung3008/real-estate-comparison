package com.example.realestate.service.impl;
/*
 * @author HongAnh
 * @created 06 / 11 / 2024 - 12:01 CH
 * @project real-estate
 * @social Github: https://github.com/lehonganh0201
 * @social Facebook: https://www.facebook.com/profile.php?id=100047152174225
 */

import com.example.realestate.constant.Status;
import com.example.realestate.domain.dto.global.GlobalResponse;
import com.example.realestate.domain.dto.global.Meta;
import com.example.realestate.domain.dto.request.AuthRequest;
import com.example.realestate.domain.dto.response.AuthResponse;
import com.example.realestate.repository.UserRepository;
import com.example.realestate.service.AuthService;
import com.example.realestate.util.JwtUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthServiceImpl implements AuthService {
    UserRepository        userRepository;
    PasswordEncoder       passwordEncoder;
    AuthenticationManager authenticationManager;
    JwtUtils              jwtUtils;

    @Override
    public GlobalResponse<Meta, AuthResponse> login(AuthRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        String accessToken = jwtUtils.generateJwtTokenForUser(authentication);

        AuthResponse response = AuthResponse.builder()
                                            .token(accessToken).build();

        return GlobalResponse.<Meta, AuthResponse>builder()
                             .meta(Meta.builder().status(Status.SUCCESS).build())
                             .data(response)
                             .build();
    }
}
