package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class UserService {
    private UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public User addFriend(long id, long friendId) {
        User user = userStorage.getUser(id);
        if (userStorage.getUser(friendId) == null) {
            throw new NullPointerException("Пользователя не существует");
        }
        user.addFriend(friendId);
        return user;
    }

    public User delFriend(long id, long friendId) {
        User user = userStorage.getUser(id);
        if (user == null) {
            throw new NullPointerException("Пользователя с указанным ID не существует");
        }

        if (userStorage.getUser(friendId) == null) {
            throw new NullPointerException("Друга с указанным ID не существует");
        }

        if (!user.getFriends().contains(friendId)) {
            throw new ValidationException("Пользователь не был добавлен в друзья");
        }
        user.delFriend(friendId);
        return user;
    }

    public List<Long> getFriends(long id) {
        User user = userStorage.getUser(id);
        if (user == null) {
            throw new NullPointerException("Пользователя с указанным ID не существует");
        }
        return user.getFriends().stream().toList();
    }

    public List<Long> getMutualFriends(long id, long otherId) {
        Set<Long> list1 = userStorage.getUser(id).getFriends();
        List<Long> returnList = new ArrayList<>();
        for (long friendId : userStorage.getUser(otherId).getFriends()) {
            if (!list1.add(friendId)) {
                returnList.add(friendId);
            }
        }
        return returnList;
    }
}
