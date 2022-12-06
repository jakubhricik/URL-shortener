package sk.hricik.jakub.urlshortener.modules.common.service;

import sk.hricik.jakub.urlshortener.modules.common.dto.AppUserDto;
import sk.hricik.jakub.urlshortener.modules.common.model.AppUser;

import java.util.List;

public interface AppUserService {
    AppUserDto saveUserDto(AppUserDto appUserDto);

    AppUserDto createUser(AppUserDto appUserDto);

    AppUserDto saveUser(AppUser appUser);
    AppUserDto getUserDto(String username);

    AppUser getUser(String username);
    List<AppUserDto> getUsers();
}
