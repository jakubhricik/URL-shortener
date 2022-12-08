package sk.hricik.jakub.urlshortener.modules.error.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import sk.hricik.jakub.urlshortener.modules.ApiException;

@Getter
@Setter
@AllArgsConstructor
public class ErrorResponseDto {
    private boolean success = false;
    private ApiException.FaultType faultType;
    private String description;
    private int code;
}
