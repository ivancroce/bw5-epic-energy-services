package team6.bw5_epic_energy_services.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import team6.bw5_epic_energy_services.entities.Address;
import team6.bw5_epic_energy_services.entities.Customer;
import team6.bw5_epic_energy_services.exceptions.BadRequestException;
import team6.bw5_epic_energy_services.exceptions.NotFoundException;
import team6.bw5_epic_energy_services.payloads.NewCustomerDTO;
import team6.bw5_epic_energy_services.repositories.AddressRepository;
import team6.bw5_epic_energy_services.repositories.CustomersRepository;

import java.time.LocalDate;
import java.util.UUID;

@Slf4j
@Service
public class CustomerService {
    @Autowired
    private CustomersRepository customersRepository;

    @Autowired
    private AddressRepository addressRepository;


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

        Address legalAddress = this.addressRepository.findById(payload.legalAddressId())
                .orElseThrow(() -> new NotFoundException("Address with ID " + payload.legalAddressId() + " not found!"));

        Address operationalAddress;

        if (payload.operationalAddressId() != null) {
            operationalAddress = addressRepository.findById(payload.operationalAddressId())
                    .orElseThrow(() -> new NotFoundException("Operational Address with ID " + payload.operationalAddressId() + " not found!"));
        } else {
            operationalAddress = legalAddress;
        }

        LocalDate insertDate = LocalDate.now();
        LocalDate lastContactDate = payload.lastContactDate() != null
                ? payload.lastContactDate()
                : insertDate;

        Customer newCustomer = new Customer();


        newCustomer.setCompanyName(payload.companyName());
        newCustomer.setVatNumb(payload.vatNumb());
        newCustomer.setEmail(payload.email());
        newCustomer.setAnnualRevenue(payload.annualRevenue());
        newCustomer.setPec(payload.pec());
        newCustomer.setPhoneNumb(payload.phoneNumb());
        newCustomer.setContactEmail(payload.contactEmail());
        newCustomer.setContactFirstName(payload.contactFirstName());
        newCustomer.setContactLastName(payload.contactLastName());
        newCustomer.setContactPhoneNumb(payload.contactPhoneNumb());
        newCustomer.setCompanyLogo(payload.companyLogo());
        newCustomer.setClientType(payload.clientType());
        newCustomer.setInsertDate(insertDate);
        newCustomer.setLastContactDate(lastContactDate);
        newCustomer.setLegalAddress(legalAddress);
        newCustomer.setOperationalAddress(operationalAddress);

        Customer savedCustomer = customersRepository.save(newCustomer);

        log.info("Customer " + savedCustomer.getCompanyName() + " with VAT " + savedCustomer.getVatNumb() + " has been created");

