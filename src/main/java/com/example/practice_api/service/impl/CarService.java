package com.example.practice_api.service.impl;

import com.example.practice_api.constants.StatusConstant;
import com.example.practice_api.dto.CarDto;
import com.example.practice_api.dto.request.car.SearchCarRequest;
import com.example.practice_api.dto.response.car.SearchCarResponse;
import com.example.practice_api.entities.Car;
import com.example.practice_api.mapper.CarMapper;
import com.example.practice_api.repository.CarRepository;
import com.example.practice_api.service.contract.ICarService;
import com.example.practice_api.utils.MessageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CarService implements ICarService {

    @Autowired
    private CarRepository carRepository;

    CarMapper carMapper = CarMapper.INSTANCE;

    @Override
    public ResponseEntity<String> addCard(CarDto carDto) {
        try {
            var carInDB = carRepository.findByModelAndMake(carDto.getModel(), carDto.getMake());
            if (!Objects.isNull(carInDB)) {
                return MessageUtil.getResponseStatus(StatusConstant.DATA_IS_EXIST, HttpStatus.BAD_REQUEST);
            }
            carRepository.save(carMapper.carDtoToCar(carDto));
            return MessageUtil.getResponseStatus(StatusConstant.INF_MSG_SUCCESSFULLY, HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return MessageUtil.getResponseStatus(StatusConstant.SOME_THING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<SearchCarResponse> search(SearchCarRequest request, Pageable pageable) {
        try {
            var specification = buildSearchFilter(request);
            var carPages = carRepository.findAll(specification, pageable);
            var resultData = carPages.getContent();
            if (resultData.isEmpty()) {
                return new ResponseEntity<>(new SearchCarResponse(), HttpStatus.OK);
            }

            var response = new SearchCarResponse();
            response.setCarList(resultData);
            response.setPageIndex(request.getSearchRequest().getIndex());
            response.setPageSize(request.getSearchRequest().getSize());
            response.setNumberOfPages(carPages.getTotalPages());
            response.setNumOfItems(carPages.getTotalElements());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<Map<String, Double>> groupAndSumPriceForManufacturer() {
        try {
            var cars = carRepository.findAll();
            var resultData = new HashMap<String, Double>();
            if (cars.isEmpty()) {
                return new ResponseEntity<>(resultData, HttpStatus.OK);
            }
            var carListByMake = cars.stream().collect(Collectors.groupingBy(c -> c.getMake()));
            for (var key : carListByMake.keySet()) {
                var sumOfManufacturer = carListByMake.get(key).stream().mapToDouble(c -> c.getPrice()).sum();
                resultData.put(key, sumOfManufacturer);
            }

            return new ResponseEntity<>(resultData, HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> deleteCar(List<Integer> ids) {
        try {
            var carInDb = carRepository.findAllById(ids);
            if (carInDb.size() == 0) {
                return MessageUtil.getResponseStatus(StatusConstant.DATA_IS_NOT_FOUND, HttpStatus.NOT_FOUND);
            }
            var resultData = carInDb.stream().filter(x -> !x.getDeleted()).collect(Collectors.toList());
            if (resultData.size() != 0) {
                resultData.forEach(x -> x.setDeleted(true));
                carRepository.saveAll(resultData);
            }
            return MessageUtil.getResponseStatus(StatusConstant.INF_MSG_SUCCESSFULLY, HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return MessageUtil.getResponseStatus(StatusConstant.SOME_THING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //private methods
    private Specification<Car> buildSearchFilter(SearchCarRequest request) {
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> finalFilter = new ArrayList<>();

            if (request.getMake() != null && !request.getMake().isEmpty()) {
                finalFilter.add(criteriaBuilder.equal(root.get("make"), request.getMake()));
            }

            if (request.getModel() != null && !request.getModel().isEmpty()) {
                finalFilter.add(criteriaBuilder.equal(root.get("model"), request.getModel()));
            }

            if (request.getYear() != null) {
                finalFilter.add(criteriaBuilder.equal(root.get("year"), request.getYear()));
            }

            if (request.getPrice() != null) {
                finalFilter.add(criteriaBuilder.equal(root.get("price"), request.getPrice()));
            }

            if (request.getSearchRequest().getSortBy() != null && !request.getSearchRequest().getSortBy().isEmpty() && request.getSearchRequest().isAscending()) {
                query.orderBy(criteriaBuilder.asc(root.get(request.getSearchRequest().getSortBy())));
            }

            if (request.getSearchRequest().getSortBy() != null && !request.getSearchRequest().getSortBy().isEmpty()) {
                query.orderBy(criteriaBuilder.desc(root.get(request.getSearchRequest().getSortBy())));
            }

            return criteriaBuilder.and(finalFilter.toArray(new Predicate[0]));
        });
    }
}
