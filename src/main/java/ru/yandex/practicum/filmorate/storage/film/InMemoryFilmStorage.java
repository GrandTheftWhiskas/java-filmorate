package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

@Component
public class InMemoryFilmStorage implements FilmStorage {
    private Map<Long, Film> films = new HashMap<>();

    public Film postFilm(Film film) {
        film.setId(getNextId());
        films.put(film.getId(), film);
        return film;
    }

    public Film putFilm(Film film) {
        if (films.containsKey(film.getId())) {
            films.put(film.getId(), film);
            return film;
        } else {
            throw new NotFoundException("Указанного фильма не существует");
        }
    }

    public void delFilm(Film film) {
        films.remove(film.getId());
    }

    public Film getFilm(long id) {
        return films.get(id);
    }

    public List<Film> getFilms() {
        return films.values().stream().toList();
    }

    public List<Film> getMostPopularFilms(int count) {
        return films.values().stream().sorted((Film film1, Film film2) -> {
            return film2.getLike() - film1.getLike();
        }).limit(count).toList();
    }

    private long getNextId() {
        long num = films.keySet().stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++num;
    }
}
