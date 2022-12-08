package sk.hricik.jakub.urlshortener.configuration.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import sk.hricik.jakub.urlshortener.modules.auth.security.RestAuthenticationEntryPoint;
import sk.hricik.jakub.urlshortener.properties.SecurityProperties;
import sk.hricik.jakub.urlshortener.configuration.security.filter.TokenAuthenticationFilter;
import sk.hricik.jakub.urlshortener.modules.auth.security.jwt.JwtTokenFactory;
import sk.hricik.jakub.urlshortener.modules.auth.security.jwt.JwtTokenParser;
import sk.hricik.jakub.urlshortener.modules.auth.security.jwt.TokenFactory;

import javax.servlet.Filter;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final SecurityProperties securityProperties;
    private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    public SecurityConfiguration(SecurityProperties securityProperties, RestAuthenticationEntryPoint restAuthenticationEntryPoint) {
        this.securityProperties = securityProperties;
        this.restAuthenticationEntryPoint = restAuthenticationEntryPoint;
    }

    private static final String[] AUTH_WHITELIST = {
            "/account/**"
    };

    @Override
    @SuppressWarnings("java:S4502")
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .formLogin().disable()
                .httpBasic().disable()
                .exceptionHandling()
                .authenticationEntryPoint(restAuthenticationEntryPoint).and()
                .addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers(AUTH_WHITELIST).permitAll()
                .anyRequest().authenticated();
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