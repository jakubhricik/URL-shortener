package sk.hricik.jakub.urlshortener.modules.common.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sk.hricik.jakub.urlshortener.modules.common.dto.AppUserDto;
import sk.hricik.jakub.urlshortener.modules.common.model.AppUser;
import sk.hricik.jakub.urlshortener.modules.common.repository.AppUserRepository;
import sk.hricik.jakub.urlshortener.modules.common.service.AppUserService;
import sk.hricik.jakub.urlshortener.util.ModelMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AppUserServiceImpl implements AppUserService {
    private final AppUserRepository appUserRepository;
    private final ModelMapper modelMapper;

    @Override
    public AppUserDto saveUser(AppUserDto appUser) {
        AppUser user = modelMapper.mapAppUserDtoToAppUser(appUser);
        return modelMapper.mapAppUserToAppUserDto(appUserRepository.saveAndFlush(user));
    }

    @Override
    public AppUserDto getUser(String username) {
        return modelMapper.mapAppUserToAppUserDto(appUserRepository.findByUsername(username));
    }

    @Override
    public List<AppUserDto> getUsers() {
        return appUserRepository.findAll()
                .stream()
                .map(modelMapper::mapAppUserToAppUserDto).toList();
    }
}
