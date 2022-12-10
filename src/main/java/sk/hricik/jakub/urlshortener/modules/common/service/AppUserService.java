package sk.hricik.jakub.urlshortener.modules.common.service;

import sk.hricik.jakub.urlshortener.modules.common.dto.AppUserDto;
import sk.hricik.jakub.urlshortener.modules.common.model.AppUser;

import java.util.List;

public interface AppUserService {

    AppUser getLoggedUser();

    AppUser saveUserDto(AppUserDto appUserDto);

    AppUser saveUserAndEncodePassword(AppUser appUser);
    AppUserDto getUserDto(String username);

    AppUser getUser(String username);
    List<AppUserDto> getUsers();
}
