package team6.bw5_epic_energy_services.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team6.bw5_epic_energy_services.entities.Municipality;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface MunicipalityRepository extends JpaRepository<Municipality, UUID> {
    Optional<Municipality> findByName(String name);
}
