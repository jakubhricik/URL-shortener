package sk.hricik.jakub.urlshortener.modules.common.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import sk.hricik.jakub.urlshortener.modules.common.dto.AppUserDto;
import sk.hricik.jakub.urlshortener.modules.common.service.AppUserService;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class AppUserController {

    private final AppUserService appUserService;

    @GetMapping("/all")
    public List<AppUserDto> getUsers() {
        return appUserService.getUsers();
    }

}
