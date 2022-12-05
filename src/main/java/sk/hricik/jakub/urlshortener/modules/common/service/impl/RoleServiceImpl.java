package sk.hricik.jakub.urlshortener.modules.common.service.impl;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sk.hricik.jakub.urlshortener.modules.common.dto.RoleDto;
import sk.hricik.jakub.urlshortener.modules.common.model.AppUser;
import sk.hricik.jakub.urlshortener.modules.common.model.Role;
import sk.hricik.jakub.urlshortener.modules.common.repository.AppUserRepository;
import sk.hricik.jakub.urlshortener.modules.common.repository.RoleRepository;
import sk.hricik.jakub.urlshortener.modules.common.service.RoleService;
import sk.hricik.jakub.urlshortener.util.ModelMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final AppUserRepository appUserRepository;

    private final ModelMapper modelMapper;

    @Override
    public void saveRole(Role role) {
        modelMapper.mapRoleToRoleDto(roleRepository.saveAndFlush(role));
    }

    @Override
    public List<RoleDto> getRoles() {
        return roleRepository.findAll()
                .stream()
                .map(modelMapper::mapRoleToRoleDto)
                .toList();
    }

    @Override
    public void addRoleToUser(String roleName, String username) {
        Role role = roleRepository.findByName(roleName);
        AppUser user = appUserRepository.findByUsername(username);
        user.getRoles().add(role);
    }
}
