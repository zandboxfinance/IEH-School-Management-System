package com.example.rds.service;

import com.example.rds.dao.UserDao;
import com.example.rds.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public int save(User user) {
        return userDao.save(user);
    }

    public int update(User user) {
        return userDao.update(user);
    }

    public User findById(int id) {
        return userDao.findById(id);
    }

    public int deleteById(int id) {
        return userDao.deleteById(id);
    }

    public List<User> findAll(int page, int size) {
        return userDao.findAll(page, size);
    }
}