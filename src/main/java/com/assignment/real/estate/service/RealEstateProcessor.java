package com.assignment.real.estate.service;

import com.assignment.real.estate.dto.FindPropertyRequestDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class RealEstateProcessor {

    public Map<String, Object> doBaseFiltering(final FindPropertyRequestDTO requestDTO) {
        final Map<String, Object> reqMargins = new HashMap<>();
        getMinMaxPrice(requestDTO.getPrice(), reqMargins);
        getMinMaxBedrooms(requestDTO.getMinReqBedrooms(), reqMargins);
        getMinMaxBathrooms(requestDTO.getMinReqBathrooms(), reqMargins);
        return reqMargins;
    }

    private void getMinMaxBathrooms(long minReqBathrooms, Map<String, Object> reqMargins) {
        reqMargins.put("minBaths", Math.max(minReqBathrooms - 1, 1));
        reqMargins.put("maxBaths", Math.max(minReqBathrooms + 1, 2));
    }

    private void getMinMaxBedrooms(long minReqBedrooms, Map<String, Object> reqMargins) {
        reqMargins.put("minBeds", Math.max(minReqBedrooms - 1, 1));
        reqMargins.put("maxBeds", Math.max(minReqBedrooms + 1, 2));
    }

    private void getMinMaxPrice(float price, Map<String, Object> reqMargins) {
        reqMargins.put("minBudget", Math.max(price - (0.25f * price), 1.0f));
        reqMargins.put("maxBudget", Math.max(price + (0.25f * price), 1.25f));
    }

}
