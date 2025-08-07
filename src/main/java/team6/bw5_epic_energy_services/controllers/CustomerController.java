package team6.bw5_epic_energy_services.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import team6.bw5_epic_energy_services.entities.Customer;
import team6.bw5_epic_energy_services.exceptions.ValidationException;
import team6.bw5_epic_energy_services.payloads.NewCustomerDTO;
import team6.bw5_epic_energy_services.services.CustomerService;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN')")
    public Customer createCustomer(@RequestBody @Validated NewCustomerDTO payload, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            List<String> errors = validationResult.getFieldErrors().stream()
                    .map(fieldError -> fieldError.getDefaultMessage())
                    .toList();
            throw new ValidationException(errors);
        }

        return customerService.saveCustomer(payload);
    }


    @GetMapping
    public Page<Customer> getAllCustomers(@RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "10") int size,
                                          @RequestParam(defaultValue = "id") String sortBy) {
        return customerService.findAllCustomers(page, size, sortBy);
    }

    @GetMapping("/{customerId}")
    public Customer getCustomerById(@PathVariable UUID customerId) {
        return customerService.findCustomerById(customerId);
    }

    @PutMapping("/{customerId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Customer updateCustomer(@PathVariable UUID customerId, @RequestBody @Validated NewCustomerDTO payload, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            List<String> errors = validationResult.getFieldErrors().stream()
                    .map(fieldError -> fieldError.getDefaultMessage())
                    .toList();
            throw new ValidationException(errors);
        }

        return customerService.findCustomerByIdAndUpdate(customerId, payload);
    }

    @DeleteMapping("/{customerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteCustomer(@PathVariable UUID customerId) {
        customerService.deleteCustomer(customerId);
    }

    @GetMapping("/search")
    public Page<Customer> searchCustomers(
            //specification ci permette di trattare i parametri come facoltativi e di fare query multifattoriali
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Double revenue,
            @RequestParam(required = false) Double min,
            @RequestParam(required = false) Double max,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate insertDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate lastContactDate,
            //meglio avere i valori di default per evitare di far fallire le query
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "companyName") String sortBy,
            @RequestParam(defaultValue = "ASC") String direction

    ) {
        return customerService.searchCustomers(name, revenue, min, max, insertDate, lastContactDate, page, size, sortBy, direction);
    }

//    @GetMapping("/search/name")
//    public Page<Customer> filterByPartialCompanyName(@RequestParam String name, @RequestParam int page, @RequestParam int size, @RequestParam String sortBy, @RequestParam String direction) {
//        return customerService.filterByPartialCompanyName(name, page, size, sortBy, direction);
//    }
//
//    @GetMapping("/search/revenue")
//    public Page<Customer> filterByAnnualRevenue(@RequestParam Double revenue, @RequestParam int page, @RequestParam int size, @RequestParam String sortBy, @RequestParam String direction) {
//        return customerService.filterByAnnualRevenue(revenue, page, size, sortBy, direction);
//    }
//
//    @GetMapping("/search/date-last-contact")
//    public Page<Customer> filterByLastContactDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date, @RequestParam int page, @RequestParam int size, @RequestParam String sortBy, @RequestParam String direction) {
//        return customerService.filterByLastContactDate(date, page, size, sortBy, direction);
//    }
//
//    @GetMapping("/search/date-insert")
//    public Page<Customer> filterByInsertDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date, @RequestParam int page, @RequestParam int size, @RequestParam String sortBy, @RequestParam String direction) {
//        return customerService.filterByInsertDate(date, page, size, sortBy, direction);
//    }
//
//    @GetMapping("/search/province")
//    public Page<Customer> filterByProvinceName(@RequestParam String province, @RequestParam int page, @RequestParam int size, @RequestParam String sortBy, @RequestParam String direction) {
//        return customerService.filterByProvinceName(province, page, size, sortBy, direction);
//    }
}