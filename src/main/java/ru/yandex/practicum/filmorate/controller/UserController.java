package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    private Map<Integer, User> users = new HashMap<>();

    @PostMapping
    public User userPost(@RequestBody User user) {
            if (user.getEmail().isBlank() || !user.getEmail().contains("@")) {
                throw new ValidationException("Электронная почта не может быть пустой и должна содержать символ @");
            }

            if (user.getLogin().isBlank() || user.getLogin().contains(" ")) {
                throw new ValidationException("Логин не может быть пустым и содержать пробелы");
            }

            if (user.getName() == null || user.getName().isBlank()) {
                user.setName(user.getLogin());
            }

            if (user.getBirthday().isAfter(LocalDate.now())) {
                throw new ValidationException("Дата рождения не может быть в будущем");
            }

            user.setId(getNextId());
            users.put(user.getId(), user);
            return user;
    }

    @PutMapping
    public User userPut(@RequestBody User user) {
                if (users.containsKey(user.getId())) {
                    if (user.getEmail().isBlank() || !user.getEmail().contains("@")) {
                        throw new ValidationException("Электронная почта не может быть пустой и должна содержать символ @");
                    }

                    if (user.getLogin().isBlank() || user.getLogin().contains(" ")) {
                        throw new ValidationException("Логин не может быть пустым и содержать пробелы");
                    }

                    if (user.getName() == null || user.getName().isBlank()) {
                        user.setName(user.getLogin());
                    }

                    if (user.getBirthday().isAfter(LocalDate.now())) {
                        throw new ValidationException("Дата рождения не может быть в будущем");
                    }

                    users.put(user.getId(), user);
                    return user;
                } else {
                    throw new ValidationException("Указанного пользователя не существует");
                }

    }

    @GetMapping
    public Collection<User> usersGet() {
        return users.values().stream().toList();
    }


    private int getNextId() {
        int num = users.keySet().stream()
                .mapToInt(id -> id)
                .max()
                .orElse(0);
        return ++num;
    }
}
