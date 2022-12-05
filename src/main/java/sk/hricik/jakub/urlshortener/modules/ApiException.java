package sk.hricik.jakub.urlshortener.modules;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiException extends RuntimeException {

    private final String errorMsg;
    private final FaultType faultType;
    private final int specificCode;

    public ApiException(FaultType faultType, String errorMsg) {
        super(errorMsg);
        this.faultType = faultType;
        this.errorMsg = errorMsg;
        this.specificCode = 0;
    }

    public ApiException(FaultType faultType, String errorMsg, SpecificCodeType specificCode) {
        super(errorMsg);
        this.faultType = faultType;
        this.errorMsg = errorMsg;
        this.specificCode = specificCode.getSpecificCode();
    }

    @AllArgsConstructor
    @Getter
    public enum SpecificCodeType {

        // @formatter:off
        LOGIN_USERNAME_CHANGED(400),
        WRONG_USERNAME(401),
        WRONG_PASSWORD(402),
        WRONG_PIN(403),
        WRONG_USER_STATE(404),
        WRONG_DEVICE_ID(340),
        IBAN_WRONG_VALIDFROM(1002),
        PASSWORD_CODE_EXPIRED(1003);
        // @formatter:on

        private final int specificCode;
    }

    @AllArgsConstructor
    @Getter
    public enum FaultType {
        // @formatter:off
        GENERAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR),
        DB_ERROR(HttpStatus.INTERNAL_SERVER_ERROR),
        ACTION_NOT_ALLOWED(HttpStatus.BAD_REQUEST),
        ACCESS_DENIED(HttpStatus.FORBIDDEN),
        WRONG_CREDENTIALS(HttpStatus.BAD_REQUEST),
        WRONG_REFRESH_TOKEN(HttpStatus.BAD_REQUEST),
        INVALID_PARAMS(HttpStatus.BAD_REQUEST),
        INVALID_PASSWORD(HttpStatus.BAD_REQUEST),
        OBJECT_NOT_FOUND(HttpStatus.BAD_REQUEST),
        OBJECT_ALREADY_EXISTS(HttpStatus.BAD_REQUEST),
        EMAIL_ALREADY_EXISTS(HttpStatus.BAD_REQUEST),
        TOKEN_ALREADY_EXISTS(HttpStatus.BAD_REQUEST),
        EXAM_DATE_ALREADY_EXISTS(HttpStatus.BAD_REQUEST),
        FILE_INVALID_SIZE(HttpStatus.BAD_REQUEST),
        FILE_INVALID_NAME(HttpStatus.BAD_REQUEST),
        FILE_INVALID_EXTENSION(HttpStatus.BAD_REQUEST),
        FILE_INVALID_MEDIA_TYPE(HttpStatus.BAD_REQUEST),
        EXPORT_GENERATION_ERROR(HttpStatus.INTERNAL_SERVER_ERROR),
        RESET_PASSWORD_EMAIL_ALREADY_SENT(HttpStatus.BAD_REQUEST);
        // @formatter:on

        private final HttpStatus httpStatus;
    }

    public static ApiException createUserNotLoggedIn() {
        return new ApiException(FaultType.ACTION_NOT_ALLOWED, "User is not logged in");
    }

    public static ApiException createNotEnoughPermissions() {
        return new ApiException(FaultType.ACTION_NOT_ALLOWED, "Logged user does not have permission for this action");
    }

    public static ApiException createNotEnoughPermissions(String m) {
        return new ApiException(FaultType.ACTION_NOT_ALLOWED, m);
    }

    public static ApiException createNotPatient() {
        return new ApiException(FaultType.ACTION_NOT_ALLOWED, "Logged user is not a patient");
    }

    public static ApiException createNotAllowed(String m) {
        return new ApiException(FaultType.ACTION_NOT_ALLOWED, m);
    }

    public static ApiException createNotAllowed(String m, SpecificCodeType code) {
        return new ApiException(FaultType.ACTION_NOT_ALLOWED, m, code);
    }

    public static ApiException createObjectNotFound(String errorMsg) {
        return new ApiException(FaultType.OBJECT_NOT_FOUND, errorMsg);
    }

    public static ApiException createObjectNotFound() {
        return createObjectNotFound("Object not found in DB");
    }

    public static ApiException createInvalidParams() {
        return new ApiException(FaultType.INVALID_PARAMS, "Invalid parameters");
    }

    public static ApiException createInvalidParams(String errorMsg) {
        return new ApiException(FaultType.INVALID_PARAMS, errorMsg);
    }

    public static ApiException createInvalidParams(String errorMsg, SpecificCodeType code) {
        return new ApiException(FaultType.INVALID_PARAMS, errorMsg, code);
    }

    public static ApiException createInvalidParamsMaxSize(String fieldName, int size) {
        return new ApiException(FaultType.INVALID_PARAMS,
            fieldName + " size must be max " + size + " characters");
    }

    public static ApiException createEmailExists(String mail) {
        return new ApiException(FaultType.EMAIL_ALREADY_EXISTS, "User with email " + mail + " already exists");
    }

    public static ApiException createGeneralError(String errorMsg) {
        return new ApiException(FaultType.GENERAL_ERROR, errorMsg);
    }
}
