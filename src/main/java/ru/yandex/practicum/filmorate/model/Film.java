package ru.yandex.practicum.filmorate.model;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.time.Duration;
import java.time.LocalDate;

@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
@Valid
public class Film {
    @Setter
    private int id;
    @NotBlank
    private final String name;
    @Max(200)
    private final String description;
    private final LocalDate releaseDate;
    private final Duration duration;

    public int getDuration() {
        return (int) duration.toSeconds();
    }
}
