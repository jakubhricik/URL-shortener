package sk.hricik.jakub.urlshortener.modules.url.service;

import sk.hricik.jakub.urlshortener.modules.url.dto.RegisterUrlDto;
import sk.hricik.jakub.urlshortener.modules.url.dto.ShortUrlResponse;

import java.util.Map;

public interface ShortUrlService {
    ShortUrlResponse registerUrl(RegisterUrlDto registerUrlDto);

    void redirectToUrl(String shortenUrlCode);

    Map<String, Integer> getStatisticsByUsername(String username);
}
