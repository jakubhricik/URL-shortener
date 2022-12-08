package sk.hricik.jakub.urlshortener.modules.auth.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sk.hricik.jakub.urlshortener.modules.ApiException;
import sk.hricik.jakub.urlshortener.modules.auth.dto.*;
import sk.hricik.jakub.urlshortener.modules.auth.dto.AccountCreationResponse;
import sk.hricik.jakub.urlshortener.modules.auth.service.AuthService;
import sk.hricik.jakub.urlshortener.modules.auth.service.TokenService;
import sk.hricik.jakub.urlshortener.modules.common.model.AppUser;
import sk.hricik.jakub.urlshortener.modules.common.repository.AppUserRepository;
import sk.hricik.jakub.urlshortener.modules.common.service.AppUserService;
import sk.hricik.jakub.urlshortener.modules.common.service.RoleService;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    public static final int PASSWORD_LENGTH = 8;

    private final TokenService tokenService;
    private final AppUserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final AppUserRepository userRepository;


    @Override
    public AccountLoginResponse login(AccountLoginDto credentials) {
        AppUser user = userRepository.findByUsername(credentials.getAccountId())
                .orElseThrow(
                        () -> new ApiException(ApiException.FaultType.WRONG_CREDENTIALS,
                        "Wrong username", ApiException.SpecificCodeType.WRONG_USERNAME)
                );
        if (!passwordEncoder.matches(credentials.getPassword(), user.getPassword())) {
            throw new ApiException(ApiException.FaultType.WRONG_CREDENTIALS, "Wrong password", ApiException.SpecificCodeType.WRONG_PASSWORD);
        }

        return AccountLoginResponse.builder()
                .success(true)
                .description("Successfully logged in")
                .tokens(tokenService.createTokenResponse(user))
                .build();
    }

    @Override
    public AccountCreationResponse createAccount(AccountCreationDto credentials) {
        Optional<AppUser> user = userRepository.findByUsername(credentials.getAccountId());
        if (user.isPresent())
            throw new ApiException(ApiException.FaultType.OBJECT_ALREADY_EXISTS, "Already exists user with account identifier: " + credentials.getAccountId());

        String randomPassword;
        try {
            randomPassword = generateRandomPassword();
        } catch (NoSuchAlgorithmException ex) {
            log.error("random password generation problem:\n{}", ex.getMessage());
            throw new ApiException(ApiException.FaultType.GENERAL_ERROR, "Problem while generating password, please contact support team.");
        }

        AppUser newUser = AppUser.builder()
                .username(credentials.getAccountId())
                .password(randomPassword)
                .roles(new ArrayList<>())
                .build();

        userService.saveUserAndEncodePassword(newUser);
        newUser = roleService.addRoleToUser("ROLE_USER", newUser.getUsername());

        return AccountCreationResponse.builder()
                .success(true)
                .description("Your account is opened")
                .generatedPassword(randomPassword)
                .tokens(tokenService.createTokenResponse(newUser))
                .build();
    }

    private String generateRandomPassword() throws NoSuchAlgorithmException {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        Random random = SecureRandom.getInstanceStrong();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(PASSWORD_LENGTH)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
