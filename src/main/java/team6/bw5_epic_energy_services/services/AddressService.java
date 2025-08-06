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
import team6.bw5_epic_energy_services.exceptions.NotFoundException;
import team6.bw5_epic_energy_services.payloads.NewAddressDTO;
import team6.bw5_epic_energy_services.repositories.AddressRepository;

import java.util.UUID;

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
            throw new BadRequestException("Address " + address.getStreet() + " already exists in our system");
        });
        Address newAddress = new Address(payload.street(), payload.buildingNumber(), payload.location(), payload.postalCode(), payload.municipality());
        Address savedAddress = this.addressRepository.save(newAddress);

        log.info("Address " + savedAddress.getStreet() + " has been successfully saved");

        return savedAddress;
    }

    public Address findAddressById(UUID addressId) {
        return addressRepository.findById(addressId).orElseThrow(() -> new NotFoundException("Address not found"));
    }

    public Address findAddressByIdAndUpdate(UUID addressId, NewAddressDTO payload) {

        Address foundAddress = findAddressById(addressId);

        foundAddress.setStreet(payload.street());
        foundAddress.setBuildingNumber(payload.buildingNumber());
        foundAddress.setLocation(payload.location());
        foundAddress.setPostalCode(payload.postalCode());
        foundAddress.setMunicipality(payload.municipality());

        Address updatedAddress = addressRepository.save(foundAddress);

        log.info("Address " + updatedAddress.getStreet() + " has been updated");

        return updatedAddress;
    }

    public void deleteAddress(UUID addressId) {
        Address a = findAddressById(addressId);
        addressRepository.delete(a);
        log.info("The address" + a.getStreet() + " has been deleted");
    }
}
