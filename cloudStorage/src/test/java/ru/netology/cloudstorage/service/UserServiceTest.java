package ru.netology.cloudstorage.service;

import ru.netology.cloudstorage.model.entity.User;
import ru.netology.cloudstorage.repository.UserRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {UserService.class})
@ExtendWith(SpringExtension.class)
public class UserServiceTest {
    @MockBean
    private UserRepository userRepositoryTest;

    @Autowired
    private UserService userServiceTest;

    @Test
    @SneakyThrows
    void loadUserByUsernameCorrectRequest() {
        String testUsername = "testUser@mail.ru";
        User user = User.builder()
                .email(testUsername).build();
        when(userRepositoryTest.findUserByEmail(Mockito.any())).thenReturn(Optional.of(user));
        User result = userServiceTest.findUserByEmail(testUsername).orElse(null);
        Assertions.assertEquals(user, result);
    }
}