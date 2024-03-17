package dev.lucasmendes.primespring.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @JoinColumn
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private CarMake make;
    private String model;
    private Integer year;

    public Car(CarMake make, String model, Integer year) {
        this(null, make, model, year);
    }
}
