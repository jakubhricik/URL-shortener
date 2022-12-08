package sk.hricik.jakub.urlshortener.util;


import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import sk.hricik.jakub.urlshortener.modules.common.dto.AppUserDto;
import sk.hricik.jakub.urlshortener.modules.common.dto.RoleDto;
import sk.hricik.jakub.urlshortener.modules.common.model.AppUser;
import sk.hricik.jakub.urlshortener.modules.common.model.Role;

@Mapper(implementationName = "ModelMapperImplementation", componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        typeConversionPolicy = ReportingPolicy.ERROR)
public interface ModelMapper {

    AppUser mapAppUserDtoToAppUser(AppUserDto appUserDto);
    AppUserDto mapAppUserToAppUserDto(AppUser appUser);

    RoleDto mapRoleToRoleDto(Role role);
}
