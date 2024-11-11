package com.example.realestate.service.impl;
/*
 * @author HongAnh
 * @created 07 / 11 / 2024 - 11:45 CH
 * @project real-estate
 * @social Github: https://github.com/lehonganh0201
 * @social Facebook: https://www.facebook.com/profile.php?id=100047152174225
 */

import com.example.realestate.constant.ErrorMessage;
import com.example.realestate.constant.Status;
import com.example.realestate.domain.dto.global.GlobalResponse;
import com.example.realestate.domain.dto.global.Meta;
import com.example.realestate.domain.dto.request.UserRequest;
import com.example.realestate.domain.dto.response.UserResponse;
import com.example.realestate.domain.entity.User;
import com.example.realestate.domain.mapper.UserMapper;
import com.example.realestate.exception.BadRequestException;
import com.example.realestate.exception.NotFoundException;
import com.example.realestate.repository.UserRepository;
import com.example.realestate.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@Log4j2
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {
    UserRepository  userRepository;
    PasswordEncoder passwordEncoder;

    @Override
    public GlobalResponse<Meta, UserResponse> createUser(UserRequest request) {
        User user = UserMapper.INSTANCE.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        user = userRepository.save(user);

        UserResponse response = UserMapper.INSTANCE.toUserResponse(user);

        return GlobalResponse.<Meta, UserResponse>builder()
                             .meta(Meta.builder().status(Status.SUCCESS).build())
                             .data(response)
                             .build();
    }

    @Override
    public GlobalResponse<Meta, UserResponse> getUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.User.ERR_NOT_FOUND_BY_USERNAME));

        UserResponse response = UserMapper.INSTANCE.toUserResponse(user);

        return GlobalResponse.<Meta, UserResponse>builder()
                             .meta(Meta.builder().status(Status.SUCCESS).build())
                             .data(response)
                             .build();
    }

    @Override
    public GlobalResponse<Meta, List<UserResponse>> getUsers() {
        List<UserResponse> responses = userRepository.findAll()
                                                     .stream()
                                                     .map(user -> UserMapper.INSTANCE.toUserResponse(user))
                                                     .toList();

        return GlobalResponse.<Meta, List<UserResponse>>builder()
                             .meta(Meta.builder().status(Status.SUCCESS).build())
                             .data(responses)
                             .build();
    }

    @Override
    public GlobalResponse<Meta, UserResponse> getUser(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.User.ERR_NOT_FOUND_BY_ID));

        UserResponse response = UserMapper.INSTANCE.toUserResponse(user);

        return GlobalResponse.<Meta, UserResponse>builder()
                             .meta(Meta.builder().status(Status.SUCCESS).build())
                             .data(response)
                             .build();
    }

    @Override
    public GlobalResponse<Meta, UserResponse> updateUser(UUID uuid, UserRequest request) {
        if (!Objects.isNull(request.getUsername())) {
            throw new BadRequestException(ErrorMessage.User.ERR_CANNOT_UPDATE_USERNAME);
        }

        User user = userRepository.findById(uuid)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.User.ERR_NOT_FOUND_BY_ID));

        UserMapper.INSTANCE.updateUser(request, user);

        if (StringUtils.hasText(request.getPassword())) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        user = userRepository.save(user);

        UserResponse response = UserMapper.INSTANCE.toUserResponse(user);

        return GlobalResponse.<Meta, UserResponse>builder()
                             .meta(Meta.builder().status(Status.SUCCESS).build())
                             .data(response)
                             .build();
    }

    @Override
    public GlobalResponse<Meta, String> deleteUser(UUID id) {
        userRepository.deleteById(id);

        return GlobalResponse.<Meta, String>builder()
                             .meta(Meta.builder().status(Status.SUCCESS).build())
                             .data("Delete user successfully!")
                             .build();
    }
}
