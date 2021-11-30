package com.assignment.real.estate.web;

import com.assignment.real.estate.dto.APIError;
import com.assignment.real.estate.dto.AddPropertyRequestDTO;
import com.assignment.real.estate.dto.FindPropertyRequestDTO;
import com.assignment.real.estate.dto.FindPropertyResponseDTO;
import com.assignment.real.estate.service.RealEstateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
public class RealEstateController {

    private final RealEstateService realEstateService;

    public RealEstateController(final RealEstateService realEstateService) {
        this.realEstateService = realEstateService;
    }

    @PostMapping(value = "/property")
    public APIError addProperty(@RequestBody @Valid final AddPropertyRequestDTO requestDTO) {
        final long id = realEstateService.addProperty(requestDTO);
        return new APIError(HttpStatus.OK, "Property Added Successfully with id: " + id);
    }

    @GetMapping(value = "/properties")
    public List<FindPropertyResponseDTO> findProperty(@RequestBody @Valid final FindPropertyRequestDTO requestDTO) {
        return realEstateService.findProperties(requestDTO);
    }

}

