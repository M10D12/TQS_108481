package com.example.lab3_2;

import com.example.lab3_2.model.Car;
import com.example.lab3_2.repository.CarRepository;
import com.example.lab3_2.service.CarManagerService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class CarManagerServiceTest {

    @Mock
    private CarRepository carRepository; 

    @InjectMocks
    private CarManagerService carService; 
    private Car car1, car2;

    @BeforeEach
    void setUp() {
        car1 = new Car("Toyota", "Corolla");
        car1.setCarId(1L);
        car2 = new Car("Honda", "Civic");
        car2.setCarId(2L);
    }

    @Test
    void whenSaveCar_thenReturnCar() {
        when(carRepository.save(any(Car.class))).thenReturn(car1);

        Car savedCar = carService.save(new Car("Toyota", "Corolla"));

        assertThat(savedCar).isNotNull();
        assertThat(savedCar.getMaker()).isEqualTo("Toyota");
        assertThat(savedCar.getModel()).isEqualTo("Corolla");

        verify(carRepository, times(1)).save(any(Car.class));
    }

    @Test
    void whenGetAllCars_thenReturnCarList() {
        List<Car> cars = Arrays.asList(car1, car2);
        when(carRepository.findAll()).thenReturn(cars);

        List<Car> result = carService.getAllCars();

        assertThat(result).hasSize(2);
        assertThat(result).extracting(Car::getMaker).contains("Toyota", "Honda");

        verify(carRepository, times(1)).findAll();
    }

    @Test
    void whenGetCarDetails_thenReturnCar() {
        when(carRepository.findById(1L)).thenReturn(Optional.of(car1));

        Optional<Car> foundCar = carService.getCarDetails(1L);

        assertThat(foundCar).isPresent();
        assertThat(foundCar.get().getMaker()).isEqualTo("Toyota");

        verify(carRepository, times(1)).findById(1L);
    }

    @Test
    void whenGetCarDetailsWithInvalidId_thenReturnEmpty() {
        when(carRepository.findById(999L)).thenReturn(Optional.empty());

        Optional<Car> foundCar = carService.getCarDetails(999L);

        assertThat(foundCar).isEmpty();

        verify(carRepository, times(1)).findById(999L);
    }
}
