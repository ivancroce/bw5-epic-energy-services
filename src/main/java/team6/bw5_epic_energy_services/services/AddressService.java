package team6.bw5_epic_energy_services.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import team6.bw5_epic_energy_services.entities.Address;
import team6.bw5_epic_energy_services.exceptions.BadRequestException;
import team6.bw5_epic_energy_services.payloads.NewAddressDTO;
import team6.bw5_epic_energy_services.repositories.AddressRepository;

@Service
@Slf4j
public class AddressService {
    @Autowired
    private AddressRepository addressRepository;

    //----------------------------FIND ALL-----------------------------------------------------------
    public Page<Address> findAll(int pageNumber, int pageSize, String sortBy) {
        if (pageSize > 50) pageSize = 50;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).descending());
        return this.addressRepository.findAll(pageable);
    }

    //------------------------------SAVE-----------------------------------------------
    public Address save(NewAddressDTO payload) {
        this.addressRepository.findByStreet(payload.street()).ifPresent(address -> {
            throw new BadRequestException("La via " + address.getStreet() + "è già in uso!");
        });
        Address newAddress = new Address(payload.street(), payload.buildingNumber(), payload.location(), payload.postalCode(), payload.municipality());
        Address savedAddress = this.addressRepository.save(newAddress);

        log.info("L'indirizzo con via " + savedAddress.getId() + "è stato salvato con successo!");

        return savedAddress;
    }
}
