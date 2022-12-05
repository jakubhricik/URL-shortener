package sk.hricik.jakub.urlshortener.modules.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sk.hricik.jakub.urlshortener.modules.common.model.Role;

import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppUserDto {
    private Long id;
    private String username;
    private String password;
    private Collection<Role> roles;
}
