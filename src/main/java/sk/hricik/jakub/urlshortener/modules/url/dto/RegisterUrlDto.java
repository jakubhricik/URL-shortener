package sk.hricik.jakub.urlshortener.modules.url.dto;


import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;


@Getter
@Setter
public class RegisterUrlDto {

    private String url;

    @Nullable
    private Short redirectType;
}
