package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;
import java.util.List;

public interface FilmStorage {
    Film postFilm(Film film);

    Film putFilm(Film film);

    void delFilm(Film film);

    Film getFilm(long id);

    List<Film> getFilms();

    List<Film> getMostPopularFilms(int count);
}
