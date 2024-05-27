package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.*;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;
import java.util.ArrayList;
import java.util.List;

@Service
public class FilmService {
    private FilmStorage filmStorage;
    private UserStorage userStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage, UserStorage  userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }

    public String addLike(long id, long userId) {
        Film film = filmStorage.getFilm(id);
        User user = userStorage.getUser(userId);
        if (film == null) {
            throw new NullPointerException("Такого фильма не существует");
        }

        if (user == null) {
            throw new NullPointerException("Такого пользователя не существует");
        }
        film.like(userId);
        return "Лайк успешно добавлен";
    }

    public String deleteLike(long id, long userId) {
        Film film = filmStorage.getFilm(id);
        User user = userStorage.getUser(userId);
        if (film == null) {
            throw new NullPointerException("Такого фильма не существует");
        }

        if (user == null) {
            throw new NullPointerException("Такого пользователя не существует");
        }
        film.delLike(userId);
        return "Лайк успешно удален";
    }

    public List<Film> getPopularFilms(int count) {
       List<Film> allFilms = filmStorage.getFilms();
       allFilms.sort((Film film1, Film film2) -> {
           return film1.getLike() - film2.getLike();
       });
       List<Film> popularFilms = new ArrayList<>();
       for (int i = 0; i < count; i++) {
           popularFilms.add(allFilms.get(i));
       }
       return popularFilms;
    }
}