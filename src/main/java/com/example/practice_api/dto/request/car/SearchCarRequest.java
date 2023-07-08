package com.example.practice_api.dto.request.car;

import com.example.practice_api.dto.request.BaseSearchRequest;
import lombok.Data;

@Data
public class SearchCarRequest {
    private String make;
    private String model;
    private Double price;
    private Integer year;
    private String color;

    private BaseSearchRequest searchRequest;
}
