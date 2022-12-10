package sk.hricik.jakub.urlshortener.modules.common.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sk.hricik.jakub.urlshortener.modules.ApiException;
import sk.hricik.jakub.urlshortener.modules.auth.security.jwt.JwtAuthentication;
import sk.hricik.jakub.urlshortener.modules.auth.security.jwt.JwtTokenParser;
import sk.hricik.jakub.urlshortener.modules.auth.security.jwt.TokenException;
import sk.hricik.jakub.urlshortener.modules.common.dto.AppUserDto;
import sk.hricik.jakub.urlshortener.modules.common.model.AppUser;
import sk.hricik.jakub.urlshortener.modules.common.repository.AppUserRepository;
import sk.hricik.jakub.urlshortener.modules.common.service.AppUserService;
import sk.hricik.jakub.urlshortener.util.ModelMapper;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AppUserServiceImpl implements AppUserService {
    private final AppUserRepository appUserRepository;
    private final ModelMapper modelMapper;

    private final PasswordEncoder passwordEncoder;

    private final HttpServletRequest httpServletRequest;

    private final JwtTokenParser tokenParser;

    @Override
    public AppUser getLoggedUser() {
        String accessToken = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION).substring("Bearer ".length());
        Optional<AppUser> user;
        try {
            JwtAuthentication authentication = tokenParser.parseAccessToken(accessToken);
            user = appUserRepository.findById(Long.parseLong(authentication.getName()));
            if (user.isEmpty())
                throw new ApiException(ApiException.FaultType.OBJECT_NOT_FOUND, "There is no user with ID: " + authentication.getName());
        } catch (TokenException ex) {
            throw new ApiException(ApiException.FaultType.WRONG_CREDENTIALS, ex.getMessage());
        }
        return user.get();
    }

    @Override
    public AppUser saveUserDto(AppUserDto appUser) {
        AppUser user = modelMapper.mapAppUserDtoToAppUser(appUser);
        return saveUserAndEncodePassword(user);
    }

    @Override
    public AppUser saveUserAndEncodePassword(AppUser newUser) {
        Optional<AppUser> user = appUserRepository.findByAccountId(newUser.getAccountId());
        if (user.isPresent())
            throw new ApiException(ApiException.FaultType.OBJECT_ALREADY_EXISTS, "Already exists user with username: " + newUser.getAccountId());

        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        return appUserRepository.saveAndFlush(newUser);
    }

    @Override
    public AppUserDto getUserDto(String username) {
        return modelMapper.mapAppUserToAppUserDto(getUser(username));
    }

    @Override
    public AppUser getUser(String username) {
        Optional<AppUser> user = appUserRepository.findByAccountId(username);
        if (user.isEmpty())
            throw new ApiException(ApiException.FaultType.OBJECT_NOT_FOUND, "There is not such user wit username: " + username);
        return user.get();
    }

    @Override
    public List<AppUserDto> getUsers() {
        return appUserRepository.findAll()
                .stream()
                .map(modelMapper::mapAppUserToAppUserDto).toList();
    }

}
