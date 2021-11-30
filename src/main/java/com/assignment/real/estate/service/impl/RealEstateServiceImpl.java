package com.assignment.real.estate.service.impl;

import com.assignment.real.estate.dto.AddPropertyRequestDTO;
import com.assignment.real.estate.dto.PropertyScore;
import com.assignment.real.estate.exceptions.BizException;
import com.assignment.real.estate.dto.FindPropertyRequestDTO;
import com.assignment.real.estate.dto.FindPropertyResponseDTO;
import com.assignment.real.estate.entities.Property;
import com.assignment.real.estate.repositories.PropertyRepository;
import com.assignment.real.estate.service.RealEstateMatching;
import com.assignment.real.estate.service.RealEstateProcessor;
import com.assignment.real.estate.service.RealEstateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class RealEstateServiceImpl implements RealEstateService {

    private final PropertyRepository propertyRepository;
    private final RealEstateProcessor realEstateProcessor;
    private final float BROKERAGE_CHARGES = 0.02f;

    @Autowired
    public RealEstateServiceImpl(final PropertyRepository propertyRepository, final RealEstateProcessor realEstateProcessor) {
        this.propertyRepository = propertyRepository;
        this.realEstateProcessor = realEstateProcessor;
    }

    @Override
    public long addProperty(final AddPropertyRequestDTO requestDTO) {
        try {
            final Property property = new Property();
            property.setName(requestDTO.getName());
            property.setEmail(requestDTO.getEmail());
            property.setNumOfBedrooms(requestDTO.getNumOfBedrooms());
            property.setAreaOfEachBedroom(requestDTO.getAreaOfEachBedroom());
            property.setNumOfBathrooms(requestDTO.getNumOfBathrooms());
            property.setAreaOfEachBathroom(requestDTO.getAreaOfEachBathroom());
            property.setTotalArea(requestDTO.getTotalArea());
            property.setPrice(requestDTO.getPrice());
            final Property savedProperty = propertyRepository.save(property);
            return savedProperty.getId();
        } catch (final Exception e) {
            log.error("Exception occurred while db operation", e);
            throw new BizException("Unable to add property.", e);
        }
    }

    @Override
    public List<FindPropertyResponseDTO> findProperties(FindPropertyRequestDTO requestDTO) {
        final Map<String, Object> map = realEstateProcessor.doBaseFiltering(requestDTO);
        final List<Property> propertyList = propertyRepository.findAll(propertySpecification(map));
        final RealEstateMatching realEstateMatching = new RealEstateMatching();
        final List<PropertyScore> propertyScores = realEstateMatching.match(requestDTO, propertyList, map);
        final List<Property> matchedList = new ArrayList<>();
        for(PropertyScore propertyScore: propertyScores) {
            if(propertyScore.getTotalScore() > 40) {
                matchedList.add(propertyRepository.getOne(propertyScore.getId()));
            }
        }
        matchedList.sort((o1, o2) -> Float.compare(o1.getPrice(), o2.getPrice()));
        return mapResponse(matchedList);
    }

    private List<FindPropertyResponseDTO> mapResponse(List<Property> matchedList) {
        final List<FindPropertyResponseDTO> responseDTOS = new ArrayList<>();
        for(final Property p: matchedList) {
            final FindPropertyResponseDTO dto = new FindPropertyResponseDTO();
            addBrokerageCharges(p);
            dto.setPrice(p.getPrice());
            dto.setNumOfBedrooms(p.getNumOfBedrooms());
            dto.setNumOfBathrooms(p.getNumOfBathrooms());
            dto.setAreaOfEachBedrooms(p.getAreaOfEachBedroom());
            dto.setAreaOfEachBathrooms(p.getAreaOfEachBathroom());
            dto.setTotalArea(p.getTotalArea());
            dto.setId(p.getId());
            responseDTOS.add(dto);
        }
        return responseDTOS;
    }

    private void addBrokerageCharges(Property p) {
        p.setPrice(Math.max(p.getPrice() + (BROKERAGE_CHARGES * p.getPrice()), 1.08f));
    }

    private Specification<Property> propertySpecification(final Map<String, Object> map) {
        Specification<Property> specification = (root, cq, cb) -> cb.between(root.get("price"), (float) map.get("minBudget"), (float )map.get("maxBudget"));
        specification = specification.and((root, cq, cb) -> cb.between(root.get("numOfBedrooms"), (long) map.get("minBeds"), (long) map.get("maxBeds")));
        specification = specification.and((root, cq, cb) -> cb.between(root.get("numOfBathrooms"), (long) map.get("minBaths"), (long) map.get("maxBaths")));
        return specification;
    }

}
