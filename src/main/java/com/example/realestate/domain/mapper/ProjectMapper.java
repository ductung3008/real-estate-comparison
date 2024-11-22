package com.example.realestate.domain.mapper;
/*
 * @author HongAnh
 * @created 08 / 11 / 2024 - 1:50 CH
 * @project real-estate
 * @social Github: https://github.com/lehonganh0201
 * @social Facebook: https://www.facebook.com/profile.php?id=100047152174225
 */

import com.example.realestate.domain.dto.request.ProjectRequest;
import com.example.realestate.domain.dto.response.ProjectResponse;
import com.example.realestate.domain.entity.Project;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProjectMapper {
    ProjectMapper INSTANCE = Mappers.getMapper(ProjectMapper.class);

    Project toProject(ProjectRequest request);

    @Mapping(target = "places", ignore = true)
    ProjectResponse toProjectResponse(Project project);

    void updateProject(ProjectRequest request, @MappingTarget Project project);

}
