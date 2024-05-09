package ru.yandex.practicum.filmorate.controller;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.Level;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import static java.time.Month.DECEMBER;

@RestController
@RequestMapping("/films")
public class FilmController {
    private static Logger log = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger("FilmController");
    private Map<Integer, Film> films = new HashMap<>();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    @PostMapping
    public Film filmPost(@RequestBody Film film) {
        try {
            log.setLevel(Level.INFO);
            if (film.getName() == null || film.getName().isBlank()) {
                throw new ValidationException("Имя не может быть пустым");
            }

            if (film.getDescription().length() > 200) {
                throw new ValidationException("Описание не может быть больше 200 символов");
            }

            if (film.getDuration() < 0) {
                throw new ValidationException("Продолжительность не может быть отрицательным числом");
            }

            if (film.getReleaseDate().isBefore(LocalDate.of(1895, DECEMBER, 28))) {
                throw new ValidationException("Указана неверная дата");
            }

            film.setId(getNextId());
            films.put(film.getId(), film);
            return film;
        } catch (ValidationException e) {
            System.out.println(e.getMessage());
        }
        return film;
    }

    @PutMapping
    public Film filmPut(@RequestBody Film film) {
        try {
            if (films.containsKey(film.getId())) {
                if (film.getName() == null || film.getName().isBlank()) {
                    throw new ValidationException("Имя не может быть пустым");
                }

                if (film.getDescription().length() > 200) {
                    throw new ValidationException("Описание не может быть больше 200 символов");
                }

                if (film.getDuration() < 0) {
                    throw new ValidationException("Продолжительность не может быть отрицательным числом");
                }

                films.put(film.getId(), film);
                return film;
            } else {
                System.out.println("Указанного фильма не существует");
                return film;
            }
        } catch (ValidationException e) {
            System.out.println(e.getMessage());
        }
        return film;
    }

    @GetMapping
    public Collection<Film> filmsGet() {
        return films.values().stream().toList();
    }

    private int getNextId() {
        int num = films.keySet().stream()
                .mapToInt(id -> id)
                .max()
                .orElse(0);
        return ++num;
    }
}
