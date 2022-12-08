package sk.hricik.jakub.urlshortener.modules.auth.service;


import sk.hricik.jakub.urlshortener.modules.auth.dto.RefreshTokenDto;
import sk.hricik.jakub.urlshortener.modules.auth.dto.TokenResponse;
import sk.hricik.jakub.urlshortener.modules.common.model.AppUser;

public interface TokenService {

    /**
     * creates token response for provided user
     * @param user token is processed for
     * @return tokenResponse with access and refresh token
     */
    TokenResponse createTokenResponse(AppUser user);

    /**
     * parses refresh token and retrieves user id from it
     * @param refreshTokenDto token to be parsed
     * @return user id
     */
    String checkAndParseRefreshTokenToUserId(RefreshTokenDto refreshTokenDto);
}
