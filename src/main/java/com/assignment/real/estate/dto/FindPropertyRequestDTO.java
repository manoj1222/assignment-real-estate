package com.assignment.real.estate.dto;

import lombok.Data;

import javax.validation.constraints.Min;

@Data
public class FindPropertyRequestDTO {

    @Min(value = 1, message = "price must be greater than zero")
    private float price;

    @Min(value = 1, message = "minReqBedrooms must be greater than zero")
    private long minReqBedrooms;

    @Min(value = 1, message = "minAreaReqForEachBedroom must be greater than zero")
    private long minAreaReqForEachBedroom;

    @Min(value = 1, message = "minReqBathrooms must be greater than zero")
    private long minReqBathrooms;

    @Min(value = 1, message = "minAreaReqForEachBathroom must be greater than zero")
    private long minAreaReqForEachBathroom;

    @Min(value = 1, message = "minTotalArea must be greater than zero")
    private long minTotalArea;

}
