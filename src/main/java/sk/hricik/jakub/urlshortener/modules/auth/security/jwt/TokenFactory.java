package sk.hricik.jakub.urlshortener.modules.auth.security.jwt;

import java.util.Date;
import java.util.List;

public interface TokenFactory {
    String createAccessToken(Long subject, List<String> roles, Date issuedAt);

    String createRefreshToken(Long subject, Date issuedAt);
}
