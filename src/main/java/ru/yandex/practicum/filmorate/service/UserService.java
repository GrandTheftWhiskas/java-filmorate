package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
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

    @PutMapping("/users/{id}/friend/{friendId}")
    public void addFriend(@PathVariable long id, @PathVariable long friendId) {
        User user = userStorage.getUser(id);
        user.addFriend(friendId);
    }

    @DeleteMapping("/users/{id}/friends/{friendId}")
    public void delFriend(@PathVariable long id, @PathVariable long friendId) {
        User user = userStorage.getUser(id);
        user.delFriend(friendId);
    }

    @GetMapping("/users/{id}/friends")
    public List<Long> getFriends(@PathVariable long id) {
        User user = userStorage.getUser(id);
        return user.getFriends().stream().toList();
    }

    @GetMapping("/users/{id}/friends/common/{otherId}")
    public List<Long> getMutualFriends(@PathVariable long id, @PathVariable long otherId) {
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
