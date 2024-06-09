package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;
import java.util.List;


@RestController
@RequestMapping("/users")
public class UserController {
    UserService userService;
    InMemoryUserStorage userStorage;

    @Autowired
    public UserController(UserService userService, InMemoryUserStorage userStorage) {
        this.userService = userService;
        this.userStorage = userStorage;
    }

    @PostMapping
    public User postUser(@RequestBody User user) {
        return userStorage.postUser(user);
    }

    @PutMapping
    public User putUser(@RequestBody User user) {
        return userStorage.putUser(user);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public User addFriend(@PathVariable long id, @PathVariable long friendId) {
        return userService.addFriend(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public User delFriend(@PathVariable long id, @PathVariable long friendId) {
        return userService.delFriend(id, friendId);
    }

    @GetMapping
    public List<User> getUsers() {
        return userStorage.getUsers();
    }

    @GetMapping("/{id}/friends")
    public List<Long> getFriends(@RequestBody @PathVariable long id) {
        return userService.getFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> getMutualFriends(@RequestBody @PathVariable long id, @PathVariable long otherId) {
        return userService.getMutualFriends(id, otherId);
    }
}
