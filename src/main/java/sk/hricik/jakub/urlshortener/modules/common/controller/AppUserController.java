package sk.hricik.jakub.urlshortener.modules.common.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import sk.hricik.jakub.urlshortener.modules.common.dto.AppUserDto;
import sk.hricik.jakub.urlshortener.modules.common.service.AppUserService;
import sk.hricik.jakub.urlshortener.modules.url.service.ShortUrlService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(headers = HttpHeaders.AUTHORIZATION)
@RequiredArgsConstructor
public class AppUserController {

    private final AppUserService appUserService;
    private final ShortUrlService urlService;

    @GetMapping(value = "/users")
    public List<AppUserDto> getUsers() {
        return appUserService.getUsers();
    }

    @GetMapping(value = "/statistics/{username}")
    public Map<String, Integer> getStatisticsByUsername(@PathVariable String username) {
        return urlService.getStatisticsByUsername(username);
    }

}
