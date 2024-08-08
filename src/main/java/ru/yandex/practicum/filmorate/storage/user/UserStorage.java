package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;
import java.util.List;

public interface UserStorage {
    User postUser(User user);

    User putUser(User user);

    void delUser(User user);

    User getUser(long id);

    List<User> getUsers();

    List<User> addFriend(long userId, long friendId);

    List<User> delFriend(long userId, long friendId);

    List<User> getFriends(long id);

    List<User> getMutualFriends(long id, long otherId);
}
