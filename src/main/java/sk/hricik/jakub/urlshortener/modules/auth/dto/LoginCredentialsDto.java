package sk.hricik.jakub.urlshortener.modules.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginCredentialsDto {
    private String username;
    private String password;
}
