package team6.bw5_epic_energy_services.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import team6.bw5_epic_energy_services.entities.Address;
import team6.bw5_epic_energy_services.exceptions.ValidationException;
import team6.bw5_epic_energy_services.payloads.NewAddressDTO;
import team6.bw5_epic_energy_services.services.AddressService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/addresses")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Address createAddress(@RequestBody @Validated NewAddressDTO payload, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            List<String> errors = validationResult.getFieldErrors().stream()
                    .map(fieldError -> fieldError.getDefaultMessage())
                    .toList();
            throw new ValidationException(errors);
        }

        return addressService.save(payload);
    }

    @GetMapping
    public Page<Address> getAllAddresses(@RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "10") int size,
                                         @RequestParam(defaultValue = "id") String sortBy) {
        return addressService.findAll(page, size, sortBy);
    }

    @GetMapping("/{addressId}")
    public Address getAddressById(@PathVariable UUID addressId) {
        return addressService.findAddressById(addressId);
    }

    @PutMapping("/{addressId}")
    public Address updateAddress(@PathVariable UUID addressId, @RequestBody @Validated NewAddressDTO payload, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            List<String> errors = validationResult.getFieldErrors().stream()
                    .map(fieldError -> fieldError.getDefaultMessage())
                    .toList();
            throw new ValidationException(errors);
        }

        return addressService.findAddressByIdAndUpdate(addressId, payload);
    }

    @DeleteMapping("/{addressId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAddress(@PathVariable UUID addressId) {
        addressService.deleteAddress(addressId);
    }
}
