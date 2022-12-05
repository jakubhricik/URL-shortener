package sk.hricik.jakub.urlshortener.modules.common.service;

import sk.hricik.jakub.urlshortener.modules.common.dto.AppUserDto;

import java.util.List;

public interface AppUserService {
    AppUserDto saveUser(AppUserDto appUser);
    AppUserDto getUser(String username);
    List<AppUserDto> getUsers();
}
