package sk.hricik.jakub.urlshortener.modules.common.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sk.hricik.jakub.urlshortener.modules.ApiException;
import sk.hricik.jakub.urlshortener.modules.common.dto.AppUserDto;
import sk.hricik.jakub.urlshortener.modules.common.model.AppUser;
import sk.hricik.jakub.urlshortener.modules.common.model.Role;
import sk.hricik.jakub.urlshortener.modules.common.repository.AppUserRepository;
import sk.hricik.jakub.urlshortener.modules.common.repository.RoleRepository;
import sk.hricik.jakub.urlshortener.modules.common.service.AppUserService;
import sk.hricik.jakub.urlshortener.modules.common.service.RoleService;
import sk.hricik.jakub.urlshortener.util.ModelMapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AppUserServiceImpl implements AppUserService {
    private final AppUserRepository appUserRepository;
    private final RoleService roleService;
    private final ModelMapper modelMapper;

    private final PasswordEncoder passwordEncoder;

    @Override
    public AppUserDto saveUserDto(AppUserDto appUser) {
        AppUser user = modelMapper.mapAppUserDtoToAppUser(appUser);
        return saveUser(user);
    }

    @Override
    public AppUserDto createUser(AppUserDto appUserDto) {
        AppUserDto user = saveUserDto(appUserDto);
        return roleService.addRoleToUser("ROLE_USER", user.getUsername());
    }

    @Override
    public AppUserDto saveUser(AppUser newUser) {
        List<AppUserDto> users = getUsers();
        users.forEach(appUserDto -> {
            if (appUserDto.getUsername().equals(newUser.getUsername()))
                throw new ApiException(ApiException.FaultType.OBJECT_ALREADY_EXISTS, "There is already user with username: " + newUser.getUsername());
        });

        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        return modelMapper.mapAppUserToAppUserDto(appUserRepository.saveAndFlush(newUser));
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
