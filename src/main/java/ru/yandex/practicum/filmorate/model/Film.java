package ru.yandex.practicum.filmorate.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.time.Duration;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@EqualsAndHashCode
@ToString
@RequiredArgsConstructor
@Valid
public class Film {
    @JsonIgnore
    private Set<Long> likes = new HashSet<>();
    @Setter
    private long id;
    @NotBlank
    private final String name;
    @Max(200)
    private final String description;
    private final String genre;
    private final Rating rating;
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

    public int getLike() {
        return likes.size();
    }
}
