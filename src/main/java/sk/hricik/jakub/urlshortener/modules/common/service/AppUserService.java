package sk.hricik.jakub.urlshortener.modules.common.service;

import sk.hricik.jakub.urlshortener.modules.common.dto.AppUserDto;
import sk.hricik.jakub.urlshortener.modules.common.model.AppUser;

import java.util.List;

public interface AppUserService {

    AppUser getLoggedUser();

    void saveUserAndEncodePassword(AppUser appUser);

    AppUser getUser(String username);
    List<AppUserDto> getUsers();
}
