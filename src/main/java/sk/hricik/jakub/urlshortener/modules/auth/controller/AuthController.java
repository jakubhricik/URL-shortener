package sk.hricik.jakub.urlshortener.modules.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sk.hricik.jakub.urlshortener.modules.auth.dto.LoginCredentialsDto;
import sk.hricik.jakub.urlshortener.modules.auth.dto.RegisterCredentialsDto;
import sk.hricik.jakub.urlshortener.modules.auth.dto.TokenResponse;
import sk.hricik.jakub.urlshortener.modules.auth.service.AuthService;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public TokenResponse login(@Valid @RequestBody LoginCredentialsDto credentials) {
        return authService.logInViaCredentials(credentials);
    }

    @PostMapping("/register")
    public TokenResponse register(@Valid @RequestBody RegisterCredentialsDto credentials) {
        return authService.registerViaCredentials(credentials);
    }
}
