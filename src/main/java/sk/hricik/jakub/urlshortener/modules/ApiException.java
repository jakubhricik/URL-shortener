package sk.hricik.jakub.urlshortener.modules;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiException extends RuntimeException {

    private final String description;
    private final FaultType faultType;

    private final int specificCode;

    public ApiException(FaultType faultType, String description) {
        super(description);
        this.faultType = faultType;
        this.description = description;
        this.specificCode = 0;
    }

    public ApiException(FaultType faultType, String description, SpecificCodeType specificCode) {
        super(description);
        this.faultType = faultType;
        this.description = description;
        this.specificCode = specificCode.getSpecificCode();
    }

    @AllArgsConstructor
    @Getter
    public enum SpecificCodeType {
        WRONG_USERNAME(401),
        WRONG_PASSWORD(402);
        private final int specificCode;
    }

    @AllArgsConstructor
    @Getter
    public enum FaultType {
        GENERAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR),
        DB_ERROR(HttpStatus.INTERNAL_SERVER_ERROR),
        ACTION_NOT_ALLOWED(HttpStatus.BAD_REQUEST),
        ACCESS_DENIED(HttpStatus.FORBIDDEN),
        WRONG_CREDENTIALS(HttpStatus.BAD_REQUEST),
        WRONG_REFRESH_TOKEN(HttpStatus.BAD_REQUEST),
        INVALID_PARAMS(HttpStatus.BAD_REQUEST),
        INVALID_PASSWORD(HttpStatus.BAD_REQUEST),
        OBJECT_NOT_FOUND(HttpStatus.BAD_REQUEST),
        OBJECT_ALREADY_EXISTS(HttpStatus.BAD_REQUEST);

        private final HttpStatus httpStatus;
    }



    public static ApiException createObjectNotFound(String errorMsg) {
        return new ApiException(FaultType.OBJECT_NOT_FOUND, errorMsg);
    }




    public static ApiException createInvalidParams(String errorMsg) {
        return new ApiException(FaultType.INVALID_PARAMS, errorMsg);
    }


    public static ApiException createGeneralError(String errorMsg) {
        return new ApiException(FaultType.GENERAL_ERROR, errorMsg);
    }
}
