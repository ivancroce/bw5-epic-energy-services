package team6.bw5_epic_energy_services.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team6.bw5_epic_energy_services.entities.Customer;
import team6.bw5_epic_energy_services.repositories.CustomersRepository;

@Service
public class CustomerService {
    @Autowired
    private CustomersRepository customersRepository;

    public Customer saveCustomer(Customer payload) {

    }
}
