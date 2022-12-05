package sk.hricik.jakub.urlshortener.util;

import org.mapstruct.Condition;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import sk.hricik.jakub.urlshortener.modules.common.dto.AppUserDto;
import sk.hricik.jakub.urlshortener.modules.common.dto.RoleDto;
import sk.hricik.jakub.urlshortener.modules.common.model.AppUser;
import sk.hricik.jakub.urlshortener.modules.common.model.Role;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        typeConversionPolicy = ReportingPolicy.ERROR)
public interface ModelMapper {
    @Condition
    default boolean isNotEmpty(Object object) {
        return object != null;
    }

    AppUser mapAppUserDtoToAppUser(AppUserDto appUserDto);
    AppUserDto mapAppUserToAppUserDto(AppUser appUser);

    Role mapRoleDtoToRole(RoleDto roleDto);
    RoleDto mapRoleToRoleDto(Role role);
}
