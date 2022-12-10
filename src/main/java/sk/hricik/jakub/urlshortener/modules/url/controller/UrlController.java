package sk.hricik.jakub.urlshortener.modules.url.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import sk.hricik.jakub.urlshortener.modules.url.dto.RegisterUrlDto;
import sk.hricik.jakub.urlshortener.modules.url.dto.ShortUrlResponse;
import sk.hricik.jakub.urlshortener.modules.url.service.ShortUrlService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class UrlController {
    private final ShortUrlService urlService;

    @PostMapping(value = "/register", headers = HttpHeaders.AUTHORIZATION)
    public ShortUrlResponse registerUrl(@Valid @RequestBody RegisterUrlDto registerUrlDto) {
        return urlService.registerUrl(registerUrlDto);
    }

    @GetMapping("/sh_{shortenUrlCode}")
    public void redirectOnOriginalUrl(@PathVariable String shortenUrlCode) {
        urlService.redirectToUrl(shortenUrlCode);
    }

}
