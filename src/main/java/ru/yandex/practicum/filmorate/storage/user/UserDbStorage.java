package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.yandex.practicum.filmorate.model.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import java.sql.Date;
import java.util.Arrays;
import java.util.List;

@Component
@Qualifier("userDbStorage")
public class UserDbStorage implements UserStorage {
    private final JdbcTemplate jdbcTemplate;
    private int id = 1;

    public UserDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public User postUser(User user) {
        user.setId(id);
        id++;
        String request = "INSERT INTO users(name, email, login, birthday) " + "values(?, ?, ?, ?)";
        jdbcTemplate.update(request, user.getName(), user.getEmail(), user.getLogin(),
                Date.valueOf(user.getBirthday()));
        return user;
    }

    public User putUser(User user) {
        String request = "UPDATE users SET " + "name = ?, email = ?, login = ?, birthday = ?" + "WHERE id = ?";
        int result = jdbcTemplate.update(request, user.getEmail(), user.getLogin(),
                user.getName(), user.getBirthday(), user.getId());
        if (result == 0) {
            throw new NotFoundException("Указанного пользователя нет в базе данных");
        }

        return user;
    }

    public void delUser(User user) {
        String request = "DELETE FROM users WHERE id = ?";
        jdbcTemplate.update(request, user.getId());
    }

    public User getUser(long id) {
        try {
            String request = "SELECT * FROM users WHERE id = ?";
            return jdbcTemplate.queryForObject(request, new UserRowMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("Не найдено");
        }
    }

    public List<User> getUsers() {
        String request = "SELECT * FROM users";
        return jdbcTemplate.query(request, new UserRowMapper());
    }

    public List<User> addFriend(long userId, long friendId) {
        User user = getUser(userId);
        User friend = getUser(friendId);
        String request = "INSERT INTO friends(user_id, friend_id, status) " + "values(?, ?, ?)";
        if (getFriends(friendId).stream().mapToLong(User::getId).anyMatch(id -> id == userId)) {
            jdbcTemplate.update(request, userId, friendId, "confirmed");
            jdbcTemplate.update("UPDATE friends SET friend_id = ?, id = ?, status = ?",
                    friendId, userId, "confirmed");
        } else {
            jdbcTemplate.update(request, userId, friendId, "unconfirmed");
        }
        return Arrays.asList(user, friend);
    }

    public List<User> delFriend(long userId, long friendId) {
        User user = getUser(userId);
        User friend = getUser(friendId);
        String request = "DELETE FROM friends WHERE user_id = ? AND friend_id = ?";
        jdbcTemplate.update(request, userId, friendId);
        return Arrays.asList(user, friend);
    }

    public List<User> getFriends(long id) {
        if (getUser(id) == null) {
            throw new NotFoundException("Не найден человек с таким айди");
        }
        String request = "SELECT * FROM users WHERE id IN " +
                "(SELECT friend_id FROM friends WHERE user_id = ?)";
        return jdbcTemplate.query(request, new UserRowMapper(), id);
    }

    public List<User> getMutualFriends(long id, long otherId) {
        String request = "SELECT * FROM users WHERE id IN " +
                "(SELECT friend_id FROM friends WHERE user_id = ?) AND id IN " +
                "(SELECT friend_id FROM friends WHERE user_id = ?)";
        return jdbcTemplate.query(request, new UserRowMapper(), id, otherId);
    }
}