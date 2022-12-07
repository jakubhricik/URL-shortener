package sk.hricik.jakub.urlshortener.modules.auth.security.jwt;

public class TokenException extends Exception {

    public TokenException(String message) {
        super(message);
    }

    public TokenException(String message, Throwable cause) {
        super(message, cause);
    }
}
