package team6.bw5_epic_energy_services.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import team6.bw5_epic_energy_services.entities.Customer;
import team6.bw5_epic_energy_services.entities.Invoice;
import team6.bw5_epic_energy_services.entities.InvoiceStatus;
import team6.bw5_epic_energy_services.exceptions.NotFoundException;
import team6.bw5_epic_energy_services.payloads.NewInvoiceDTO;
import team6.bw5_epic_energy_services.repositories.InvoicesRepository;

import java.time.LocalDate;
import java.util.UUID;

@Service
@Slf4j
public class InvoiceService {
    @Autowired
    private InvoicesRepository invoicesRepository;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private InvoiceStatusService invoiceStatusService;

    public Invoice saveInvoice(NewInvoiceDTO payload) {
        Customer customer = customerService.findCustomerById(payload.customerId());
        InvoiceStatus invoiceStatus = invoiceStatusService.findInvoiceStatusById(payload.invoiceStatusId());

        Invoice newInvoice = new Invoice(payload.date(), payload.amount(), payload.number(), invoiceStatus, customer);

        Invoice savedInvoice = invoicesRepository.save(newInvoice);
        log.info("Invoice with code " + savedInvoice.getId() + " has been created");

        return savedInvoice;
    }

    public Page<Invoice> findAllInvoices(int pageNumb, int pageSize, String sortBy) {
        if (pageSize > 50) pageSize = 50;
        Pageable pageable = PageRequest.of(pageNumb, pageSize, Sort.by(sortBy).descending());
        return invoicesRepository.findAll(pageable);
    }

    public Invoice findInvoiceById(UUID invoiceId) {
        return invoicesRepository.findById(invoiceId)
                .orElseThrow(() -> new NotFoundException("Invoice with code " + invoiceId + " not found"));
    }

    public Invoice findInvoiceByIdAndUpdate(UUID invoiceId, NewInvoiceDTO payload) {
        Invoice foundInvoice = findInvoiceById(invoiceId);

        Customer customer = customerService.findCustomerById(payload.customerId());
        InvoiceStatus status = invoiceStatusService.findInvoiceStatusById(payload.invoiceStatusId());

        foundInvoice.setDate(payload.date());
        foundInvoice.setAmount(payload.amount());
        foundInvoice.setNumber(payload.number());
        foundInvoice.setInvoiceStatusId(status);
        foundInvoice.setCustomer(customer);

        Invoice updatedInvoice = invoicesRepository.save(foundInvoice);
        log.info("Invoice with code " + updatedInvoice.getId() + " has been updated");

        return updatedInvoice;
    }

