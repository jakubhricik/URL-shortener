package sk.hricik.jakub.urlshortener.modules.common.service;

import sk.hricik.jakub.urlshortener.modules.common.dto.RoleDto;
import sk.hricik.jakub.urlshortener.modules.common.model.AppUser;
import sk.hricik.jakub.urlshortener.modules.common.model.Role;

import java.util.List;

public interface RoleService {
    void saveRole(Role role);

    Role getRole(String name);

    List<RoleDto> getRoles();

    AppUser addRoleToUser(String roleName, String username);
}
