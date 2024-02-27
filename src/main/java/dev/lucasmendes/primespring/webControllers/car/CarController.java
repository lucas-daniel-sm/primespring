package dev.lucasmendes.primespring.webControllers.car;


import dev.lucasmendes.primespring.entities.Car;
import dev.lucasmendes.primespring.repositories.CarRepository;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
@RequiredArgsConstructor
@Getter
@Setter
public class CarController implements Serializable {
    @Serial
    private static final long serialVersionUID = 1094801825728386363L;

    private final CarRepository carRepository;
    private final CarLazyDataModel carLazyModel;
    
    public void populate() {
        // List generated with ms copilot, I not check the information, it can be wrong.
        var cars = List.of(
                new Car("Lamborghini", "Aventador", 2022),
                new Car("Lamborghini", "Huracan", 2023),
                new Car("Ferrari", "488 GTB", 2022),
                new Car("Ferrari", "SF90 Stradale", 2023),
                new Car("Koenigsegg", "Jesko", 2022),
                new Car("Koenigsegg", "Regera", 2023),
                new Car("Koenigsegg", "Gemera", 2024),
                new Car("Dodge", "Challenger", 2022),
                new Car("Nissan", "Skyline GT-R R34", 2002),
                new Car("Nissan", "370Z", 2020),
                new Car("Nissan", "Fairlady Z", 1970),
                new Car("Nissan", "Datsun 240Z", 1971),
                new Car("Volkswagen", "Beetle", 1970),
                new Car("Volkswagen", "Kombi", 1975),
                new Car("Volkswagen", "Brasilia", 1973),
                new Car("Chevrolet", "Monza", 1980),
                new Car("Chevrolet", "Opala", 1988),
                new Car("Chevrolet", "Impala", 1967)
        );
        carRepository.saveAll(cars);
    }

    public void clear() {
        carRepository.deleteAll();
    }
}
