package sk.hricik.jakub.urlshortener.modules.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sk.hricik.jakub.urlshortener.modules.common.model.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    AppUser findByUsername(String username);

}
