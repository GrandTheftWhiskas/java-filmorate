package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import java.util.*;

@Component
public class InMemoryUserStorage implements UserStorage {
    private Map<Long, User> users = new HashMap<>();
    private final UserDbStorage userDbStorage;

    @Autowired
    public InMemoryUserStorage(UserDbStorage userDbStorage) {
        this.userDbStorage = userDbStorage;
    }


    public User postUser(User user) {
        return userDbStorage.postUser(user);
    }

    public User putUser(User user) {
        return userDbStorage.putUser(user);
    }

    public void delUser(User user) {
        users.remove(user.getId());
    }

    public User getUser(long id) {
        return userDbStorage.getUser(id);
    }

    public List<User> getUsers() {
        return userDbStorage.getUsers();
    }

    private long getNextId() {
        long num = users.keySet().stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++num;
    }
}
