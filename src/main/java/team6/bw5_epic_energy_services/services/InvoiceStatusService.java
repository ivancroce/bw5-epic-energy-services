package team6.bw5_epic_energy_services.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team6.bw5_epic_energy_services.entities.InvoiceStatus;
import team6.bw5_epic_energy_services.exceptions.BadRequestException;
import team6.bw5_epic_energy_services.exceptions.NotFoundException;
import team6.bw5_epic_energy_services.payloads.NewInvoiceStatusDTO;
import team6.bw5_epic_energy_services.repositories.InvoicesStatusRepository;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class InvoiceStatusService {
    @Autowired
    private InvoicesStatusRepository invoicesStatusRepository;

    public InvoiceStatus saveInvoiceStatus(NewInvoiceStatusDTO payload) {
        invoicesStatusRepository.findByStatusNameIgnoreCase(payload.statusName()).ifPresent(s -> {
            throw new BadRequestException("Invoice status " + payload.statusName() + " already exists in our system");
        });

        InvoiceStatus newStatus = new InvoiceStatus(payload.statusName());
        return invoicesStatusRepository.save(newStatus);
    }

    public List<InvoiceStatus> findAllInvoicesStatus() {
        return invoicesStatusRepository.findAll();
    }

    public InvoiceStatus findInvoiceStatusById(UUID invoiceStatusId) {
        return invoicesStatusRepository.findById(invoiceStatusId).orElseThrow(() -> new NotFoundException("Invoice status with ID " + invoiceStatusId + " not found"));
    }
}
