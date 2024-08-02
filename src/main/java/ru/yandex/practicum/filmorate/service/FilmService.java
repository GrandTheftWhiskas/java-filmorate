package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.*;
import ru.yandex.practicum.filmorate.storage.film.FilmDbStorage;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserDbStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import static java.time.Month.DECEMBER;

@Service
public class FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;
    private static final int MAX_SYMBOLS = 200;
    private static final LocalDate MOVIE_BIRTHDAY = LocalDate.of(1895, DECEMBER, 28);

    @Autowired
    public FilmService(FilmStorage filmStorage, UserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }

    public Film addLike(long id, long userId) {
        User user = userStorage.getUser(id);
        User friend = userStorage.getUser(userId);
        if (user == null) {
            throw new NotFoundException("Пользователя не существует");
        }

        if (friend == null) {
            throw new NotFoundException("Друга не существует");
        }

        return filmStorage.addLike(id, userId);
    }

    public Film delLike(long id, long userId) {
        User user = userStorage.getUser(id);
        User friend = userStorage.getUser(userId);
        if (user == null) {
            throw new NotFoundException("Пользователя не существует");
        }

        if (friend == null) {
            throw new NotFoundException("Друга не существует");
        }

        if (!userStorage.getFriends(userId).contains(userId)) {
            System.out.println("Пользователь не был добавлен в друзья");
        }
        return filmStorage.delLike(id, userId);
    }

    public Film postFilm(Film film) {
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

        if (film.getMpa().getId() >= 10) {
            throw new ValidationException("Указан неверный рейтинг");
        }
        return filmStorage.postFilm(film);
    }

    public Film putFilm(Film film) {
        if (film.getName() == null || film.getName().isBlank()) {
            throw new ValidationException("Имя не может быть пустым");
        }

        if (film.getDescription().length() > 200) {
            throw new ValidationException("Описание не может быть больше 200 символов");
        }

        if (film.getDuration() < 0) {
            throw new ValidationException("Продолжительность не может быть отрицательным числом");
        }
        return filmStorage.putFilm(film);
    }

    public Film getFilm(long id) {
        return filmStorage.getFilm(id);
    }

    public Collection<Film> getFilms() {
        return filmStorage.getFilms();
    }

    public Genre getGenre(long id) {
        return filmStorage.getGenre(id);
    }

    public List<Genre> getGenres() {
        return filmStorage.getGenres();
    }

    public MPA getMpa(long id) {
       return filmStorage.getMpa(id);
    }

    public List<MPA> getAllMpa() {
        return filmStorage.getAllMpa();
    }

    public Collection<Film> getMostPopularFilms(int count) {
        return filmStorage.getMostPopularFilms(count);
    }
}