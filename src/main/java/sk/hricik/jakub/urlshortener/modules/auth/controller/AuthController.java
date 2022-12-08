package sk.hricik.jakub.urlshortener.modules.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sk.hricik.jakub.urlshortener.modules.auth.dto.*;
import sk.hricik.jakub.urlshortener.modules.auth.service.AuthService;

import javax.validation.Valid;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public AccountLoginResponse login(@Valid @RequestBody AccountLoginDto credentials) {
        return authService.login(credentials);
    }

    @PostMapping()
    public AccountCreationResponse accountCreation(@Valid @RequestBody AccountCreationDto credentials) {
        return authService.createAccount(credentials);
    }
}
