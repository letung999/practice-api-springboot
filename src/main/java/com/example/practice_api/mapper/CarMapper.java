package com.example.practice_api.mapper;

import com.example.practice_api.dto.CarDto;
import com.example.practice_api.entities.Car;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CarMapper {
    CarMapper INSTANCE = Mappers.getMapper(CarMapper.class);

    CarDto carToCarDto(Car car);

    Car carDtoToCar(CarDto carDto);

    List<CarDto> carListToCarDtoList(List<Car> cars);
}
