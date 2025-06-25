package com.jobapp.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/message")
public class MessageControoller {

    @Value("${app.message}")
    private String message;

    @GetMapping
    public ResponseEntity<?> getMessage() {
        return ResponseEntity.ok(message);
    }
}
