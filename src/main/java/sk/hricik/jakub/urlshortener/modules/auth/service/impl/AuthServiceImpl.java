package sk.hricik.jakub.urlshortener.modules.auth.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sk.hricik.jakub.urlshortener.modules.ApiException;
import sk.hricik.jakub.urlshortener.modules.auth.dto.LoginCredentialsDto;
import sk.hricik.jakub.urlshortener.modules.auth.dto.RegisterCredentialsDto;
import sk.hricik.jakub.urlshortener.modules.auth.dto.TokenResponse;
import sk.hricik.jakub.urlshortener.modules.auth.service.AuthService;
import sk.hricik.jakub.urlshortener.modules.auth.service.TokenService;
import sk.hricik.jakub.urlshortener.modules.common.model.AppUser;
import sk.hricik.jakub.urlshortener.modules.common.repository.AppUserRepository;
import sk.hricik.jakub.urlshortener.modules.common.service.AppUserService;
import sk.hricik.jakub.urlshortener.modules.common.service.RoleService;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final TokenService tokenService;
    private final AppUserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final AppUserRepository userRepository;


    @Override
    public TokenResponse logInViaCredentials(LoginCredentialsDto credentials) {
        AppUser user = userRepository.findByUsername(credentials.getUsername())
                .orElseThrow(
                        () -> new ApiException(ApiException.FaultType.WRONG_CREDENTIALS,
                        "Wrong username", ApiException.SpecificCodeType.WRONG_USERNAME)
                );
        if (!passwordEncoder.matches(credentials.getPassword(), user.getPassword())) {
            throw new ApiException(ApiException.FaultType.WRONG_CREDENTIALS, "Wrong password", ApiException.SpecificCodeType.WRONG_PASSWORD);
        }
        return tokenService.createTokenResponse(user);
    }

    @Override
    public TokenResponse registerViaCredentials(RegisterCredentialsDto credentials) {
        Optional<AppUser> user = userRepository.findByUsername(credentials.getUsername());
        if (user.isPresent())
            throw new ApiException(ApiException.FaultType.OBJECT_ALREADY_EXISTS, "Already exists user with username: " + credentials.getUsername());

        if (!credentials.getPassword().equals(credentials.getPasswordVerification()))
            throw new ApiException(ApiException.FaultType.WRONG_CREDENTIALS, "Password and PasswordVerification are not same", ApiException.SpecificCodeType.WRONG_PASSWORD);


        AppUser newUser = AppUser.builder()
                .username(credentials.getUsername())
                .password(credentials.getPassword())
                .roles(new ArrayList<>())
                .build();
        userService.saveUserAndEncodePassword(newUser);
        newUser = roleService.addRoleToUser("ROLE_USER", newUser.getUsername());

        return tokenService.createTokenResponse(newUser);
    }
}
