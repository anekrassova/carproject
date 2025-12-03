package org.example.lab4.service;

import org.example.lab4.entity.Car;
import org.example.lab4.repository.CarRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {

    private final CarRepository repository;

    public CarService(CarRepository repository) {
        this.repository = repository;
    }

    public Car addCar(Car car) {
        return repository.save(car);
    }

    public List<Car> getAllCars() {
        return repository.findAll();
    }

    public Car getCar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Car not found"));
    }

    public Car updateCar(Long id, Car updated) {
        Car existing = getCar(id);

        existing.setBrand(updated.getBrand());
        existing.setModel(updated.getModel());
        existing.setYear(updated.getYear());

        return repository.save(existing);
    }

    public void deleteCar(Long id) {
        repository.deleteById(id);
    }
}
