package team6.bw5_epic_energy_services.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team6.bw5_epic_energy_services.entities.Invoice;

import java.time.LocalDate;
import java.util.UUID;

@Repository
public interface InvoicesRepository extends JpaRepository<Invoice, UUID> {

    Page<Invoice> findByCustomer_Id(UUID customerId, Pageable pageable);

    Page<Invoice> findByInvoiceStatusId_Id(UUID invoiceStatusId, Pageable pageable);

    Page<Invoice> findByDate(LocalDate date, Pageable pageable);

    Page<Invoice> findByDateBefore(LocalDate date, Pageable pageable);

    Page<Invoice> findByDateAfter(LocalDate date, Pageable pageable);

    Page<Invoice> findByAmount(Double amount, Pageable pageable);

    Page<Invoice> findByAmountGreaterThan(Double amount, Pageable pageable);

    Page<Invoice> findByAmountLessThan(Double amount, Pageable pageable);
}
