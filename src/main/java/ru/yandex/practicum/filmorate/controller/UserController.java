package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import java.util.List;


@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public User postUser(@Valid @RequestBody User user) {
        log.info("Добавление пользователя...");
        return userService.postUser(user);
    }


    @PutMapping
    public User putUser(@Valid @RequestBody User user) {
        log.info("Обновление пользователя...");
        return userService.putUser(user);
    }


    @PutMapping("/{id}/friends/{friendId}")
    public List<User> addFriend(@Valid @PathVariable long id, @PathVariable long friendId) {
       return userService.addFriend(id, friendId);
    }


    @DeleteMapping("/{id}/friends/{friendId}")
    public List<User> delFriend(@Valid @PathVariable long id, @PathVariable long friendId) {
        return userService.delFriend(id, friendId);
    }


    @GetMapping
    public List<User> getUsers() {
        return userService.getUsers();
    }


    @GetMapping("/{id}/friends")
    public List<User> getFriends(@Valid @PathVariable long id) {
        return userService.getFriends(id);
    }


    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> getMutualFriends(@Valid @PathVariable long id, @PathVariable long otherId) {
        log.info("Поиск общих друзей...");
        return userService.getMutualFriends(id, otherId);
    }
}
