package com.example.realestate.service;
/*
 * @author HongAnh
 * @created 06 / 11 / 2024 - 11:54 SA
 * @project real-estate
 * @social Github: https://github.com/lehonganh0201
 * @social Facebook: https://www.facebook.com/profile.php?id=100047152174225
 */

import com.example.realestate.domain.dto.global.GlobalResponse;
import com.example.realestate.domain.dto.global.Meta;
import com.example.realestate.domain.dto.request.AuthRequest;
import com.example.realestate.domain.dto.response.AuthResponse;

public interface AuthService {
    GlobalResponse<Meta, AuthResponse> login(AuthRequest request);
}
