package ru.yandex.practicum.filmorate.model;
import lombok.*;
import java.time.Duration;
import java.time.LocalDate;

@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
public class Film {
    @Setter
    private int id;
    private final String name;
    private final String description;
    private final LocalDate releaseDate;
    private final Duration duration;

    public int getDuration() {
        return (int) duration.toSeconds();
    }
}
