package org.example.lab4.controller;

import org.example.lab4.entity.User;
import org.example.lab4.repository.UserRepository;
import org.example.lab4.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    public JwtUtil jwtUtil;

    @Autowired
    public UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/getIdFromToken")
    public Long getIdFromToken(String token) {
        logger.info("Получен токен: {}", token);
        if (token.startsWith("Bearer")) {
            token = token.substring(6).trim();
        }
        logger.info("Обработанный токен: {}", token);

        try {
            Long userId = jwtUtil.extractUserId(token);
            logger.info("Извлечён userId: {}", userId);
            return userId;
        } catch (Exception e) {
            logger.error("Ошибка при извлечении userId", e);
            throw e;
        }
    }

    @GetMapping("/getEmailById")
    public String getEmailById(Long id) {
        User user = userRepository.findById(id).get();
        return user.getEmail();
    }

    @GetMapping("/getUsernameById")
    public String getUsernameById(Long id) {
        User user = userRepository.findById(id).get();
        return user.getUsername();
    }
}
