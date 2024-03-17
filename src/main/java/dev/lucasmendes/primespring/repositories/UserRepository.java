package dev.lucasmendes.primespring.repositories;

import dev.lucasmendes.primespring.entities.PrimeSpringUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<PrimeSpringUser, Long> {
    Optional<PrimeSpringUser> findByUsername(String username);

    long count();
}
