package sk.hricik.jakub.urlshortener.modules.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sk.hricik.jakub.urlshortener.modules.common.model.AppUser;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByUsername(String username);

}
