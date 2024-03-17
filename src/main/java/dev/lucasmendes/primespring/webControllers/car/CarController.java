package dev.lucasmendes.primespring.webControllers.car;


import dev.lucasmendes.primespring.entities.Car;
import dev.lucasmendes.primespring.entities.CarMake;
import dev.lucasmendes.primespring.repositories.CarRepository;
import jakarta.el.MethodExpression;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
@RequiredArgsConstructor(onConstructor_ = @Inject)
@Getter
@Setter
@Slf4j
public class CarController implements Serializable {
    @Serial
    private static final long serialVersionUID = 1094801825728386363L;

    private final SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
    private final CarRepository carRepository;
    private final CarLazyDataModel carLazyModel;

    public void populate() {
        // List generated with ms copilot, I not check the information, it can be wrong.
        var cars = List.of(
                new Car(new CarMake("Lamborghini"), "Aventador", 2022),
                new Car(new CarMake("Lamborghini"), "Huracan", 2023),
                new Car(new CarMake("Ferrari"), "488 GTB", 2022),
                new Car(new CarMake("Ferrari"), "SF90 Stradale", 2023),
                new Car(new CarMake("Koenigsegg"), "Jesko", 2022),
                new Car(new CarMake("Koenigsegg"), "Regera", 2023),
                new Car(new CarMake("Koenigsegg"), "Gemera", 2024),
                new Car(new CarMake("Dodge"), "Challenger", 2022),
                new Car(new CarMake("Nissan"), "Skyline GT-R R34", 2002),
                new Car(new CarMake("Nissan"), "370Z", 2020),
                new Car(new CarMake("Nissan"), "Fairlady Z", 1970),
                new Car(new CarMake("Nissan"), "Datsun 240Z", 1971),
                new Car(new CarMake("Volkswagen"), "Beetle", 1970),
                new Car(new CarMake("Volkswagen"), "Kombi", 1975),
                new Car(new CarMake("Volkswagen"), "Brasilia", 1973),
                new Car(new CarMake("Chevrolet"), "Monza", 1980),
                new Car(new CarMake("Chevrolet"), "Opala", 1988),
                new Car(new CarMake("Chevrolet"), "Impala", 1967)
        );
        carRepository.saveAll(cars);
    }

    public void clear() {
        carRepository.deleteAll();
    }

    public void logout() {
        // post request to logout
        var request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        var response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        var auth = (Authentication) FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
        logoutHandler.logout(request, response, auth);
        // refresh page
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/login");
        } catch (IOException e) {
            log.error("Error on logout", e);
        }
    }
}
