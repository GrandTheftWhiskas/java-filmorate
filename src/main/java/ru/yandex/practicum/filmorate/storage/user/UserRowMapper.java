package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.model.User;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        User user = new User(resultSet.getString("email"), resultSet.getString("login"),
                resultSet.getDate("birthday").toLocalDate());
        user.setId(resultSet.getLong("id"));
        user.setName(resultSet.getString("name"));
        return user;
    }
}
