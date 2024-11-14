package com.example.realestate.service;
/*
 * @author HongAnh
 * @created 08 / 11 / 2024 - 1:46 CH
 * @project real-estate
 * @social Github: https://github.com/lehonganh0201
 * @social Facebook: https://www.facebook.com/profile.php?id=100047152174225
 */

import com.example.realestate.domain.dto.global.GlobalResponse;
import com.example.realestate.domain.dto.global.Meta;
import com.example.realestate.domain.dto.request.ProjectRequest;
import com.example.realestate.domain.dto.response.ProjectResponse;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface ProjectService {
    GlobalResponse<Meta, ProjectResponse> createProject(String username, ProjectRequest request);

    GlobalResponse<Meta, List<ProjectResponse>> importProject() throws JsonProcessingException, IOException;

    GlobalResponse<Meta, List<ProjectResponse>> getProjects(int page, int size);

    GlobalResponse<Meta, ProjectResponse> getProject(UUID id);

    GlobalResponse<Meta, String> deleteProject(UUID id);

    GlobalResponse<Meta, ProjectResponse> updateProject(UUID id, ProjectRequest request);
}
