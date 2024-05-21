package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import static java.time.Month.DECEMBER;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {
    private static final int MAX_SYMBOLS = 200;
    private static final LocalDate MOVIE_BIRTHDAY = LocalDate.of(1895, DECEMBER, 28);
    private Map<Integer, Film> films = new HashMap<>();

    @PostMapping
    public Film filmPost(@RequestBody Film film) {
            if (film.getName() == null || film.getName().isBlank()) {
                throw new ValidationException("Имя не может быть пустым");
            }

            if (film.getDescription().length() > MAX_SYMBOLS) {
                throw new ValidationException("Описание не может быть больше 200 символов");
            }

            if (film.getDuration() < 0) {
                throw new ValidationException("Продолжительность не может быть отрицательным числом");
            }

            if (film.getReleaseDate().isBefore(MOVIE_BIRTHDAY)) {
                throw new ValidationException("Указана неверная дата");
            }

            film.setId(getNextId());
            films.put(film.getId(), film);
            return film;
    }

    @PutMapping
    public Film filmPut(@RequestBody Film film) {
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
                throw new ValidationException("Указанного фильма не существует");
            }

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
