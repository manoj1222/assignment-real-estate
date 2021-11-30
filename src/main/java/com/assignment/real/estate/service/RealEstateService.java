package com.assignment.real.estate.service;

import com.assignment.real.estate.dto.AddPropertyRequestDTO;
import com.assignment.real.estate.dto.FindPropertyRequestDTO;
import com.assignment.real.estate.dto.FindPropertyResponseDTO;

import java.util.List;

public interface RealEstateService {

    long addProperty(AddPropertyRequestDTO requestDTO);

    List<FindPropertyResponseDTO> findProperties(FindPropertyRequestDTO requestDTO);

}
