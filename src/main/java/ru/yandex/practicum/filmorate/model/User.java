package ru.yandex.practicum.filmorate.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.time.LocalDate;

@Data
@Valid
public class User {
    private long id;
    @NotBlank
    private final String email;
    @NotBlank
    private final String login;
    private String name;
    private final LocalDate birthday;
}
