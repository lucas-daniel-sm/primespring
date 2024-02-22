package dev.lucasmendes.primespring.controller;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serial;
import java.io.Serializable;


@Component
@Named
@ViewScoped
@Data
public class HomeController implements Serializable {
    @Serial
    private static final long serialVersionUID = 1094801825228386363L;

    private String message = "";

    public void clear() {
        this.message = "";
    }

    public String getReversedMessage() {
        return new StringBuilder(this.message).reverse().toString();
    }
}
