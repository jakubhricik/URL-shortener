package sk.hricik.jakub.urlshortener.modules.auth.service;

import sk.hricik.jakub.urlshortener.modules.auth.dto.*;
import sk.hricik.jakub.urlshortener.modules.auth.dto.AccountCreationResponse;

public interface AuthService {
    AccountLoginResponse login(AccountLoginDto credentials);

    AccountCreationResponse createAccount(AccountCreationDto credentials);
}
