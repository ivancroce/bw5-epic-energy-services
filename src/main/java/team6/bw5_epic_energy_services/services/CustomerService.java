package team6.bw5_epic_energy_services.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import team6.bw5_epic_energy_services.entities.Address;
import team6.bw5_epic_energy_services.entities.Customer;
import team6.bw5_epic_energy_services.exceptions.BadRequestException;
import team6.bw5_epic_energy_services.exceptions.NotFoundException;
import team6.bw5_epic_energy_services.payloads.NewCustomerDTO;
import team6.bw5_epic_energy_services.repositories.CustomersRepository;

import java.time.LocalDate;
import java.util.UUID;

@Slf4j
@Service
public class CustomerService {
    @Autowired
    private CustomersRepository customersRepository;


    public Customer saveCustomer(NewCustomerDTO payload) {

        customersRepository.findByVatNumbIgnoreCase(payload.vatNumb()).ifPresent(c -> {
            throw new BadRequestException("A customer with VAT number " + payload.vatNumb() + " already exists in our system");
        });

        customersRepository.findByEmailIgnoreCase(payload.email()).ifPresent(c -> {
            throw new BadRequestException("A customer with email " + payload.email() + " already exists in our system");
        });

        customersRepository.findByPecIgnoreCase(payload.pec()).ifPresent(c -> {
            throw new BadRequestException("A customer with PEC " + payload.pec() + " already exists in our system");
        });

        Address operationalAddress = payload.operationalAddress() != null
                ? payload.operationalAddress()
                : payload.legalAddress();


        LocalDate lastContactDate = payload.lastContactDate() != null
                ? payload.lastContactDate()
                : payload.insertDate();

        Customer newCustomer = new Customer(
                payload.companyName(),
                payload.vatNumb(),
                payload.email(),
                payload.insertDate(),
                lastContactDate,
                payload.annualRevenue(),
                payload.pec(),
                payload.phoneNumb(),
                payload.contactEmail(),
                payload.contactFirstName(),
                payload.contactLastName(),
                payload.contactPhoneNumb(),
                payload.companyLogo(),
                payload.clientType(),
                payload.legalAddress(),
                operationalAddress
        );

        Customer savedCustomer = customersRepository.save(newCustomer);

        log.info("Customer " + savedCustomer.getCompanyName() + " with VAT " + savedCustomer.getVatNumb() + " has been created");

        return savedCustomer;
    }

    public Page<Customer> findAllCustomers(int pageNumb, int pageSize) {
        if (pageSize > 50) pageSize = 50;
        Pageable pageable = PageRequest.of(pageNumb, pageSize);
        return customersRepository.findAll(pageable);
    }

    public Customer findCustomerById(UUID customerId) {
        return customersRepository.findById(customerId).orElseThrow(() -> new NotFoundException("Company with ID " + customerId + " not found"));
    }

    public Customer findCustomerByIdAndUpdate(UUID customerId, NewCustomerDTO payload) {
        Customer foundCustomer = findCustomerById(customerId);

        Address operationalAddress = payload.operationalAddress() != null
                ? payload.operationalAddress()
                : payload.legalAddress();

        foundCustomer.setCompanyName(payload.companyName());
        foundCustomer.setVatNumb(payload.vatNumb());
        foundCustomer.setEmail(payload.email());
        foundCustomer.setInsertDate(payload.insertDate());
        foundCustomer.setLastContactDate(payload.lastContactDate());
        foundCustomer.setAnnualRevenue(payload.annualRevenue());
        foundCustomer.setPec(payload.pec());
        foundCustomer.setPhoneNumb(payload.phoneNumb());
        foundCustomer.setContactEmail(payload.contactEmail());
        foundCustomer.setContactFirstName(payload.contactFirstName());
        foundCustomer.setContactLastName(payload.contactLastName());
        foundCustomer.setContactPhoneNumb(payload.contactPhoneNumb());
        foundCustomer.setCompanyLogo(payload.companyLogo());
        foundCustomer.setClientType(payload.clientType());
        foundCustomer.setLegalAddress(payload.legalAddress());
        foundCustomer.setOperationalAddress(operationalAddress);

        Customer updatedCustomer = customersRepository.save(foundCustomer);

        log.info("Customer " + updatedCustomer.getCompanyName() + " with VAT " + updatedCustomer.getVatNumb() + " has been updated");

        return updatedCustomer;
    }

    public void deleteCustomer(UUID customerId) {
        Customer c = findCustomerById(customerId);
        customersRepository.delete(c);
        log.info("Customer " + c.getCompanyName() + " with VAT " + c.getVatNumb() + " has been deleted");
    }

    public Page<Customer> filterByPartialCompanyName(String name, int page, int size, String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return customersRepository.findByCompanyNameContainingIgnoreCase(name, pageable);
    }

    public Page<Customer> filterByAnnualRevenue(Double revenue, int page, int size, String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return customersRepository.findByAnnualRevenue(revenue, pageable);
    }

    public Page<Customer> filterByInsertDate(LocalDate date, int page, int size, String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return customersRepository.findByInsertDate(date, pageable);
    }

    public Page<Customer> filterByLastContactDate(LocalDate date, int page, int size, String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return customersRepository.findByLastContactDate(date, pageable);
    }

    public Page<Customer> filterByProvinceName(String provinceName, int page, int size, String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return customersRepository.findByLegalAddress_Municipality_Province_NameIgnoreCase(provinceName, pageable);
    }

    //TODO: l'admin può inviare mail al cliente?

}
