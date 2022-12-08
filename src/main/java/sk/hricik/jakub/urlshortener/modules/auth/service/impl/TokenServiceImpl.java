package sk.hricik.jakub.urlshortener.modules.auth.service.impl;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sk.hricik.jakub.urlshortener.modules.ApiException;
import sk.hricik.jakub.urlshortener.modules.auth.dto.RefreshTokenDto;
import sk.hricik.jakub.urlshortener.modules.auth.dto.TokenResponse;
import sk.hricik.jakub.urlshortener.modules.auth.security.jwt.TokenException;
import sk.hricik.jakub.urlshortener.modules.auth.security.jwt.TokenFactory;
import sk.hricik.jakub.urlshortener.modules.auth.security.jwt.TokenParser;
import sk.hricik.jakub.urlshortener.modules.auth.service.TokenService;
import sk.hricik.jakub.urlshortener.modules.common.model.AppUser;
import sk.hricik.jakub.urlshortener.modules.common.model.Role;

import java.util.*;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final TokenFactory tokenFactory;
    private final TokenParser tokenParser;


    @Override
    public TokenResponse createTokenResponse(AppUser user) {
        Date issuedAt = new Date();
        List<String> roles = getRoles(user);

        String accessToken = tokenFactory.createAccessToken(user.getId(), roles, issuedAt);
        String refreshToken = tokenFactory.createRefreshToken(user.getId(), issuedAt);
        return new TokenResponse(accessToken, refreshToken);
    }

    @Override
    public String checkAndParseRefreshTokenToUserId(RefreshTokenDto refreshTokenDto) {
        try {
            return (String) tokenParser.parseRefreshToken(refreshTokenDto.getRefreshToken());
        } catch (TokenException e) {
            throw new ApiException(ApiException.FaultType.WRONG_REFRESH_TOKEN, "Wrong refresh token");
        }
    }

    private List<String> getRoles(AppUser user) {
        return user.getRoles().stream().map(Role::getName).toList();
    }

}
