package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import java.time.LocalDate;
import java.util.*;

@Component
@RequestMapping("/users")
public class InMemoryUserStorage implements UserStorage {
    private Map<Long, User> users = new HashMap<>();
    @PostMapping
    public User postUser(@RequestBody User user) {
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
    public User putUser(@RequestBody User user) {
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

    @DeleteMapping
    public void delUser(@RequestBody User user) {
        users.remove(user.getId());
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable long id) {
        return users.get(id);
    }

    @GetMapping
    public List<User> getUsers() {
        return users.values().stream().toList();
    }


    private long getNextId() {
        long num = users.keySet().stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++num;
    }
}
