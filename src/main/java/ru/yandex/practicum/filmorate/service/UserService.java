package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Service
public class UserService {
    private UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public List<User> addFriend(long id, long friendId) {
        User user1 = userStorage.getUser(id);
        User user2 = userStorage.getUser(friendId);
        if (user1 == null) {
            throw new NullPointerException("Пользователя не существует");
        }

        if (user2 == null) {
            throw new NullPointerException("Друга не существует");
        }

        user1.addFriend(friendId);
        user2.addFriend(id);
        return Arrays.asList(user1, user2);
    }

    public User delFriend(long id, long friendId) {
        User user = userStorage.getUser(id);
        User user1 = userStorage.getUser(friendId);
        if (user == null) {
            throw new NullPointerException("Пользователя с указанным ID не существует");
        }

        if (user1 == null) {
            throw new NullPointerException("Друга с указанным ID не существует");
        }

        if (!user.getFriends().contains(friendId)) {
            System.out.println("Пользователь не был добавлен в друзья");
            return user;
        }
        user.delFriend(friendId);
        user1.delFriend(id);
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
}
