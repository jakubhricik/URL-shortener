package sk.hricik.jakub.urlshortener.modules.auth.security.jwt;

import io.jsonwebtoken.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static sk.hricik.jakub.urlshortener.modules.auth.security.jwt.JwtTokenFactory.AUTHORITIES_CLAIM_NAME;
import static sk.hricik.jakub.urlshortener.modules.auth.security.jwt.JwtTokenFactory.TOKEN_TYPE_CLAIM_NAME;

public class JwtTokenParser implements TokenParser {

    private final String signingKey;

    public JwtTokenParser(String signingKey) {
        this.signingKey = signingKey;
    }

    /**
     * Parse given JWT token into {@link org.springframework.security.core.Authentication} instance
     *
     * @param token JWT token to be parsed
     * @return authentication instance
     * @throws TokenException if token type is different than {@link TokenType#ACCESS_TOKEN} or token is
     *         invalid
     */
    @Override
    public JwtAuthentication parseAccessToken(String token) throws TokenException {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(signingKey)
                    .parseClaimsJws(token).getBody();

            if (!TokenType.ACCESS_TOKEN.getAbbreviation().equals(claims.get(TOKEN_TYPE_CLAIM_NAME))) {
                throw new TokenException("Wrong type of token. Expected type: " + TokenType.ACCESS_TOKEN);
            }

            @SuppressWarnings("unchecked")
            List<String> roles = claims.get(AUTHORITIES_CLAIM_NAME, List.class);
            Set<SimpleGrantedAuthority> authorities = roles.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toSet());

            return new JwtAuthentication(claims.getSubject(), authorities, claims);
        } catch (Exception ex) {
            throw catcher(ex);
        }
    }

    /**
     * Parse given JWT token and returns subject claim
     *
     * @param token JWT token to be parsed
     * @return subject claim
     * @throws TokenException if token type is different than {@link TokenType#REFRESH_TOKEN} or token
     *         is invalid
     */
    @Override
    public Object parseRefreshToken(String token) throws TokenException {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(signingKey)
                    .parseClaimsJws(token).getBody();

            if (!TokenType.REFRESH_TOKEN.getAbbreviation().equals(claims.get(TOKEN_TYPE_CLAIM_NAME))) {
                throw new TokenException("Wrong type of token. Expected type: " + TokenType.REFRESH_TOKEN);
            }

            return claims.getSubject();
        } catch (Exception ex) {
            throw catcher(ex);
        }
    }

    private TokenException catcher(Exception ex) {
        if (ex instanceof SignatureException) return new TokenException("Invalid JWT signature.", ex);
        else if (ex instanceof MalformedJwtException) return new TokenException("Invalid JWT token.", ex);
        else if (ex instanceof ExpiredJwtException) return new TokenException("Expired JWT token.", ex);
        else if (ex instanceof UnsupportedJwtException) return new TokenException("Unsupported JWT token.", ex);
        else if (ex instanceof IllegalArgumentException) return new TokenException("JWT claims string is empty.", ex);
        else return new TokenException(ex.getMessage());
    }
}
