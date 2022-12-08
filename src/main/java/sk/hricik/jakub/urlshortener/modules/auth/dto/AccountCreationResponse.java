package sk.hricik.jakub.urlshortener.modules.auth.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@Builder
public class AccountCreationResponse {

    @NotEmpty
    private boolean success;

    @NotEmpty
    private String description;

    @Nullable
    private String generatedPassword;

    @Nullable
    private TokenResponse tokens;
}
