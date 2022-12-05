package sk.hricik.jakub.urlshortener.modules.common.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import sk.hricik.jakub.urlshortener.modules.common.dto.RoleDto;
import sk.hricik.jakub.urlshortener.modules.common.dto.RoleToUserDto;
import sk.hricik.jakub.urlshortener.modules.common.service.RoleService;

import java.util.List;

@RestController
@RequestMapping("/role")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @GetMapping("/all")
    public List<RoleDto> saveRole() {
        return roleService.getRoles();
    }

    @PostMapping("/add-to-user")
    public void addRoleToUser(@RequestBody RoleToUserDto roleToUserDto) {
        roleService.addRoleToUser(roleToUserDto.getRoleName(), roleToUserDto.getUsername());
    }
}
