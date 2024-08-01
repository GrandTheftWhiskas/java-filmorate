package ru.yandex.practicum.filmorate.model;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

@Getter
@EqualsAndHashCode
@ToString
@RequiredArgsConstructor
@Valid
public class Film {
    @Setter
    private long id;
    @NotBlank
    private final String name;
    @Max(200)
    private final String description;
    @Setter
    private List<Genre> genres;
    @Setter
    private MPA mpa;
    private final LocalDate releaseDate;
    private final Duration duration;

    public int getDuration() {
        return (int) duration.toSeconds();
    }

}
