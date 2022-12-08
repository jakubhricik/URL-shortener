package sk.hricik.jakub.urlshortener.modules.auth.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class TokenResponse {

    private String accessToken;
    private String refreshToken;
}
