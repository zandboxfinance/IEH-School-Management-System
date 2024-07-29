package com.example.rds.service;

import com.example.rds.dao.UserCheckinLogDao;
import com.example.rds.model.UserCheckinLog;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserCheckinLogService {
    private final UserCheckinLogDao userCheckinLogDao;

    public UserCheckinLogService(UserCheckinLogDao userCheckinLogDao) {
        this.userCheckinLogDao = userCheckinLogDao;
    }

    public int save(UserCheckinLog log) {
        return userCheckinLogDao.save(log);
    }

    public List<UserCheckinLog> findByUserId(int userId) {
        return userCheckinLogDao.findByUserId(userId);
    }
}