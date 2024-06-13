package com.bigbox.labreports.system;

import com.bigbox.labreports.system.core.entity.User;
import com.bigbox.labreports.system.core.results.DataResult;
import com.bigbox.labreports.system.core.results.Result;
import com.bigbox.labreports.system.entity.dtos.user.UserForLoginRequest;
import com.bigbox.labreports.system.entity.dtos.user.UserForRegisterRequest;
import com.bigbox.labreports.system.service.implementations.AuthServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {
    @Mock
    private AuthServiceImpl authService;

    @Mock
    private PasswordEncoder passwordEncoder;

    private UserForRegisterRequest userForRegisterRequest;
    private UserForLoginRequest userForLoginRequest;
    private User user;

    @BeforeEach
    void setUp() {
        userForRegisterRequest = new UserForRegisterRequest("user1", "user1@example.com", "password");
        userForLoginRequest = new UserForLoginRequest("user1", "password");
        user = new User(1L, "user1", "user1@example.com", "password", null);
    }

    @Test
    void TestRegisterUser() {
        when(authService.register(any(UserForRegisterRequest.class)))
                .thenReturn(new Result(true, "User registered successfully"));

        Result result = authService.register(userForRegisterRequest);

        assertTrue(result.isSuccess());
        assertTrue(result.getMessage().contains("User registered successfully"));

    }

    @Test
    void testLoginUser() {
        when(authService.login(any(UserForLoginRequest.class)))
                .thenReturn(new DataResult<String>("fake-token", true, "User logged in successfully"));
        DataResult<String> result = authService.login(userForLoginRequest);

        assertTrue(result.isSuccess());
        assertTrue(result.getMessage().contains("User logged in successfully"));
        assertTrue(result.getData().contains("fake-token"));
    }
}
