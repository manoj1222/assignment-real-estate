package com.assignment.real.estate.dto;

import lombok.Data;

@Data
public class PropertyScore {

    private long id;
    private float price;
    private float bedrooms;
    private float bathrooms;
    private float totalArea;
    private float totalScore;

}
