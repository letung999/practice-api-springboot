package com.example.practice_api.dto.response.car;

import com.example.practice_api.entities.Car;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class SearchCarResponse {
    private List<Car> carList;

    private Long numOfItems;

    private Integer numberOfPages;

    private Integer pageIndex;

    private Integer pageSize;
}
