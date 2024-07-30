package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MPA;

import java.util.*;

@Component
public class InMemoryFilmStorage implements FilmStorage {
    private Map<Long, Film> films = new HashMap<>();
    private final FilmDbStorage filmDbStorage;

    @Autowired
    public InMemoryFilmStorage(FilmDbStorage filmDbStorage) {
        this.filmDbStorage = filmDbStorage;
    }

    public Film postFilm(Film film) {
        film.setId(getNextId());
        films.put(film.getId(), film);
        return filmDbStorage.postFilm(film);
    }

    public Film putFilm(Film film) {
        if (films.containsKey(film.getId())) {
            films.put(film.getId(), film);
            return filmDbStorage.putFilm(film);
        } else {
            throw new NotFoundException("Указанного фильма не существует");
        }
    }

    public void delFilm(Film film) {
        films.remove(film.getId());
    }

    public Film getFilm(long id) {
        return filmDbStorage.getFilm(id);
    }

    public List<Film> getFilms() {
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
        return null;
    }

    public List<Film> getMostPopularFilms(int count) {
        return filmDbStorage.getMostPopularFilms(count);

    }

    private long getNextId() {
        long num = films.keySet().stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++num;
    }
}
