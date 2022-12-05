package sk.hricik.jakub.urlshortener.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import sk.hricik.jakub.urlshortener.error.dto.ErrorResponseDto;
import sk.hricik.jakub.urlshortener.modules.ApiException;


import static sk.hricik.jakub.urlshortener.modules.ApiException.FaultType.*;


@Slf4j
@ControllerAdvice
public class RestResponseExceptionHandler extends ResponseEntityExceptionHandler {

    private static final ApiException.FaultType UNEXPECTED_ERROR_TYPE = GENERAL_ERROR;

    private static final String UNEXPECTED_ERROR_TEXT =
            "Unexpected error occurred. Please contact administrator for more info";
    private static final String ACCESS_DENIED_TEXT =
            "Access denied";

    @ExceptionHandler(value = ApiException.class)
    protected ResponseEntity<Object> handleApiException(ApiException ex) {
        log.error("Handling ApiException with message '{}'", ex.getErrorMsg());
        return createResponse(ex.getFaultType(), ex.getErrorMsg(), ex.getSpecificCode());
    }

    @ExceptionHandler(value = MaxUploadSizeExceededException.class)
    protected ResponseEntity<Object> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException ex) {
        log.error("Handling MaxUploadSizeExceededException and saving to DB");
        return createResponse(FILE_INVALID_SIZE, ex.getMessage());
    }

    @ExceptionHandler(value = DataAccessException.class)
    protected ResponseEntity<Object> handleDbException(DataAccessException ex) {
        log.error("Handling DbException and saving to DB", ex);
        return createResponse(DB_ERROR, ex.getMostSpecificCause().getMessage());
    }

    @ExceptionHandler(value = Exception.class)
    private ResponseEntity<Object> handleUnexpectedException(Exception ex) {
        log.error("Handling unexpected exception", ex);
        return createResponse(UNEXPECTED_ERROR_TYPE, UNEXPECTED_ERROR_TEXT);
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    private ResponseEntity<Object> handleAccessDeniedException(Exception ex) {
        log.error("Handling access denied exception");
        return createResponse(ACCESS_DENIED, ACCESS_DENIED_TEXT);
    }


    private ResponseEntity<Object> createResponse(ApiException.FaultType faultType, String message, int code) {
        return ResponseEntity.status(faultType.getHttpStatus())
                .body(new ErrorResponseDto(faultType, message, code));
    }

    private ResponseEntity<Object> createResponse(ApiException.FaultType faultType, String message) {
        return ResponseEntity.status(faultType.getHttpStatus())
                .body(new ErrorResponseDto(faultType, message, 0));
    }
}
