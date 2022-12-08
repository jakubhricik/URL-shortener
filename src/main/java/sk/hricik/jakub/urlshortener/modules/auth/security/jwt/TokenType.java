package sk.hricik.jakub.urlshortener.modules.auth.security.jwt;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

@AllArgsConstructor
@Getter
public enum TokenType {
    ACCESS_TOKEN("AT"),
    REFRESH_TOKEN("RT");

    private static final Map<String, TokenType> map =
            Arrays.stream(values()).collect(toMap(o -> o.abbreviation, o -> o));

    private final String abbreviation;

    @JsonCreator
    public static TokenType deserialize(String value) {
        return map.get(value);
    }

    @JsonValue
    public String serialize() {
        return abbreviation;
    }
}
