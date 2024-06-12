package com.bigbox.labreports.system.controller;

import com.bigbox.labreports.system.core.results.DataResult;
import com.bigbox.labreports.system.core.results.Result;
import com.bigbox.labreports.system.entity.dtos.user.UserForLoginRequest;
import com.bigbox.labreports.system.entity.dtos.user.UserForRegisterRequest;
import com.bigbox.labreports.system.service.contracts.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {


    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<Result> register(@RequestBody UserForRegisterRequest userForRegister) {
        Result result = authService.register(userForRegister);
        return new ResponseEntity<>(result, result.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/login")
    public ResponseEntity<DataResult<String>> login(@RequestBody UserForLoginRequest userForLogin) {
        DataResult<String> result = authService.login(userForLogin);
        return new ResponseEntity<>(result, result.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }
}
