package com.example.practice_api.controller;

import com.example.practice_api.dto.CarDto;
import com.example.practice_api.dto.request.car.SearchCarRequest;
import com.example.practice_api.dto.response.car.SearchCarResponse;
import com.example.practice_api.entities.Car;
import com.example.practice_api.service.impl.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("car")
public class CarController {
    @Autowired
    private CarService carService;

    @PostMapping(value = "/add-car", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> addCar(@Valid @RequestBody CarDto car) {
        return carService.addCard(car);
    }

    @PostMapping(value = "/search")
    ResponseEntity<SearchCarResponse> search(@Valid @RequestBody SearchCarRequest request) {
        PageRequest pageRequest = PageRequest.of(request.getSearchRequest().getIndex() - 1, request.getSearchRequest().getSize());
        return carService.search(request, pageRequest);
    }

    @GetMapping(value = "sum-by-manufacturer")
    ResponseEntity<Map<String, Double>> sumByManufacturer() {
        return carService.groupAndSumPriceForManufacturer();
    }

    @DeleteMapping(value = "/delete")
    ResponseEntity<String> deleteByIds(@RequestBody @Valid List<Integer> ids) {
        return carService.deleteCar(ids);
    }
}
