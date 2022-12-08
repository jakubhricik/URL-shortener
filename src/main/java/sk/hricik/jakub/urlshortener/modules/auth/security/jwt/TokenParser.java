package sk.hricik.jakub.urlshortener.modules.auth.security.jwt;

import org.springframework.security.core.Authentication;

public interface TokenParser {
    Authentication parseAccessToken(String token) throws TokenException;

    Object parseRefreshToken(String token) throws TokenException;
}
