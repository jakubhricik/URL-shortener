package sk.hricik.jakub.urlshortener.modules.common.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sk.hricik.jakub.urlshortener.modules.ApiException;
import sk.hricik.jakub.urlshortener.modules.common.dto.AppUserDto;
import sk.hricik.jakub.urlshortener.modules.common.model.AppUser;
import sk.hricik.jakub.urlshortener.modules.common.repository.AppUserRepository;
import sk.hricik.jakub.urlshortener.modules.common.service.AppUserService;
import sk.hricik.jakub.urlshortener.util.ModelMapper;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AppUserServiceImpl implements AppUserService {
    private final AppUserRepository appUserRepository;
    private final ModelMapper modelMapper;

    private final PasswordEncoder passwordEncoder;

    @Override
    public AppUser saveUserDto(AppUserDto appUser) {
        AppUser user = modelMapper.mapAppUserDtoToAppUser(appUser);
        return saveUserAndEncodePassword(user);
    }

    @Override
    public AppUser saveUserAndEncodePassword(AppUser newUser) {
        Optional<AppUser> user = appUserRepository.findByUsername(newUser.getUsername());
        if (user.isPresent())
            throw new ApiException(ApiException.FaultType.OBJECT_ALREADY_EXISTS, "Already exists user with username: " + newUser.getUsername());

        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        return appUserRepository.saveAndFlush(newUser);
    }

    @Override
    public AppUserDto getUserDto(String username) {
        return modelMapper.mapAppUserToAppUserDto(getUser(username));
    }

    @Override
    public AppUser getUser(String username) {
        Optional<AppUser> user = appUserRepository.findByUsername(username);
        if (user.isEmpty())
            throw new ApiException(ApiException.FaultType.OBJECT_NOT_FOUND, "There is not such user wit username: " + username);
        return user.get();
    }

    @Override
    public List<AppUserDto> getUsers() {
        return appUserRepository.findAll()
                .stream()
                .map(modelMapper::mapAppUserToAppUserDto).toList();
    }

}
