package team6.bw5_epic_energy_services.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team6.bw5_epic_energy_services.entities.Address;
import team6.bw5_epic_energy_services.entities.Municipality;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AddressRepository extends JpaRepository<Address, UUID> {
    Optional<Address> findByStreetAndBuildingNumberAndPostalCodeAndMunicipality(
            String street,
            String buildingNumber,
            String postalCode,
            Municipality municipality
    );
}
