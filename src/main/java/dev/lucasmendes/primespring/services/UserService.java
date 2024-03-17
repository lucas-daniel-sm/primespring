package dev.lucasmendes.primespring.services;

import dev.lucasmendes.primespring.entities.PrimeSpringGroup;
import dev.lucasmendes.primespring.entities.PrimeSpringUser;
import dev.lucasmendes.primespring.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    @PostConstruct
    public void init() {
        if (this.hasUser()) {
            return;
        }
        final var defaultGroup = PrimeSpringGroup.builder().name("default").build();
        final var user = PrimeSpringUser.builder()
                .username("admin")
                .password("admin")
                .groups(List.of(defaultGroup))
                .build();
        this.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public boolean hasUser() {
        return userRepository.count() > 0;
    }

    public void save(PrimeSpringUser user) {
        userRepository.save(user);
    }
}
