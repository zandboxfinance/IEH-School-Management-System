package com.example.rds.dao;

import com.example.rds.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UserDao {
    private final JdbcTemplate jdbcTemplate;

    public UserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int save(User user) {
        return jdbcTemplate.update("INSERT INTO tb_user (name, email) VALUES (?, ?)",
                user.getName(), user.getEmail());
    }

    public int update(User user) {
        return jdbcTemplate.update("UPDATE tb_user SET name = ?, email = ? WHERE id = ?",
                user.getName(), user.getEmail(), user.getId());
    }

    public User findById(int id) {
        return jdbcTemplate.queryForObject("SELECT * FROM tb_user WHERE id = ?", new Object[]{id}, new UserRowMapper());
    }

    public int deleteById(int id) {
        return jdbcTemplate.update("DELETE FROM tb_user WHERE id = ?", id);
    }

    public List<User> findAll(int page, int size) {
        int offset = (page - 1) * size;
        return jdbcTemplate.query("SELECT * FROM tb_user LIMIT ? OFFSET ?", new Object[]{size, offset}, new UserRowMapper());
    }

    private static class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setName(rs.getString("name"));
            user.setEmail(rs.getString("email"));
            user.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
            return user;
        }
    }
}