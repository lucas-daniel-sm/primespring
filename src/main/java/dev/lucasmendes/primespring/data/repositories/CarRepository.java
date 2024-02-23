package dev.lucasmendes.primespring.data.repositories;

import dev.lucasmendes.primespring.data.entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Integer> {
}
