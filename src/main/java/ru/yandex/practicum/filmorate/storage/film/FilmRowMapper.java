package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MPA;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.util.List;
public class FilmRowMapper implements RowMapper<Film> {
    @Override
    public Film mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Film film = new Film(resultSet.getString("name"),
                resultSet.getString("description"),
                resultSet.getDate("release_date").toLocalDate(),
                        Duration.ofSeconds(resultSet.getInt("duration")));
        film.setId(resultSet.getLong("id"));
        film.setGenres(List.of(new Genre(resultSet.getInt("id"), resultSet.getString("name"))));
        film.setMpa(new MPA(resultSet.getInt("id"), resultSet.getString("name")));
        return film;
    }
}
