package sk.hricik.jakub.urlshortener.modules.auth.security.jwt;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

@Getter
@RequiredArgsConstructor
@ToString
public class JwtAuthentication implements Authentication {

    private final String subject;
    private final Set<? extends GrantedAuthority> authorities;
    private final transient Map<String, Object> details;
    private boolean authenticated = true;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Map<String, Object> getDetails() {
        return details;
    }

    @Override
    public String getPrincipal() {
        return subject;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) {
        this.authenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        return getPrincipal();
    }
}
