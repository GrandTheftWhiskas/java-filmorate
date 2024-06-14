package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Valid
public class User {
    @JsonIgnore
    private Set<Long> friends = new HashSet<>();
    private long id;
    @NotBlank
    private final String email;
    @NotBlank
    private final String login;
    private String name;
    private final LocalDate birthday;

    public void addFriend(long id) {
        friends.add(id);
    }

    public void delFriend(long id) {
        friends.remove(id);
    }
}
