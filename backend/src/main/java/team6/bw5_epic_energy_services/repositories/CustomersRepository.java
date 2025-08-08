package team6.bw5_epic_energy_services.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import team6.bw5_epic_energy_services.entities.Address;
import team6.bw5_epic_energy_services.entities.Customer;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomersRepository extends JpaRepository<Customer, UUID>, JpaSpecificationExecutor<Customer> {
    Optional<Customer> findByVatNumbIgnoreCase(String vatNumb);

    Optional<Customer> findByEmailIgnoreCase(String email);

    Optional<Customer> findByPecIgnoreCase(String pec);

    Page<Customer> findByCompanyNameContainingIgnoreCase(String name, Pageable pageable);

    Page<Customer> findByLastContactDate(LocalDate date, Pageable pageable);

    Page<Customer> findByInsertDate(LocalDate date, Pageable pageable);

    Page<Customer> findByAnnualRevenue(Double annualRevenue, Pageable pageable);

    Page<Customer> findByLegalAddress_Municipality_Province_NameIgnoreCase(String provinceName, Pageable pageable);

    boolean existsByLegalAddressOrOperationalAddress(Address legalAddress, Address operationalAddress);
}
