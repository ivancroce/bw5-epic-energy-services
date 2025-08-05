package team6.bw5_epic_energy_services.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team6.bw5_epic_energy_services.entities.Address;
import team6.bw5_epic_energy_services.entities.Customer;
import team6.bw5_epic_energy_services.exceptions.BadRequestException;
import team6.bw5_epic_energy_services.payloads.NewCustomerDTO;
import team6.bw5_epic_energy_services.repositories.CustomersRepository;

import java.time.LocalDate;

@Slf4j
@Service
public class CustomerService {
    @Autowired
    private CustomersRepository customersRepository;

    //TODO: dovremmo verificare che sia un admin a salvare, modificare e eliminare i Customers
    
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

}
