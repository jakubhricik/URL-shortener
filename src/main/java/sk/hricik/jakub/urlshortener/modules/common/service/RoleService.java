package sk.hricik.jakub.urlshortener.modules.common.service;

import sk.hricik.jakub.urlshortener.modules.common.dto.RoleDto;
import sk.hricik.jakub.urlshortener.modules.common.model.Role;

import java.util.List;

public interface RoleService {
    void saveRole(Role role);

    List<RoleDto> getRoles();

    void addRoleToUser(String roleName, String username);
}
