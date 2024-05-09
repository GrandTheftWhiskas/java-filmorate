package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.Level;
import org.slf4j.LoggerFactory;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {
    private static Logger log = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger("UserController");
    private Map<Integer, User> users = new HashMap<>();

    @PostMapping
    public User userPost(@RequestBody User user) throws ValidationException {
            log.setLevel(Level.INFO);
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
    public User userPut(@RequestBody User user) throws ValidationException {
            try {
                log.setLevel(Level.INFO);
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
            } catch (ValidationException e) {
                System.out.println(e.getMessage());
            }
            return user;
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
