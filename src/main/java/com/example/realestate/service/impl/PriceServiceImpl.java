package com.example.realestate.service.impl;

import com.example.realestate.constant.ErrorMessage;
import com.example.realestate.constant.Status;
import com.example.realestate.domain.dto.global.GlobalResponse;
import com.example.realestate.domain.dto.global.Meta;
import com.example.realestate.domain.dto.request.PriceRequest;
import com.example.realestate.domain.dto.response.PriceResponse;
import com.example.realestate.domain.entity.Price;
import com.example.realestate.domain.entity.Project;
import com.example.realestate.domain.mapper.PriceMapper;
import com.example.realestate.exception.NotFoundException;
import com.example.realestate.repository.PriceRepository;
import com.example.realestate.repository.ProjectRepository;
import com.example.realestate.service.PriceService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PriceServiceImpl implements PriceService {
    PriceRepository priceRepository;
    ProjectRepository projectRepository;

    @Override
    public GlobalResponse<Meta, List<PriceResponse>> getPriceByProject(UUID id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Project.ERR_NOT_FOUND_BY_ID));

        List<Price> prices = priceRepository.findAllByProject(project);

        return getMetaListGlobalResponse(id, prices);
    }

    @Override
    public GlobalResponse<Meta, List<PriceResponse>> importPrice(String path) {
        String newPath = path.replace("\\", "/");

        File file = new File(newPath);
        List<PriceRequest> requests = getPriceRequest(file);

        List<Price> prices = requests.stream()
                .map(request -> {
                    Price price = PriceMapper.INSTANCE.toPrice(request);
                    price.setProject(projectRepository.findById(request.getProjectId()).orElse(new Project(request.getProjectId())));

                    return price;
                })
                .collect(Collectors.toList());

        prices = priceRepository.saveAll(prices);

        return getMetaListGlobalResponse(prices);
    }

    private GlobalResponse<Meta, List<PriceResponse>> getMetaListGlobalResponse(List<Price> prices) {
        List<PriceResponse> responses = prices.stream()
                .map(price -> {
                    PriceResponse response = PriceMapper.INSTANCE.toPriceResponse(price);
                    response.setProjectID(price.getProject().getId());

                    return response;
                })
                .collect(Collectors.toList());

        return GlobalResponse.<Meta, List<PriceResponse>>builder()
                .meta(Meta.builder().status(Status.SUCCESS).build())
                .data(responses)
                .build();
    }

    private GlobalResponse<Meta, List<PriceResponse>> getMetaListGlobalResponse(UUID id, List<Price> prices) {
        List<PriceResponse> responses = prices.stream()
                .map(price -> {
                    PriceResponse response = PriceMapper.INSTANCE.toPriceResponse(price);
                    response.setProjectID(id);

                    return response;
                })
                .collect(Collectors.toList());

        return GlobalResponse.<Meta, List<PriceResponse>>builder()
                .meta(Meta.builder().status(Status.SUCCESS).build())
                .data(responses)
                .build();
    }

    private List<PriceRequest> getPriceRequest(File file) {
        ObjectMapper objectMapper = new ObjectMapper();
        List<PriceRequest> requests = new ArrayList<>();

        try {
            requests = objectMapper.readValue(file, new TypeReference<List<PriceRequest>>() {});
        } catch (IOException e) {
            e.printStackTrace();
        }

        return requests;
    }
}
