package com.example.realestate.service;
/*
 * @author HongAnh
 * @created 07 / 11 / 2024 - 11:45 CH
 * @project real-estate
 * @social Github: https://github.com/lehonganh0201
 * @social Facebook: https://www.facebook.com/profile.php?id=100047152174225
 */

import com.example.realestate.domain.dto.global.GlobalResponse;
import com.example.realestate.domain.dto.global.Meta;
import com.example.realestate.domain.dto.request.UserRequest;
import com.example.realestate.domain.dto.response.UserResponse;

import java.util.List;
import java.util.UUID;

public interface UserService {
    GlobalResponse<Meta, UserResponse> createUser(UserRequest request);

    GlobalResponse<Meta, UserResponse> getUser(String username);

    GlobalResponse<Meta, List<UserResponse>> getUsers();

    GlobalResponse<Meta, UserResponse> getUser(UUID id);

    GlobalResponse<Meta, UserResponse> updateUser(UUID uuid, UserRequest request);

    GlobalResponse<Meta, String> deleteUser(UUID id);
}
