package com.vittorfraga.estacionamentoapi.web;

import com.vittorfraga.estacionamentoapi.usecases.auth.dto.LoginRequest;
import com.vittorfraga.estacionamentoapi.usecases.auth.dto.LoginResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Auth")
@RequestMapping("/auth")
@Validated
public interface AuthAPI {

    @PostMapping("/login")
    @Operation(summary = "Login user and return token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User logged in successfully"),
            @ApiResponse(responseCode = "400", description = "Missing parameter"),
            @ApiResponse(responseCode = "404", description = "Username not found"),
    })
    ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest userLogin);

    @PostMapping("/register")
    @Operation(summary = "Register a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created"),
            @ApiResponse(responseCode = "400", description = "Missing parameter"),
            @ApiResponse(responseCode = "409", description = "Username already in use"),
    })
    ResponseEntity<?> register(@RequestBody @Valid LoginRequest userLogin);


}