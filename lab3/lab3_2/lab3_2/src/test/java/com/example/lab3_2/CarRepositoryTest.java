package com.example.lab3_2;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.example.lab3_2.model.Car;
import com.example.lab3_2.repository.CarRepository;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class CarRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CarRepository carRepository;

    @Test
    void whenFindById_thenReturnCar() {
        Car car = new Car("Toyota", "Corolla");
        entityManager.persistAndFlush(car);

        Optional<Car> foundCar = carRepository.findById(car.getCarId());

        assertThat(foundCar).isPresent();
        assertThat(foundCar.get().getMaker()).isEqualTo(car.getMaker());
    }

    @Test
    void whenFindAll_thenReturnAllCars() {
        
        Car car1 = new Car("Honda", "Civic");
        Car car2 = new Car("Ford", "Focus");
        entityManager.persist(car1);
        entityManager.persist(car2);
        entityManager.flush();

        List<Car> cars = carRepository.findAll();

        assertThat(cars).hasSize(2).extracting(Car::getMaker).contains("Honda", "Ford");
    }

    @Test
    void whenSave_thenCarIsPersisted() {
        Car car = new Car("BMW", "X5");

        Car savedCar = carRepository.save(car);

        assertThat(savedCar).isNotNull();
        assertThat(savedCar.getMaker()).isEqualTo("BMW");
        assertThat(savedCar.getCarId()).isNotNull(); 
    }

    @Test
    void whenDelete_thenCarIsRemoved() {
        Car car = new Car("Tesla", "Model 3");
        entityManager.persistAndFlush(car);

        carRepository.delete(car);
        Optional<Car> deletedCar = carRepository.findById(car.getCarId());

        assertThat(deletedCar).isEmpty();
    }
}
