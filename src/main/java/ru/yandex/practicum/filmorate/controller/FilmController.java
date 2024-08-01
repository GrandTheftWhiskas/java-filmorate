package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.Collection;
import java.util.List;


@RestController
@Slf4j
public class FilmController {
   private final FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @PostMapping("/films")
    public Film postFilm(@RequestBody Film film) {
        log.info("Добавление фильма...");
        return filmService.postFilm(film);
    }

    @PutMapping("/films")
    public Film putFilm(@RequestBody Film film) {
        log.info("Обновление фильма...");
        return filmService.putFilm(film);
    }

    @PutMapping("/films/{id}/like/{userId}")
    public Film addLike(@PathVariable long id, @PathVariable long userId) {
        return filmService.addLike(id, userId);
    }


    @DeleteMapping("/films/{id}/like/{userId}")
    public Film delLike(@PathVariable long id, @PathVariable long userId) {
        return filmService.delLike(id, userId);
    }

    @GetMapping("/films/{id}")
    public Film getFilm(@PathVariable long id) {
        return filmService.getFilm(id);
    }

    @GetMapping("/films")
    public Collection<Film> getFilms() {
        return filmService.getFilms();
    }


    @GetMapping("/genres/{id}")
    public Genre getGenre(@PathVariable long id) {
        return filmService.getGenre(id);
    }

    @GetMapping("/genres")
    public List<Genre> getGenres() {
        return filmService.getGenres();
    }


    @GetMapping("/mpa/{id}")
    public MPA getMpa(@PathVariable long id) {
        return filmService.getMpa(id);
    }

    @GetMapping("/mpa")
    public List<MPA> getAllMpa() {
        return filmService.getAllMpa();
    }

    @GetMapping("/films/popular")
    @ResponseStatus(HttpStatus.OK)
    public Collection<Film> getMostPopularFilms(@RequestParam(defaultValue = "10") int count) {
        log.info("Поиск популярных фильмов...");
        return filmService.getMostPopularFilms(count);
    }
}
