package ru.yandex.practicum.filmorate.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@EqualsAndHashCode
@ToString
@RequiredArgsConstructor
@Valid
public class User {
    private Set<Long> friends = new HashSet<>();
    @Setter
    private long id;
    @NotBlank
    private final String email;
    @NotBlank
    private final String login;
    @Setter
    private String name;
    private final LocalDate birthday;

    public void addFriend(long id) {
        friends.add(id);
    }

    public void delFriend(long id) {
        friends.remove(id);
    }
}
