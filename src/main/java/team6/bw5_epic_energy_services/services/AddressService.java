package team6.bw5_epic_energy_services.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import team6.bw5_epic_energy_services.entities.Address;
import team6.bw5_epic_energy_services.entities.Municipality;
import team6.bw5_epic_energy_services.exceptions.BadRequestException;
import team6.bw5_epic_energy_services.exceptions.NotFoundException;
import team6.bw5_epic_energy_services.payloads.NewAddressDTO;
import team6.bw5_epic_energy_services.repositories.AddressRepository;
import team6.bw5_epic_energy_services.repositories.MunicipalityRepository;

import java.util.UUID;

@Service
@Slf4j
public class AddressService {
    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private MunicipalityRepository municipalityRepository;

    //----------------------------FIND ALL-----------------------------------------------------------
    public Page<Address> findAll(int pageNumber, int pageSize, String sortBy) {
        if (pageSize > 50) pageSize = 50;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).descending());
        return this.addressRepository.findAll(pageable);
    }

    //------------------------------SAVE-----------------------------------------------
    public Address save(NewAddressDTO payload) {
        Municipality municipality = this.municipalityRepository.findById(payload.municipalityId())
                .orElseThrow(() -> new NotFoundException("Municipality with ID " + payload.municipalityId() + " not found!"));

        addressRepository.findByStreetAndBuildingNumberAndPostalCodeAndMunicipality(
                payload.street(),
                payload.buildingNumber(),
                payload.postalCode(),
                municipality
        ).ifPresent(address -> {
            throw new BadRequestException("Address already exists with ID: " + address.getId());
        });

        Address newAddress = new Address(
                payload.street(),
                payload.buildingNumber(),
                payload.location(),
                payload.postalCode(),
                municipality
        );

        Address savedAddress = this.addressRepository.save(newAddress);
        log.info("Address {} has been successfully saved", savedAddress.getStreet());
        return savedAddress;

    }

    public Address findAddressById(UUID addressId) {
        return addressRepository.findById(addressId).orElseThrow(() -> new NotFoundException("Address not found"));
    }

    public Address findAddressByIdAndUpdate(UUID addressId, NewAddressDTO payload) {

        Address foundAddress = this.findAddressById(addressId);

        Municipality municipality = this.municipalityRepository.findById(payload.municipalityId())
                .orElseThrow(() -> new NotFoundException("Municipality with ID " + payload.municipalityId() + " not found!"));

        foundAddress.setStreet(payload.street());
        foundAddress.setBuildingNumber(payload.buildingNumber());
        foundAddress.setLocation(payload.location());
        foundAddress.setPostalCode(payload.postalCode());
        foundAddress.setMunicipality(municipality);

        log.info("Address " + foundAddress + " has been updated");

        return addressRepository.save(foundAddress);

    }

    public void deleteAddress(UUID addressId) {
        Address a = findAddressById(addressId);
        addressRepository.delete(a);
        log.info("The address" + a.getStreet() + " has been deleted");
    }
}
