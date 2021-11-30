package com.assignment.real.estate.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class AddPropertyRequestDTO {

    @NotBlank(message = "Name cannot be null/empty")
    private String name;

    @NotBlank(message = "Name cannot be null/empty")
    @Size(min = 4, max = 60, message = "email min length is 4 and max is 60")
    private String email;

    @Min(value = 1, message = "numOfBedrooms must be greater than zero")
    private long numOfBedrooms;

    @Min(value = 1, message = "areaOfEachBedroom must be greater than zero")
    private long areaOfEachBedroom;

    @Min(value = 1, message = "numOfBathrooms must be greater than zero")
    private long numOfBathrooms;

    @Min(value = 1, message = "areaOfEachBathroom must be greater than zero")
    private long areaOfEachBathroom;

    @Min(value = 1, message = "totalArea must be greater than zero")
    private long totalArea;

    @Min(value = 1, message = "price must be greater than zero")
    private float price;

}
