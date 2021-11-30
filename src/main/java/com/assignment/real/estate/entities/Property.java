package com.assignment.real.estate.entities;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "property")
public class Property implements Serializable {

    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "num_of_bedrooms", nullable = false)
    private long numOfBedrooms;

    @Column(name = "area_of_each_bedroom", nullable = false)
    private long areaOfEachBedroom;

    @Column(name = "num_of_bathrooms", nullable = false)
    private long numOfBathrooms;

    @Column(name = "area_of_each_bathroom", nullable = false)
    private long areaOfEachBathroom;

    @Column(name = "total_area", nullable = false)
    private long totalArea;

    @Column(name = "price", nullable = false)
    private float price;
}
