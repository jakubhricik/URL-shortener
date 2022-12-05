package sk.hricik.jakub.urlshortener;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import sk.hricik.jakub.urlshortener.modules.common.dto.AppUserDto;
import sk.hricik.jakub.urlshortener.modules.common.model.Role;
import sk.hricik.jakub.urlshortener.modules.common.service.AppUserService;
import sk.hricik.jakub.urlshortener.modules.common.service.RoleService;


@SpringBootApplication
public class UrlShortenerApplication {

	public static final String USERNAME = "JakubHricik";

	public static void main(String[] args) {
		SpringApplication.run(UrlShortenerApplication.class, args);
	}

	@Bean
	CommandLineRunner run(AppUserService userService, RoleService roleService) {
		return args -> {
			roleService.saveRole(new Role(null, "ROLE_SUPER_ADMIN"));
			roleService.saveRole(new Role(null, "ROLE_ADMIN"));
			roleService.saveRole(new Role(null, "ROLE_USER"));

			userService.saveUser(new AppUserDto(null, USERNAME, "Heslo", null));

			roleService.addRoleToUser("ROLE_ADMIN", USERNAME);
			roleService.addRoleToUser("ROLE_USER", USERNAME);
			roleService.addRoleToUser("ROLE_SUPER_ADMIN", USERNAME);
		};
	}
}
