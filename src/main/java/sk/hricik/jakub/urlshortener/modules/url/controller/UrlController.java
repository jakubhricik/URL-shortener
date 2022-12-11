package sk.hricik.jakub.urlshortener.modules.url.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import sk.hricik.jakub.urlshortener.modules.url.dto.RegisterUrlDto;
import sk.hricik.jakub.urlshortener.modules.url.dto.ShortUrlResponse;
import sk.hricik.jakub.urlshortener.modules.url.service.ShortUrlService;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class UrlController {
    private final ShortUrlService urlService;

    @ApiOperation(value = "Register custom url and get shorten one")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Url is not valid"),
            @ApiResponse(code = 400, message = "Redirect Type must be 301 or 302"),
            @ApiResponse(code = 400, message = "Given URL is already shorten: {shorten url}"),
    })
    @PostMapping(value = "/register", headers = HttpHeaders.AUTHORIZATION)
    public ShortUrlResponse registerUrl(
            @Valid
            @RequestBody
            @ApiParam(name = "registerUrlDto", value = "URL and redirect type", required = true)
            RegisterUrlDto registerUrlDto
    ) {
        return urlService.registerUrl(registerUrlDto);
    }

    @ApiIgnore
    @GetMapping("/sh_{shortenUrlCode}")
    public void redirectOnOriginalUrl(@PathVariable String shortenUrlCode) {
        urlService.redirectToUrl(shortenUrlCode);
    }

}
