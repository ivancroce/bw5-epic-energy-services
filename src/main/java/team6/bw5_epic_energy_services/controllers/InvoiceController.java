package team6.bw5_epic_energy_services.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import team6.bw5_epic_energy_services.entities.Invoice;
import team6.bw5_epic_energy_services.exceptions.ValidationException;
import team6.bw5_epic_energy_services.payloads.NewInvoiceDTO;
import team6.bw5_epic_energy_services.services.InvoiceService;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/invoces")
public class InvoiceController {
    @Autowired
    private InvoiceService invoicesService;

    //    @GetMapping("/customer/{customerId}") // GET http://localhost:3001/invoices/customer/{customerId}
//    public Page<Invoice> filterByCustomer(
//            @PathVariable UUID customerId,
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int size,
//            @RequestParam(defaultValue = "id") String sortBy,
//            @RequestParam(defaultValue = "asc") String direction
//    ) {
//        return this.invoicesService.filterByCustomer(customerId, page, size, sortBy, direction);
//    }
//
//    @GetMapping("/status/{statusId}")// GET http://localhost:3001/invoices/status/{statusId}
//    public Page<Invoice> filterByStatus(
//            @PathVariable UUID statusId,
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int size,
//            @RequestParam(defaultValue = "id") String sortBy,
//            @RequestParam(defaultValue = "asc") String direction
//    ) {
//        return invoicesService.filterByStatus(statusId, page, size, sortBy, direction);
//    }
//
//    @GetMapping("/{date}")// GET http://localhost:3001/invoices/{date}
//    public Page<Invoice> filterByDate(
//            @PathVariable LocalDate date,
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int size,
//            @RequestParam(defaultValue = "id") String sortBy,
//            @RequestParam(defaultValue = "asc") String direction
//    ) {
//        return invoicesService.filterByDate(date, page, size, sortBy, direction);
//    }
//
//    @GetMapping("/{ye}")// GET http://localhost:3001/invoices/{year}
//    public Page<Invoice> findByDateYear(
//            @PathVariable int year,
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int size,
//            @RequestParam(defaultValue = "id") String sortBy,
//            @RequestParam(defaultValue = "asc") String direction
//    ) {
//        return invoicesService.findByDateYear(year, page, size, sortBy, direction);
//    }
//
//    @GetMapping("/between/{min}/{max}") // GET http://localhost:3001/invoices/{min}/{max}
//    public Page<Invoice> findByAmountBetween(
//            @PathVariable Double min,
//            @PathVariable Double max,
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int size,
//            @RequestParam(defaultValue = "id") String sortBy,
//            @RequestParam(defaultValue = "asc") String direction
//    ) {
//        return invoicesService.findByAmountBetween(min, max, page, size, sortBy, direction);
//    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN')")
    public Invoice createInvoice(@RequestBody @Validated NewInvoiceDTO payload, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            List<String> errors = validationResult.getFieldErrors().stream()
                    .map(fieldError -> fieldError.getDefaultMessage())
                    .toList();
            throw new ValidationException(errors);
        }

        return invoicesService.saveInvoice(payload);
    }

    @GetMapping
    public Page<Invoice> getAllInvoices(@RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "10") int size,
                                        @RequestParam(defaultValue = "id") String sortBy) {
        return invoicesService.findAllInvoices(page, size, sortBy);
    }

    @GetMapping("/{invoiceId}")
    public Invoice getInvoiceById(@PathVariable UUID invoiceId) {
        return invoicesService.findInvoiceById(invoiceId);
    }

    @PutMapping("/{invoiceId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Invoice updateInvoice(@PathVariable UUID invoiceId,
                                 @RequestBody @Validated NewInvoiceDTO payload,
                                 BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            List<String> errors = validationResult.getFieldErrors().stream()
                    .map(fieldError -> fieldError.getDefaultMessage())
                    .toList();
            throw new ValidationException(errors);
        }

        return invoicesService.findInvoiceByIdAndUpdate(invoiceId, payload);
    }

    @DeleteMapping("/{invoiceId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteInvoice(@PathVariable UUID invoiceId) {
        invoicesService.deleteInvoice(invoiceId);
    }

    @GetMapping("/search")
    public Page<Invoice> searchInvoices(
            @RequestParam(required = false) UUID customerId,
            @RequestParam(required = false) UUID statusId,
            @RequestParam(required = false) LocalDate date,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Double min,
            @RequestParam(required = false) Double max,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        return invoicesService.searchInvoices(customerId, statusId, date, year, min, max, page, size, sortBy, direction);
    }
}
