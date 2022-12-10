package sk.hricik.jakub.urlshortener.modules.common.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RoleToUserDto {
    private String roleName;
    private String accountId;
}
