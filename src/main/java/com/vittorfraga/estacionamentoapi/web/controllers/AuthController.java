package com.vittorfraga.estacionamentoapi.web.controllers;

import com.vittorfraga.estacionamentoapi.domain.user.User;
import com.vittorfraga.estacionamentoapi.usecases.auth.CreateUserUseCase;
import com.vittorfraga.estacionamentoapi.usecases.auth.LoginUseCase;
import com.vittorfraga.estacionamentoapi.usecases.auth.dto.LoginRequest;
import com.vittorfraga.estacionamentoapi.usecases.auth.dto.LoginResponse;
import com.vittorfraga.estacionamentoapi.web.AuthAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Validated
public class AuthController implements AuthAPI {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private LoginUseCase loginUseCase;

    @Autowired
    private CreateUserUseCase createUserUseCase;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest userLogin) {
        LoginResponse response = loginUseCase.execute(userLogin);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody LoginRequest userLogin) {
        User user = createUserUseCase.execute(userLogin);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
}