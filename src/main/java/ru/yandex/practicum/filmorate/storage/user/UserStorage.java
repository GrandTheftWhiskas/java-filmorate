package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.web.bind.annotation.PathVariable;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserStorage {
    User postUser(User user);

    User putUser(User user);

    void delUser(User user);

    User getUser(long id);

    List<User> getUsers();
}
