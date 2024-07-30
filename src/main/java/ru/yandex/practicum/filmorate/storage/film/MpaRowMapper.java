package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.*;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MpaRowMapper implements RowMapper<MPA> {
    @Override
    public MPA mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return new MPA(resultSet.getInt("id"), resultSet.getString("name"));
    }
}
