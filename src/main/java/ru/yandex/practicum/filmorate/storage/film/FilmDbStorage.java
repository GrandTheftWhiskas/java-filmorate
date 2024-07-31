package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MPA;
import java.sql.Date;
import java.util.*;

@Component
@Qualifier("filmDbStorage")
public class FilmDbStorage {
    private JdbcTemplate jdbcTemplate;

    public FilmDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Film postFilm(Film film) {
        int number = 0;
        String request = "INSERT INTO films(name, description, release_date, duration) "
                + "values(?, ?, ?, ?)";
        String request1 = "INSERT INTO genres(film_id, genre_id) " + "values(?, ?)";
        String request2 = "INSERT INTO film_mpa(film_id, mpa_id) " + "values(?, ?)";
        int result = jdbcTemplate.update(request, film.getName(), film.getDescription(),
                Date.valueOf(film.getReleaseDate()), film.getDuration());
        if (film.getGenres() != null) {
            for (Genre genre : film.getGenres()) {
                if (genre.getId() > 50) {
                    throw new ValidationException("Указан неверный идентификатор жанра");
                } else if (genre.getId() < number) {
                    break;
                }
                jdbcTemplate.update(request1, film.getId(), genre.getId());
                number++;
                System.out.println("Жанр добавлен");
            }
        }
        jdbcTemplate.update(request2, film.getId(), film.getMpa().getId());
        if (result != 0) {
            System.out.println("Фильм добавлен в базу данных");
        } else {
            System.out.println("Произошла ошибка при добавлении фильма в базу данных");
        }
        return film;
    }

    public Film putFilm(Film film) {
        String request = "UPDATE films SET " + "name = ?, description = ?, release_date = ?, duration = ? "
                + "WHERE id = ?";
        int result = jdbcTemplate.update(request, film.getName(), film.getDescription(), film.getReleaseDate(),
                film.getDuration(), film.getId());
        if (result == 0) {
            System.out.println("Указанного фильма нет в базе данных");
        } else {
            System.out.println("Запись обновлена в базе данных");
        }
        return film;
    }

    public void delFilm(Film film) {
        String request = "DELETE FROM films WHERE id = ?";
        String request1 = "DELETE FROM genres WHERE film_id = ?";
        String request2 = "DELETE FROM mpa WHERE film_id = ?";
        jdbcTemplate.update(request, film.getId());
        jdbcTemplate.update(request1, film.getId());
        jdbcTemplate.update(request2, film.getId());
        System.out.println("Запись удалена из базы данных");
    }

    public Film getFilm(long id) {
            String request = "SELECT * FROM films " +
                    "WHERE id = ?";
            String request1 = "SELECT gen.id, gen.name FROM genres AS g " +
                    "INNER JOIN genre AS gen ON g.genre_id = gen.id " +
                    "WHERE g.film_id = ?";
            String request2 = "SELECT m.id, m.name FROM film_mpa AS fm " +
                    "INNER JOIN mpa AS m ON fm.mpa_id = m.id " +
                    "WHERE fm.film_id = ?";
            Film film = jdbcTemplate.queryForObject(request, new FilmRowMapper(), id);
            film.setGenres(jdbcTemplate.query(request1, new GenreRowMapper(), id));
            film.setMpa(jdbcTemplate.queryForObject(request2, new MpaRowMapper(), id));
            return film;
    }

    public List<Film> getFilms() {
        String request = "SELECT f.*, g.genre_id FROM films AS f " +
                "INNER JOIN genres AS g ON f.id = g.film_id " +
                "INNER JOIN film_mpa AS m ON f.id = m.film_id";
        return jdbcTemplate.query(request, new FilmRowMapper());
    }

    public void addLike(long id, long user_id) {
        String request = "INSERT INTO likes(film_id, user_id) " +
                "values(?, ?)";
        jdbcTemplate.update(request, id, user_id);
        System.out.println("Лайк был добавлен в базу данных");
    }

    public void delLike(long id, long user_id) {
        String request = "DELETE FROM likes WHERE film_id = ? AND user_id = ?";
        jdbcTemplate.update(request, id, user_id);
        System.out.println("Лайк был удален из базы данных");
    }

    public Genre getGenre(long id) {
        String request = "SELECT * FROM genre WHERE id = ?";
        if (id > 50) {
            throw new NotFoundException("Жанр не найден");
        }
        return jdbcTemplate.queryForObject(request, new GenreRowMapper(), id);
    }

    public List<Genre> getGenres() {
        String request = "SELECT * FROM genre";
        List<Genre> genres = jdbcTemplate.query(request, new GenreRowMapper());
        if (genres.isEmpty()) {
            throw new NotFoundException("Жанры не найдены");
        }
        System.out.println("Получение жанров");
        return genres;
    }

    public MPA getMpa(long id) {
        String request = "SELECT * FROM mpa WHERE id = ?";
        if (id > 50) {
            throw new NotFoundException("Рейтинг не найден");
        }
        return jdbcTemplate.queryForObject(request, new MpaRowMapper(), id);
    }

    public List<MPA> getAllMpa() {
        String request = "SELECT * FROM mpa";
        return jdbcTemplate.query(request, new MpaRowMapper());
    }

    public List<Film> getMostPopularFilms(int count) {
        String request = "SELECT * FROM films " +
                "WHERE id IN (SELECT film_id " +
                "FROM likes " +
                "GROUP BY film_id " +
                "ORDER BY COUNT(user_id) DESC) " +
                "LIMIT ?";
        return jdbcTemplate.query(request, new FilmRowMapper(), count);
    }
}

