package team6.bw5_epic_energy_services.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import team6.bw5_epic_energy_services.entities.Invoice;
import team6.bw5_epic_energy_services.services.InvoiceService;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("/invoces")
public class InvoiceController {
    @Autowired
    private InvoiceService invoicesService;

    @GetMapping("/customer/{customerId}") // GET http://localhost:3001/invoices/customer/{customerId}
    public Page<Invoice> filterByCustomer(
            @PathVariable UUID customerId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        return this.invoicesService.filterByCustomer(customerId, page, size, sortBy, direction);
    }

    @GetMapping("/status/{statusId}")// GET http://localhost:3001/invoices/status/{statusId}
    public Page<Invoice> filterByStatus(
            @PathVariable UUID statusId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        return invoicesService.filterByStatus(statusId, page, size, sortBy, direction);
    }

    @GetMapping("/{date}")// GET http://localhost:3001/invoices/{date}
    public Page<Invoice> filterByDate(
            @PathVariable LocalDate date,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        return invoicesService.filterByDate(date, page, size, sortBy, direction);
    }

    @GetMapping("/{ye}")// GET http://localhost:3001/invoices/{year}
    public Page<Invoice> findByDateYear(
            @PathVariable int year,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        return invoicesService.findByDateYear(year, page, size, sortBy, direction);
    }

    @GetMapping("/between/{min}/{max}") // GET http://localhost:3001/invoices/{min}/{max}
    public Page<Invoice> findByAmountBetween(
            @PathVariable Double min,
            @PathVariable Double max,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        return invoicesService.findByAmountBetween(min, max, page, size, sortBy, direction);
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
