package sk.hricik.jakub.urlshortener.modules.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import sk.hricik.jakub.urlshortener.modules.common.model.Role;

import java.util.Collection;

@Data

@AllArgsConstructor
public class AppUserDto {
    private Long id;
    private String username;
    private String password;
    private Collection<Role> roles;
}
