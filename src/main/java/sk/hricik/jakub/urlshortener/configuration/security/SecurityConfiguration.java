package sk.hricik.jakub.urlshortener.configuration.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import sk.hricik.jakub.urlshortener.modules.auth.security.RestAuthenticationEntryPoint;
import sk.hricik.jakub.urlshortener.properties.SecurityProperties;
import sk.hricik.jakub.urlshortener.configuration.security.filter.TokenAuthenticationFilter;
import sk.hricik.jakub.urlshortener.modules.auth.security.jwt.JwtTokenFactory;
import sk.hricik.jakub.urlshortener.modules.auth.security.jwt.JwtTokenParser;
import sk.hricik.jakub.urlshortener.modules.auth.security.jwt.TokenFactory;

import javax.servlet.Filter;

import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {

    private final SecurityProperties securityProperties;
    private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    public SecurityConfiguration(SecurityProperties securityProperties, RestAuthenticationEntryPoint restAuthenticationEntryPoint) {
        this.securityProperties = securityProperties;
        this.restAuthenticationEntryPoint = restAuthenticationEntryPoint;
    }

    private static final String[] AUTH_WHITELIST = {
            "/account/**",
            "/sh_**", /*shorten urls*/
            "/help",
            "/"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .formLogin().disable()
                .httpBasic().disable()
                .exceptionHandling()
                .authenticationEntryPoint(restAuthenticationEntryPoint).and()
                .addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(auth ->
                    auth
                            .antMatchers(AUTH_WHITELIST).permitAll()
                            .anyRequest().authenticated()
                )
                .httpBasic(withDefaults());
        return http.build();
    }

    private Filter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter(tokenParser());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public TokenFactory tokenFactory() {
        return new JwtTokenFactory(securityProperties);
    }

    @Bean
    public JwtTokenParser tokenParser() {
        return new JwtTokenParser(securityProperties.getSigningKey());
    }

}