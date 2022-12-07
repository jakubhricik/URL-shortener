package sk.hricik.jakub.urlshortener.configuration.security.filter;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import sk.hricik.jakub.urlshortener.modules.auth.security.jwt.TokenException;
import sk.hricik.jakub.urlshortener.modules.auth.security.jwt.TokenParser;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final TokenParser tokenParser;

    public TokenAuthenticationFilter(TokenParser tokenParser) {
        this.tokenParser = tokenParser;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try {
            Optional<String> tokenOptional = extractBearerToken(request);
            if (tokenOptional.isPresent()) {
                Authentication authentication = tokenParser.parseAccessToken(tokenOptional.get());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            filterChain.doFilter(request, response);
        } catch (TokenException ex) {
            // this is very important, since it guarantees the user is not authenticated at all
            SecurityContextHolder.clearContext();
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage());
        }
    }

    private Optional<String> extractBearerToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(HttpHeaders.AUTHORIZATION))
                .filter(authorizationHeader -> authorizationHeader.startsWith("Bearer "))
                .map(authorizationHeader -> authorizationHeader.substring(7));
    }

}
