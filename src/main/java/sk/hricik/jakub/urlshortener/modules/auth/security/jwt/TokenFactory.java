package sk.hricik.jakub.urlshortener.modules.auth.security.jwt;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface TokenFactory {
    String createAccessToken(String subject, List<String> roles, Map<String, Object> additionalClaims, Date issuedAt);

    String createRefreshToken(String subject, Date issuedAt);
}
