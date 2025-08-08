package team6.bw5_epic_energy_services.payloads;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record NewAddressDTO(
        @NotNull(message = "Address must have a street")
        @Size(min = 2, max = 50, message = "The street must be between 2 and 50 characters")
        String street,
        @NotNull(message = "Address must have a building number")
        @Size(min = 2, max = 20, message = "The building number must be between 2 and 20 characters!")
        String buildingNumber,
        @NotNull(message = "Address must have a location")
        @Size(min = 2, max = 50, message = "The location must be between 2 and 20 characters!")
        String location,
        @NotNull(message = "Address must have a postal code")
        @Size(min = 3, max = 10, message = "The postal code must be between 3 and 10 digits!")
        String postalCode,
        @NotNull(message = "Address must have a municipality")
        UUID municipalityId
) {
}
