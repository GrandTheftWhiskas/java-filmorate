package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.*;
import ru.yandex.practicum.filmorate.storage.film.FilmDbStorage;
import ru.yandex.practicum.filmorate.storage.user.UserDbStorage;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import static java.time.Month.DECEMBER;

@Service
public class FilmService {
    private final FilmDbStorage filmDbStorage;
    private final UserDbStorage userDbStorage;
    private static final int MAX_SYMBOLS = 200;
    private static final LocalDate MOVIE_BIRTHDAY = LocalDate.of(1895, DECEMBER, 28);

    @Autowired
    public FilmService(FilmDbStorage filmDbStorage, UserDbStorage userDbStorage) {
        this.filmDbStorage = filmDbStorage;
        this.userDbStorage = userDbStorage;
    }

    public Film addLike(long id, long userId) {
        User user = userDbStorage.getUser(id);
        User friend = userDbStorage.getUser(userId);
        if (user == null) {
            throw new NotFoundException("Пользователя не существует");
        }

        if (friend == null) {
            throw new NotFoundException("Друга не существует");
        }

        return filmDbStorage.addLike(id, userId);
    }

    public Film delLike(long id, long userId) {
        User user = userDbStorage.getUser(id);
        User friend = userDbStorage.getUser(userId);
        if (user == null) {
            throw new NotFoundException("Пользователя не существует");
        }

        if (friend == null) {
            throw new NotFoundException("Друга не существует");
        }

        if (!userDbStorage.getFriends(userId).contains(userId)) {
            System.out.println("Пользователь не был добавлен в друзья");
        }
        return filmDbStorage.delLike(id, userId);
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
        return filmDbStorage.postFilm(film);
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
        return filmDbStorage.putFilm(film);
    }

    public Film getFilm(long id) {
        return filmDbStorage.getFilm(id);
    }

    public Collection<Film> getFilms() {
        return filmDbStorage.getFilms();
    }

    public Genre getGenre(long id) {
        return filmDbStorage.getGenre(id);
    }

    public List<Genre> getGenres() {
        return filmDbStorage.getGenres();
    }

    public MPA getMpa(long id) {
       return filmDbStorage.getMpa(id);
    }

    public List<MPA> getAllMpa() {
        return filmDbStorage.getAllMpa();
    }

    public Collection<Film> getMostPopularFilms(int count) {
        return filmDbStorage.getMostPopularFilms(count);
    }
}