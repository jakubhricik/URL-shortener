package sk.hricik.jakub.urlshortener.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Component
@ConfigurationProperties(prefix = "url-shortener.security.jwt")
@Setter
@Getter
@Validated
public class SecurityProperties {

    /** Name of an issuer of JWT token */
    @NotNull
    private String issuer;

    /** Key to be used for signing JWT tokens */
    @NotNull
    private String signingKey;

    /** Validity of access token in seconds */
    @NotNull
    private int accessTokenValidity;

    /** Validity of refresh token in seconds */
    @NotNull
    private int refreshTokenValidity;
}

