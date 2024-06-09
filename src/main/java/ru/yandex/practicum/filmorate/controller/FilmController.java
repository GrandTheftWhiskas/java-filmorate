package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import java.util.Collection;


@RestController
@RequestMapping("/films")
public class FilmController {
   private FilmService filmService;
   private FilmStorage filmStorage;

    @Autowired
    public FilmController(FilmService filmService, FilmStorage filmStorage) {
        this.filmService = filmService;
        this.filmStorage = filmStorage;
    }

    @PostMapping
    public Film postFilm(@RequestBody Film film) {
        return filmStorage.postFilm(film);
    }

    @PutMapping
    public Film putFilm(@RequestBody Film film) {
        return filmStorage.putFilm(film);
    }

    @PutMapping("/{id}/like/{userId}")
    public Film addLike(@RequestBody @PathVariable long id, @RequestBody @PathVariable long userId) {
        return filmService.addLike(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public Film delLike(@RequestBody @PathVariable long id, @RequestBody @PathVariable long userId) {
        return filmService.delLike(id, userId);
    }

    @GetMapping
    public Collection<Film> getFilms() {
        return filmStorage.getFilms();
    }

    @GetMapping("/popular")
    @ResponseStatus(HttpStatus.OK)
    public Collection<Film> getMostPopularFilms(@RequestParam(defaultValue = "10") int count) {
        return filmStorage.getMostPopularFilms(count);
    }
}