        return savedCustomer;
    }

    public Page<Customer> findAllCustomers(int pageNumb, int pageSize, String sortBy) {
        if (pageSize > 50) pageSize = 50;
        Pageable pageable = PageRequest.of(pageNumb, pageSize, Sort.by(sortBy).descending());
        return customersRepository.findAll(pageable);
    }

    public Customer findCustomerById(UUID customerId) {
        return customersRepository.findById(customerId).orElseThrow(() -> new NotFoundException("Company with ID " + customerId + " not found"));
    }

    public Customer findCustomerByIdAndUpdate(UUID customerId, NewCustomerDTO payload) {
        Customer foundCustomer = findCustomerById(customerId);

        Address legalAddress = this.addressRepository.findById(payload.legalAddressId())
                .orElseThrow(() -> new NotFoundException("Address with ID " + payload.legalAddressId() + " not found!"));

        Address operationalAddress;

        if (payload.operationalAddressId() != null) {
            operationalAddress = addressRepository.findById(payload.operationalAddressId())
                    .orElseThrow(() -> new NotFoundException("Operational Address with ID " + payload.operationalAddressId() + " not found!"));
        } else {
            operationalAddress = legalAddress;
        }

        LocalDate insertDate = LocalDate.now();
        LocalDate lastContactDate = payload.lastContactDate() != null
                ? payload.lastContactDate()
                : insertDate;


        foundCustomer.setCompanyName(payload.companyName());
        foundCustomer.setVatNumb(payload.vatNumb());
        foundCustomer.setEmail(payload.email());
        foundCustomer.setAnnualRevenue(payload.annualRevenue());
        foundCustomer.setPec(payload.pec());
        foundCustomer.setPhoneNumb(payload.phoneNumb());
        foundCustomer.setContactEmail(payload.contactEmail());
        foundCustomer.setContactFirstName(payload.contactFirstName());
        foundCustomer.setContactLastName(payload.contactLastName());
        foundCustomer.setContactPhoneNumb(payload.contactPhoneNumb());
        foundCustomer.setCompanyLogo(payload.companyLogo());
        foundCustomer.setClientType(payload.clientType());
        foundCustomer.setInsertDate(insertDate);
        foundCustomer.setLastContactDate(lastContactDate);
        foundCustomer.setLegalAddress(legalAddress);
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

//    public Page<Customer> filterByPartialCompanyName(String name, int page, int size, String sortBy, String direction) {
//        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
//        Pageable pageable = PageRequest.of(page, size, sort);
//        return customersRepository.findByCompanyNameContainingIgnoreCase(name, pageable);
//    }
//
//    public Page<Customer> filterByAnnualRevenue(Double revenue, int page, int size, String sortBy, String direction) {
//        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
//        Pageable pageable = PageRequest.of(page, size, sort);
//        return customersRepository.findByAnnualRevenue(revenue, pageable);
//    }
//
//    public Page<Customer> filterByInsertDate(LocalDate date, int page, int size, String sortBy, String direction) {
//        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
//        Pageable pageable = PageRequest.of(page, size, sort);
//        return customersRepository.findByInsertDate(date, pageable);
//    }
//
//    public Page<Customer> filterByLastContactDate(LocalDate date, int page, int size, String sortBy, String direction) {
//        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
//        Pageable pageable = PageRequest.of(page, size, sort);
//        return customersRepository.findByLastContactDate(date, pageable);
//    }
//
//    public Page<Customer> filterByProvinceName(String provinceName, int page, int size, String sortBy, String direction) {
//        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
//        Pageable pageable = PageRequest.of(page, size, sort);
//        return customersRepository.findByLegalAddress_Municipality_Province_NameIgnoreCase(provinceName, pageable);
//    }

    public Page<Customer> searchCustomers(String name,
                                          Double revenue,
                                          LocalDate insertDate,
                                          LocalDate lastContactDate,
                                          int page,
                                          int size,
                                          String sortBy,
                                          String direction) {
        //Specification, Interfaccia si Spring Data Jpa che rappresenta una condizione o un filtro da applicare a una query
        //ci permette quindi di costruire query dinamiche/componibili

        //nome parziale
        Specification<Customer> nameSpec = (root, query, builder) ->
                name == null ? null : builder.like(builder.lower(root.get("companyName")), "%" + name.toLowerCase() + "%");

        //fatturato annnuo - in questo modo filtra esattamente per l'importo (nel evcchio metodo io cercavo per valori superiori o inferiori)
        Specification<Customer> revenueSpec = (root, query, builder) ->
                revenue == null ? null : builder.equal(root.get("annualRevenue"), revenue);

        //data inserimento - in questo modo filtra esattamente per la data (nel evcchio metodo io cercavo per valori superiori o inferiori)
        Specification<Customer> insertDateSpec = (root, query, builder) ->
                insertDate == null ? null : builder.equal(root.get("insertDate"), insertDate);

        //data ultimo contatto - in questo modo filtra esattamente per la data (nel evcchio metodo io cercavo per valori superiori o inferiori)
        Specification<Customer> lastContactDateSpec = (root, query, builder) ->
                lastContactDate == null ? null : builder.equal(root.get("lastContactDate"), lastContactDate);

        Specification<Customer> specification = Specification.<Customer>unrestricted()
                .and(nameSpec)
                .and(revenueSpec)
                .and(insertDateSpec)
                .and(lastContactDateSpec);

        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        // Esegui la query con Specification e paginazione
        return customersRepository.findAll(specification, pageable);
    }

    //TODO: l'admin può inviare mail al cliente?

}
