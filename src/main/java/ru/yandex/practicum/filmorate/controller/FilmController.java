package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import java.util.List;

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
    public String addLike(@PathVariable long id, @PathVariable long userId) {
        return filmService.addLike(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public String deleteLike(@PathVariable long id,@PathVariable long userId) {
        return filmService.deleteLike(id, userId);
    }

    @GetMapping
    public List<Film> getFilms() {
        return filmStorage.getFilms();
    }

    @GetMapping("/popular")
    public List<Film> getPopularFilms(@RequestParam(defaultValue = "10") int count) {
        return filmService.getPopularFilms(count);
    }
}
