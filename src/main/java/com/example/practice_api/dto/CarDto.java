package com.example.practice_api.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class CarDto {
    private final Integer id;

    @NotEmpty
    private final String make;

    @NotEmpty
    private final String model;

    @NotEmpty
    private final Integer year;

    @NotEmpty
    private final Double price;

    @NotEmpty
    private Boolean deleted;
}
