package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.ArrayList;
import java.util.List;

@Service
public class FilmService {
    private FilmStorage filmStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    @PutMapping("/films/{id}/like/{userId}")
    public void addLike(@PathVariable long id, @PathVariable long userId) {
        Film film = filmStorage.getFilm(id);
        film.like(userId);
    }

    @DeleteMapping("/films/{id}/like/{userId}")
    public void deleteLike(@PathVariable long id, @PathVariable long userId) {
        Film film = filmStorage.getFilm(id);
        film.delLike(userId);
    }

    @GetMapping("/films/popular")
    public List<Film> getPopularFilms(@RequestParam(defaultValue = "10") int count) {
       List<Film> allFilms = filmStorage.getFilms();
       allFilms.sort((Film film1, Film film2) -> {
           return film1.getLikes() - film2.getLikes();
       });
       List<Film> popularFilms = new ArrayList<>();
       for (int i = 0; i < count; i++) {
           popularFilms.add(allFilms.get(i));
       }
       return popularFilms;
    }
}