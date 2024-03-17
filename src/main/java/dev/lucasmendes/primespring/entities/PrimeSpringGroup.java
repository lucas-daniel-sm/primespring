package dev.lucasmendes.primespring.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrimeSpringGroup implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String name;

    @Override
    public String getAuthority() {
        return "ROLE_" + this.name.toUpperCase();
    }
}
