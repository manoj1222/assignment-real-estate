package com.assignment.real.estate.dto;

import lombok.Data;

@Data
public class FindPropertyResponseDTO {

    private long id;
    private long numOfBedrooms;
    private long areaOfEachBedrooms;
    private long numOfBathrooms;
    private long areaOfEachBathrooms;
    private long totalArea;
    private float price;

}
