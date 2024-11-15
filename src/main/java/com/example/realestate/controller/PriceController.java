package com.example.realestate.controller;

import com.example.realestate.constant.Endpoint;
import com.example.realestate.domain.dto.global.GlobalResponse;
import com.example.realestate.domain.dto.global.Meta;
import com.example.realestate.domain.dto.request.PathRequest;
import com.example.realestate.domain.dto.response.PriceResponse;
import com.example.realestate.service.PriceService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Log4j2
@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PriceController {
    PriceService priceService;

    @GetMapping(Endpoint.V1.Price.GET_PRICE_BY_PROJECT)
    public ResponseEntity<GlobalResponse<Meta, List<PriceResponse>>> getPrices(@PathVariable(name = "projectId")UUID id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(priceService.getPriceByProject(id));
    }

    @PostMapping(Endpoint.V1.Price.IMPORT_PRICE)
    public ResponseEntity<GlobalResponse<Meta, List<PriceResponse>>> importPrice(@RequestBody PathRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(priceService.importPrice(request.getPath()));
    }
}