    public void deleteInvoice(UUID invoiceId) {
        Invoice invoice = findInvoiceById(invoiceId);
        invoicesRepository.delete(invoice);
        log.info("Invoice with code " + invoice.getId() + " has been deleted");
    }
//
//    public Page<Invoice> filterByCustomer(UUID customerId, int page, int size, String sortBy, String direction) {
//        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
//        Pageable pageable = PageRequest.of(page, size, sort);
//        return invoicesRepository.findByCustomer_Id(customerId, pageable);
//    }
//
//    public Page<Invoice> filterByStatus(UUID statusId, int page, int size, String sortBy, String direction) {
//        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
//        Pageable pageable = PageRequest.of(page, size, sort);
//        return invoicesRepository.findByInvoiceStatusId_Id(statusId, pageable);
//    }
//
//
//    public Page<Invoice> filterByDate(LocalDate date, int page, int size, String sortBy, String direction) {
//        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
//        Pageable pageable = PageRequest.of(page, size, sort);
//        return invoicesRepository.findByDate(date, pageable);
//    }
//
//    public Page<Invoice> filterByDateBefore(LocalDate date, int page, int size, String sortBy, String direction) {
//        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
//        Pageable pageable = PageRequest.of(page, size, sort);
//        return invoicesRepository.findByDateBefore(date, pageable);
//    }
//
//    public Page<Invoice> filterByDateAfter(LocalDate date, int page, int size, String sortBy, String direction) {
//        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
//        Pageable pageable = PageRequest.of(page, size, sort);
//        return invoicesRepository.findByDateAfter(date, pageable);
//    }
//
//
//    public Page<Invoice> filterByAmount(Double amount, int page, int size, String sortBy, String direction) {
//        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
//        Pageable pageable = PageRequest.of(page, size, sort);
//        return invoicesRepository.findByAmount(amount, pageable);
//    }
//
//    public Page<Invoice> filterByAmountGreaterThan(Double amount, int page, int size, String sortBy, String direction) {
//        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
//        Pageable pageable = PageRequest.of(page, size, sort);
//        return invoicesRepository.findByAmountGreaterThan(amount, pageable);
//    }
//
//    public Page<Invoice> filterByAmountLessThan(Double amount, int page, int size, String sortBy, String direction) {
//        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
//        Pageable pageable = PageRequest.of(page, size, sort);
//        return invoicesRepository.findByAmountLessThan(amount, pageable);
//    }
//
//    public Page<Invoice> findByDateYear(int year, int page, int size, String sortBy, String direction) {
//        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
//        Pageable pageable = PageRequest.of(page, size, sort);
//        return invoicesRepository.findByDateYear(year, pageable);
//    }
//
//    public Page<Invoice> findByAmountBetween(Double min, Double max, int page, int size, String sortBy, String direction) {
//        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
//        Pageable pageable = PageRequest.of(page, size, sort);
//        return invoicesRepository.findByAmountBetween(min, max, pageable);
//    }

    public Page<Invoice> searchInvoices(UUID customerId, UUID statusId, LocalDate date, Integer year, Double min, Double max, int page, int size, String sortBy, String direction) {
        //Specification, Interfaccia si Spring Data Jpa che rappresenta una condizione o un filtro da applicare a una query
        //ci permette quindi di costruire query dinamiche/componibili
        // Specification per cliente, se customerId è null verrà ignorata la specification
        // altrimenti costruisce l'uguaglianza tra root.get("customer").get("id) e customerId
        Specification<Invoice> customerSpec = (root, query, builder) ->
                customerId == null ? null : builder.equal(root.get("customer").get("id"), customerId);//WHERE customer.id = :customerId

        // Specification per stato
        Specification<Invoice> statusSpec = (root, query, builder) ->
                statusId == null ? null : builder.equal(root.get("invoiceStatusId").get("id"), statusId);

        // Specification per data esatta
        Specification<Invoice> dateSpec = (root, query, builder) ->
                date == null ? null : builder.equal(root.get("date"), date);

        // Specification per anno (estratto dalla data)
        Specification<Invoice> yearSpec = (root, query, builder) -> {
            if (year == null) {
                return null;
            }
            // Usa la funzione SQL YEAR() per estrarre l'anno dalla data
            return builder.equal(builder.function("YEAR", Integer.class, root.get("date")), year);
        };

        // Specification per range di importi
        Specification<Invoice> amountSpec = (root, query, builder) -> {
            if (min == null && max == null) {
                return null;
            } else if (min != null && max != null) {
                return builder.between(root.get("amount"), min, max);
            } else if (min != null) {
                return builder.greaterThanOrEqualTo(root.get("amount"), min);
            } else { // maxAmount != null
                return builder.lessThanOrEqualTo(root.get("amount"), max);
            }
        };

        // Serve a combinare tutte le Specification con AND, i null saranno ignorati
        Specification<Invoice> specification = Specification.<Invoice>unrestricted()
                .and(customerSpec)
                .and(statusSpec)
                .and(dateSpec)
                .and(yearSpec)
                .and(amountSpec);

        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        // Esegui la query con Specification e paginazione
        return invoicesRepository.findAll(specification, pageable);
    }
}
