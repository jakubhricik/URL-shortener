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

	public static final String ACCOUNT_ID = "jakubhricik";
	public static final String PASSWORD = "password";

	public static void main(String[] args) {
		SpringApplication.run(UrlShortenerApplication.class, args);
	}

	@Bean
	CommandLineRunner run(AppUserService userService, RoleService roleService) {
		return args -> {
			roleService.saveRole(new Role(null, "ROLE_ADMIN"));
			roleService.saveRole(new Role(null, "ROLE_USER"));

			userService.saveUserDto(AppUserDto.builder().accountId(ACCOUNT_ID).password(PASSWORD).build());

			roleService.addRoleToUser("ROLE_ADMIN", ACCOUNT_ID);
			roleService.addRoleToUser("ROLE_USER", ACCOUNT_ID);
		};
	}
}
