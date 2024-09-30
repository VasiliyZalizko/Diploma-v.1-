package ru.netology.cloudstorage.jwt;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

@SpringBootTest
public class JwtTokenUtilsTest {
    static UserDetails userDetails;
    static String testEmail = "test@mail.ru";
    static String testPassword = "test";
    static String testRole = "ROLE_USER";
    String testToken;

    @Autowired
    JwtTokenUtils jwtTokenUtils;

    @BeforeAll
    static void prepare() {
        userDetails = User.builder()
                .username(testEmail)
                .password(testPassword)
                .authorities(testRole)
                .build();
    }

    @BeforeEach
    void setUp() {
        testToken = jwtTokenUtils.generateToken(userDetails);
    }

    @Test
    void generateToken() {
        userDetails = User.builder()
                .username(testEmail)
                .password(testPassword)
                .authorities(testRole)
                .build();
        String testToken = jwtTokenUtils.generateToken(userDetails);
        Assertions.assertNotNull(testToken);
    }

    @Test
    void getUsernameFromToken() {
        String result = jwtTokenUtils.getUsernameFromToken(testToken);
        Assertions.assertEquals(testEmail, result);
    }

    @Test
    void getRole() {
        String result = jwtTokenUtils.getRole(testToken);
        Assertions.assertEquals(testRole, result);
    }
}