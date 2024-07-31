package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.filmorate.model.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import java.sql.Date;
import java.util.List;

@Component
@Qualifier("userDbStorage")
public class UserDbStorage {
    private JdbcTemplate jdbcTemplate;

    public UserDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public void postUser(User user) {
        String request = "INSERT INTO users(name, email, login, birthday) " + "values(?, ?, ?, ?)";
        jdbcTemplate.update(request, user.getName(), user.getEmail(), user.getLogin(),
                Date.valueOf(user.getBirthday()));
        System.out.println("Пользователь добавлен в базу данных");
    }

    public void putUser(User user) {
        String request = "UPDATE users SET " + "name = ?, email = ?, login = ?, birthday = ?" + "WHERE id = ?";
        int result = jdbcTemplate.update(request, user.getEmail(), user.getLogin(),
                user.getName(), user.getBirthday(), user.getId());
        if (result == 0) {
            System.out.println("Указанного пользователя нет в базе данных");
        } else {
            System.out.println("Запись обновлена в базе данных");
        }
    }

    public void delUser (User user) {
        String request = "DELETE FROM users WHERE id = ?";
        jdbcTemplate.update(request, user.getId());
        System.out.println("Запись успешно удалена из базы данных");
    }

    public User getUser(long id) {
        String request = "SELECT * FROM users WHERE id = ?";
        return jdbcTemplate.queryForObject(request, new UserRowMapper(), id);
    }

    public void getUsers() {
        String request = "SELECT * FROM users";
        jdbcTemplate.query(request, new UserRowMapper());
    }

    public void addFriend(long user_id, long friend_id) {
        String request = "INSERT INTO friends(user_id, friend_id, status) " + "values(?, ?, ?)";
        if (getUser(friend_id).getFriends().contains(user_id)) {
            jdbcTemplate.update(request, user_id, friend_id, "confirmed");
            jdbcTemplate.update("UPDATE friends SET friend_id = ?, id = ?, status = ?",
                    friend_id, user_id, "confirmed");
            System.out.println("Друг успешно добавлен. Дружба подтвержденная");
        } else {
            jdbcTemplate.update(request, user_id, friend_id, "unconfirmed");
            System.out.println("Друг успешно добавлен. Дружба неподтвержденная");
        }
    }

    public void delFriend(long user_id, long friend_id) {
            String request = "DELETE FROM friends WHERE user_id = ? AND friend_id = ?";
            jdbcTemplate.update(request, user_id, friend_id);
            jdbcTemplate.update(request, friend_id, user_id);
            System.out.println("Пользователь удален из друзей");
    }

    public List<User> getFriends(long id) {
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