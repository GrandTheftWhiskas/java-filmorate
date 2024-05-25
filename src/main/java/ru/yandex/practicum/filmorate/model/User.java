package ru.yandex.practicum.filmorate.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Set;

@Data
@Valid
public class User {
    private Set<Long> friends;
    private long id;
    @NotBlank
    private String email;
    @NotBlank
    private String login;
    private String name;
    private LocalDate birthday;

    public void addFriend(long id) {
        friends.add(id);
    }

    public void delFriend(long id) {
        friends.remove(id);
    }
}
