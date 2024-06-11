package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Service
public class UserService {
    private final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public List<User> addFriend(long id, long friendId) {
        User user = userStorage.getUser(id);
        User friend = userStorage.getUser(friendId);
        if (user == null) {
            throw new NotFoundException("Пользователя не существует");
        }

        if (friend == null) {
            throw new NotFoundException("Друга не существует");
        }

        user.addFriend(friendId);
        friend.addFriend(id);
        return Arrays.asList(user, friend);
    }

    public User delFriend(long id, long friendId) {
        User user = userStorage.getUser(id);
        User friend = userStorage.getUser(friendId);
        if (user == null) {
            throw new NotFoundException("Пользователя с указанным ID не существует");
        }

        if (friend == null) {
            throw new NotFoundException("Друга с указанным ID не существует");
        }

        if (!user.getFriends().contains(friendId)) {
            System.out.println("Пользователь не был добавлен в друзья");
            return user;
        }
        user.delFriend(friendId);
        friend.delFriend(id);
        return user;
    }

    public List<User> getFriends(long id) {
        List<User> returnList = new ArrayList<>();
        User user = userStorage.getUser(id);
        if (user == null) {
            throw new NullPointerException("Пользователя с указанным ID не существует");
        }
        for (long userId : user.getFriends()) {
            returnList.add(userStorage.getUser(userId));
        }
        return returnList;
    }

    public List<User> getMutualFriends(long id, long otherId) {
        Set<Long> list1 = userStorage.getUser(id).getFriends();
        List<User> returnList = new ArrayList<>();
        for (long friendId : userStorage.getUser(otherId).getFriends()) {
            if (!list1.add(friendId)) {
                returnList.add(userStorage.getUser(friendId));
            }
        }
        return returnList;
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
