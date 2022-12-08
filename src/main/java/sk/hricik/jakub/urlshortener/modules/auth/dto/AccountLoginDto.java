package sk.hricik.jakub.urlshortener.modules.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountLoginDto {
    private String accountId;
    private String password;
}
