package sk.hricik.jakub.urlshortener.modules.auth.service;

import sk.hricik.jakub.urlshortener.modules.auth.dto.LoginCredentialsDto;
import sk.hricik.jakub.urlshortener.modules.auth.dto.RegisterCredentialsDto;
import sk.hricik.jakub.urlshortener.modules.auth.dto.TokenResponse;

public interface AuthService {
    TokenResponse logInViaCredentials(LoginCredentialsDto credentials);

    TokenResponse registerViaCredentials(RegisterCredentialsDto credentials);
}
