package com.assignment.real.estate.service;

import com.assignment.real.estate.dto.FindPropertyRequestDTO;
import com.assignment.real.estate.dto.PropertyScore;
import com.assignment.real.estate.entities.Property;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class RealEstateMatching {

    public List<PropertyScore> match(final FindPropertyRequestDTO requestDTO, final List<Property> propertyList, final Map<String, Object> reqMargins) {
        final List<PropertyScore> propertyScores = new ArrayList<>();
        for(final Property property: propertyList) {
            final PropertyScore propertyScore = new PropertyScore();
            priceMatching(requestDTO.getPrice(), property.getPrice(), propertyScore, (float) reqMargins.get("minBudget"), (float) reqMargins.get("maxBudget"));
            bedRoomMatching(requestDTO.getMinReqBedrooms(), property.getNumOfBedrooms(), propertyScore, (long) reqMargins.get("minBeds"), (long) reqMargins.get("maxBeds"));
            bathRoomMatching(requestDTO.getMinReqBathrooms(), property.getNumOfBathrooms(), propertyScore, (long) reqMargins.get("minBaths"), (long) reqMargins.get("maxBaths"));
            totalAreaMatching(requestDTO.getMinTotalArea(), property.getTotalArea(), propertyScore);
            propertyScore.setTotalScore(propertyScore.getPrice() + propertyScore.getBedrooms() + propertyScore.getBathrooms() + propertyScore.getTotalArea());
            propertyScore.setId(property.getId());
            propertyScores.add(propertyScore);
        }
        propertyScores.sort((o1, o2) -> Float.compare(o1.getTotalScore(), o2.getTotalScore()));
        return propertyScores;
    }

    private void totalAreaMatching(long minTotalArea, long totalArea, PropertyScore propertyScore) {
        final long minArea = (long) Math.max(minTotalArea - (0.10 * minTotalArea), 1);
        final long maxArea = (long) Math.max(minTotalArea + (0.10 * minTotalArea), 1);
        final float totalAreaScore = getTotalAreaScore(minTotalArea, minTotalArea, totalArea, minArea, maxArea);
        propertyScore.setTotalArea(totalAreaScore);
    }

    private float getTotalAreaScore(long minTotalArea, long maxTotalArea, long totalArea, long minArea, long maxArea) {
        float weightAge;

        if(totalArea >= minTotalArea && totalArea <= maxTotalArea) {
            weightAge = 1;
        } else if(totalArea < minTotalArea) {
            weightAge = (totalArea - minArea) / Math.max((minTotalArea - minArea), 1.0f);
        } else {
            weightAge = (maxArea - totalArea) / (maxArea - maxTotalArea * 1.0f);
        }

        return weightAge * 30;
    }

    private void bathRoomMatching(long minReqBathrooms, long numOfBathrooms, PropertyScore propertyScore, long minBaths, long maxBaths) {
        final float bathRoomScore = getBedRoomScore(minReqBathrooms, minReqBathrooms, numOfBathrooms, minBaths, maxBaths);
        propertyScore.setBedrooms(bathRoomScore);
    }

    private void bedRoomMatching(long minReqBedrooms, long numOfBedrooms, PropertyScore propertyScore, long minBeds, long maxBeds) {
        final float bedRoomScore = getBedRoomScore(minReqBedrooms, minReqBedrooms, numOfBedrooms, minBeds, maxBeds);
        propertyScore.setBedrooms(bedRoomScore);
    }

    private float getBedRoomScore(long minReqBedrooms, long maxReqBedrooms, long numOfBedrooms, long minBeds, long maxBeds) {
        float weightAge;

        if(numOfBedrooms >= minReqBedrooms && numOfBedrooms <= maxReqBedrooms) {
            weightAge = 1;
        } else if(numOfBedrooms < minReqBedrooms) {
            weightAge = (numOfBedrooms - minReqBedrooms) / Math.max((minReqBedrooms - minBeds), 1.0f);
        } else {
            weightAge = (maxBeds - numOfBedrooms) / (maxBeds - maxReqBedrooms * 1.0f);
        }

        return weightAge * 20;
    }

    private void priceMatching(final float askedPrice, final float propertyPrice, final PropertyScore propertyScore, final float minBudget, final float maxBudget) {
        final float min10Budget = (float) Math.max(askedPrice - (0.10 * askedPrice), 1.0);
        final float max10Budget = (float) Math.max(askedPrice + (0.10 * askedPrice), 1.1);
        final float budgetScore = getBudgetScore(min10Budget, max10Budget, propertyPrice, minBudget, maxBudget);
        propertyScore.setPrice(budgetScore);
    }

    private float getBudgetScore(float min10Budget, float max10Budget, float propertyPrice, float minPrice, float maxPrice) {
        float weightAge;

        if(propertyPrice >= min10Budget && propertyPrice <= max10Budget) {
            weightAge = 1.0f;
        } else if(propertyPrice < min10Budget) {
            weightAge = (propertyPrice - minPrice) / (min10Budget - minPrice);
        } else {
            weightAge = (maxPrice - propertyPrice) / (maxPrice - max10Budget);
        }
        return weightAge * 30;
    }

}
