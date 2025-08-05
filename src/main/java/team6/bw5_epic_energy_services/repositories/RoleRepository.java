package team6.bw5_epic_energy_services.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import team6.bw5_epic_energy_services.entities.Role;

import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {
    Optional<Role> findByName(String name);
}
