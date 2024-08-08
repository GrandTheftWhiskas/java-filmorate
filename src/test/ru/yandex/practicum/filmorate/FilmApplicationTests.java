package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.ComponentScan;
import ru.yandex.practicum.filmorate.model.*;
import ru.yandex.practicum.filmorate.storage.film.FilmDbStorage;

import java.time.Duration;
import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@JdbcTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@ComponentScan(basePackages = "ru.yandex.practicum.filmorate")
public class FilmApplicationTests {
    private final FilmDbStorage filmDbStorage;
    Film film;

    @BeforeEach
    public void setUp() {
        film = new Film("Титаник", "Фильм", LocalDate.of(2015, 11, 1),
                Duration.ofMinutes(180));
        film.setMpa(new MPA(1, null));
        List<Genre> genres = new ArrayList<>();
        genres.add(new Genre(1, null));
        film.setGenres(genres);
    }

    @Test
    public void couldAddFilmTest() {
        assertDoesNotThrow(() -> filmDbStorage.postFilm(film));
    }

    @Test
    public void couldGetFilmTest() {
        film = filmDbStorage.postFilm(film);
        Optional<Film> film1 = Optional.ofNullable(filmDbStorage.getFilm(film.getId()));
        assertThat(film1.isPresent());
    }

    @Test
    public void couldUpdateFilmTest() {
        film = filmDbStorage.postFilm(film);
        film.setMpa(new MPA(1, "название"));
        filmDbStorage.putFilm(film);

        assertThat(filmDbStorage.getFilm(film.getId()).getMpa().getName()).isEqualTo("название");
    }

    @Test
    public void couldGetListOfFilmsTest() {
        Film film1 = new Film("фильм", "описание", LocalDate.of(2015, 11, 1),
                Duration.ofMinutes(180));
        film1.setMpa(new MPA(1, "название1"));
        Film film2 = new Film("фильм1", "описание1", LocalDate.of(2016, 12, 2),
                Duration.ofMinutes(181));
        film2.setMpa(new MPA(2, "название2"));

        film1 = filmDbStorage.postFilm(film1);
        film2 = filmDbStorage.postFilm(film2);

        List<Film> films = (List<Film>) filmDbStorage.getFilms();
        assertThat(films.size()).isEqualTo(2);
        assertThat(films.contains(film1)).isTrue();
        assertThat(films.contains(film2)).isTrue();
    }
}

