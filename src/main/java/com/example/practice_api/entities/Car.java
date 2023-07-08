package com.example.practice_api.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "make", length = 255, nullable = true)
    private String make;

    @Column(name = "model", length = 255, nullable = true)
    private String model;

    @Column(name = "year", length = 255, nullable = true)
    private Integer year;

    @Column(name = "price", length = 255, nullable = true)
    private Double price;

    @Column(name = "deleted", length = 255, nullable = true)
    private Boolean deleted;
}
