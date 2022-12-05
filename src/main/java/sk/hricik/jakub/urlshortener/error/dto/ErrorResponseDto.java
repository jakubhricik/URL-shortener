package sk.hricik.jakub.urlshortener.error.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import sk.hricik.jakub.urlshortener.modules.ApiException;

@Getter
@Setter
@AllArgsConstructor
public class ErrorResponseDto {
    private ApiException.FaultType faultType;
    private String message;
    private int code;
}
