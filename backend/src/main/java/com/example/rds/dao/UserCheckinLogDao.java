package com.example.rds.dao;

import com.example.rds.model.UserCheckinLog;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UserCheckinLogDao {
    private final JdbcTemplate jdbcTemplate;

    public UserCheckinLogDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int save(UserCheckinLog log) {
        return jdbcTemplate.update("INSERT INTO tb_user_checkin_log (user_id, checkin_time) VALUES (?, ?)",
                log.getUserId(), log.getCheckinTime());
    }

    public List<UserCheckinLog> findByUserId(int userId) {
        return jdbcTemplate.query("SELECT * FROM tb_user_checkin_log WHERE user_id = ?", new Object[]{userId}, new UserCheckinLogRowMapper());
    }

    private static class UserCheckinLogRowMapper implements RowMapper<UserCheckinLog> {
        @Override
        public UserCheckinLog mapRow(ResultSet rs, int rowNum) throws SQLException {
            UserCheckinLog log = new UserCheckinLog();
            log.setId(rs.getInt("id"));
            log.setUserId(rs.getInt("user_id"));
            log.setCheckinTime(rs.getTimestamp("checkin_time").toLocalDateTime());
            return log;
        }
    }
}