package sk.hricik.jakub.urlshortener.modules.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterCredentialsDto {
    private String username;
    private String password;
    private String passwordVerification;
}
