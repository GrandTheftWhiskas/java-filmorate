package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MPA;
import java.util.List;

public interface FilmStorage {
    Film postFilm(Film film);

    Film putFilm(Film film);

    void delFilm(Film film);

    Film getFilm(long id);

    List<Film> getFilms();

    Film addLike(long id, long userId);

    Film delLike(long id, long userId);

    List<Film> getMostPopularFilms(int count);

    Genre getGenre(long id);

    List<Genre> getGenres();

    MPA getMpa(long id);

    List<MPA> getAllMpa();
}
