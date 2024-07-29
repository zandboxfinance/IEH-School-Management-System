package com.example.rds.controller;

import com.example.rds.model.User;
import com.example.rds.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Integer> createUser(@RequestBody User user) {
        int id = userService.save(user);
        return ResponseEntity.ok(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Integer> updateUser(@PathVariable int id, @RequestBody User user) {
        user.setId(id);
        int rows = userService.update(user);
        return ResponseEntity.ok(rows);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        User user = userService.findById(id);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> deleteUserById(@PathVariable int id) {
        int rows = userService.deleteById(id);
        return ResponseEntity.ok(rows);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(@RequestParam int page, @RequestParam int size) {
        List<User> users = userService.findAll(page, size);
        return ResponseEntity.ok(users);
    }
}