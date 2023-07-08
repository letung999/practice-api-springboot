package com.example.practice_api.service.contract;

import com.example.practice_api.dto.CarDto;
import com.example.practice_api.entities.Car;
import com.example.practice_api.dto.request.car.SearchCarRequest;
import com.example.practice_api.dto.response.car.SearchCarResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface ICarService {

    /**
     * add car
     *
     * @param car
     * @return
     */
    ResponseEntity<String> addCard(CarDto car);

    /**
     * advance filter car
     *
     * @param request
     * @return
     */
    ResponseEntity<SearchCarResponse> search(SearchCarRequest request, Pageable pageable);

    /**
     * group and sum price for the car by manufacturer
     *
     * @return
     */
    ResponseEntity<Map<String, Double>> groupAndSumPriceForManufacturer();

    /**
     * delete car by ids
     * @param ids
     * @return
     */
    ResponseEntity<String> deleteCar(List<Integer> ids);
}
