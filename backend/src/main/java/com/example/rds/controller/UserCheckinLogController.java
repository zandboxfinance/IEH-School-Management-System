package com.example.rds.controller;

import com.example.rds.model.UserCheckinLog;
import com.example.rds.service.UserCheckinLogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/checkin-logs")
public class UserCheckinLogController {
    private final UserCheckinLogService userCheckinLogService;

    public UserCheckinLogController(UserCheckinLogService userCheckinLogService) {
        this.userCheckinLogService = userCheckinLogService;
    }

    @PostMapping
    public ResponseEntity<Integer> createCheckinLog(@RequestBody UserCheckinLog log) {
        int id = userCheckinLogService.save(log);
        return ResponseEntity.ok(id);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UserCheckinLog>> getCheckinLogsByUserId(@PathVariable int userId) {
        List<UserCheckinLog> logs = userCheckinLogService.findByUserId(userId);
        return ResponseEntity.ok(logs);
    }
}