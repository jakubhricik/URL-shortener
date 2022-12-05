package sk.hricik.jakub.urlshortener.modules.common.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sk.hricik.jakub.urlshortener.modules.common.dto.RoleDto;
import sk.hricik.jakub.urlshortener.modules.common.dto.RoleToUserDto;
import sk.hricik.jakub.urlshortener.modules.common.model.Role;
import sk.hricik.jakub.urlshortener.modules.common.service.RoleService;

@RestController
@RequestMapping("/role")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @PostMapping()
    public RoleDto saveRole(@RequestBody Role role) {
        return roleService.saveRole(role);
    }

    @PostMapping("/add-to-user")
    public void addRoleToUser(@RequestBody RoleToUserDto roleToUserDto) {
        roleService.addRoleToUser(roleToUserDto.getRoleName(), roleToUserDto.getUsername());
    }
}
