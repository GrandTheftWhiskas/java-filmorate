package ru.yandex.practicum.filmorate.model;

import jakarta.validation.Valid;
import lombok.Data;
import java.time.LocalDate;

@Data
@Valid
public class User {
    private int id;
    private String email;
    private String login;
    private String name;
    private LocalDate birthday;
}
