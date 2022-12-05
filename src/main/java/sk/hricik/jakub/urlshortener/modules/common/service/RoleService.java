package sk.hricik.jakub.urlshortener.modules.common.service;

import sk.hricik.jakub.urlshortener.modules.common.dto.RoleDto;
import sk.hricik.jakub.urlshortener.modules.common.model.Role;

public interface RoleService {
    RoleDto saveRole(Role role);
    void addRoleToUser(String roleName, String username);
}
