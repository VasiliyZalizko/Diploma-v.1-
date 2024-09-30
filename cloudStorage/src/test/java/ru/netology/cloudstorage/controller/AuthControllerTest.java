package ru.netology.cloudstorage.controller;

import ru.netology.cloudstorage.model.AuthRequest;
import ru.netology.cloudstorage.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {
    @Mock
    private AuthService authServiceTest;
    @InjectMocks
    private AuthController authControllerTest;
    private final String testEmail = "test@mail.ru";
    private final String testPassword = "test";
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(authControllerTest).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    @SneakyThrows
    void loginCorrectRequest() {
        AuthRequest testAuthRequest = new AuthRequest(testEmail, testPassword);
        String testAuthRequestJson = objectMapper.writeValueAsString(testAuthRequest);
        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(testAuthRequestJson))
                .andExpect(status().isOk());
        verify(authServiceTest, times(1)).login(Mockito.any());
    }
}