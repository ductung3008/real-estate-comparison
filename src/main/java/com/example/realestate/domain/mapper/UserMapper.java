package com.example.realestate.domain.mapper;
/*
 * @author HongAnh
 * @created 07 / 11 / 2024 - 10:39 CH
 * @project real-estate
 * @social Github: https://github.com/lehonganh0201
 * @social Facebook: https://www.facebook.com/profile.php?id=100047152174225
 */

import com.example.realestate.domain.dto.request.UserRequest;
import com.example.realestate.domain.dto.response.UserResponse;
import com.example.realestate.domain.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User toUser(UserRequest request);

    UserResponse toUserResponse(User user);

    void updateUser(UserRequest request, @MappingTarget User user);
}
