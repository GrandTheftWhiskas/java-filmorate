package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserDbStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;
import java.time.LocalDate;
import java.util.List;

@Service
public class UserService {
    private final UserStorage userStorage;
    private final UserDbStorage userDbStorage;

    @Autowired
    public UserService(UserStorage userStorage, UserDbStorage userDbStorage) {
        this.userStorage = userStorage;
        this.userDbStorage = userDbStorage;
    }

    public List<User> addFriend(long id, long friendId) {
        return userDbStorage.addFriend(id, friendId);
    }

    public User delFriend(long id, long friendId) {
        return userDbStorage.delFriend(id, friendId);
    }

    public List<User> getFriends(long id) {
        return userDbStorage.getFriends(id);
    }

    public List<User> getMutualFriends(long id, long otherId) {
        return userDbStorage.getMutualFriends(id, otherId);
    }

    public User postUser(User user) {
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
        return userStorage.postUser(user);
    }

    public User putUser(User user) {
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

        return userStorage.putUser(user);
    }

    public List<User> getUsers() {
        return userStorage.getUsers();
    }
}
