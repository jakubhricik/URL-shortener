package sk.hricik.jakub.urlshortener.modules.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sk.hricik.jakub.urlshortener.modules.common.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
