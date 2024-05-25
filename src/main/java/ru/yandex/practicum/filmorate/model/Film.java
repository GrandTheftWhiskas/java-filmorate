package ru.yandex.practicum.filmorate.model;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Set;

@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
@Valid
public class Film {
    private Set<Long> likes;
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

    public void like(long id) {
        likes.add(id);
    }

    public void delLike(long id) {
        likes.remove(id);
    }

    public int getLikes() {
        return likes.size();
    }
}
