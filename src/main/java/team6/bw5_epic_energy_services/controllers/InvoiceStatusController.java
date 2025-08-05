package team6.bw5_epic_energy_services.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import team6.bw5_epic_energy_services.entities.InvoiceStatus;
import team6.bw5_epic_energy_services.exceptions.ValidationException;
import team6.bw5_epic_energy_services.payloads.NewInvoiceStatusDTO;
import team6.bw5_epic_energy_services.services.InvoiceStatusService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/invoice-statuses")
public class InvoiceStatusController {
    @Autowired
    private InvoiceStatusService invoiceStatusService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public InvoiceStatus createInvoiceStatus(@RequestBody @Validated NewInvoiceStatusDTO payload, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            List<String> errors = validationResult.getFieldErrors().stream()
                    .map(fieldError -> fieldError.getDefaultMessage())
                    .toList();
            throw new ValidationException(errors);
        }

        return invoiceStatusService.saveInvoiceStatus(payload);
    }

    @GetMapping
    public List<InvoiceStatus> getAllInvoiceStatuses() {
        return invoiceStatusService.findAllInvoicesStatus();
    }

    @GetMapping("/{invoiceStatusId}")
    public InvoiceStatus getInvoiceStatusById(@PathVariable UUID invoiceStatusId) {
        return invoiceStatusService.findInvoiceStatusById(invoiceStatusId);
    }
}
