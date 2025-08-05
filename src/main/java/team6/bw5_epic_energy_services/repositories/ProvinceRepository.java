package team6.bw5_epic_energy_services.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team6.bw5_epic_energy_services.entities.Province;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProvinceRepository extends JpaRepository<Province, UUID> {
    Optional<Province> findByName(String name);

    boolean existsByName(String name);
}
