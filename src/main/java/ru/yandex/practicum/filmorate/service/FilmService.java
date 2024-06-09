package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.*;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

@Service
public class FilmService {
    private FilmStorage filmStorage;
    private UserStorage userStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage, UserStorage  userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }

    public Film addLike(long id, long userId) {
        Film film = filmStorage.getFilm(id);
        User user = userStorage.getUser(userId);
        if (film == null) {
            throw new NullPointerException("Такого фильма не существует");
        }

        if (user == null) {
            throw new NullPointerException("Такого пользователя не существует");
        }
        film.like(userId);
        return film;
    }

    public Film delLike(long id, long userId) {
        Film film = filmStorage.getFilm(id);
        User user = userStorage.getUser(userId);
        if (film == null) {
            throw new NullPointerException("Такого фильма не существует");
        }

        if (user == null) {
            throw new NullPointerException("Такого пользователя не существует");
        }
        film.delLike(userId);
        return film;
    }
}