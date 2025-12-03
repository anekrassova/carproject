package org.example.lab4;

import static org.assertj.core.api.Assertions.assertThat;

import org.example.lab4.controller.AuthController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApplicationTests {

    @Autowired
    private AuthController controller;

    @Test
    void contextLoads() {
        assertThat(controller).isNotNull();
    }
}
