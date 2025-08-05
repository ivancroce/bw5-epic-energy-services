package team6.bw5_epic_energy_services.payloads;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import team6.bw5_epic_energy_services.entities.Municipality;

public record NewAddressDTO(
        @NotNull(message = "La via non può essere vuota!")
        @Size(min = 2, max = 50, message = "La via deve essere compresa tra 2 e 50 caratteri!")
        String street,
        @NotNull(message = "Il numero dell'edificio non può essere vuoto!")
        @Size(min = 2, max = 20, message = "Il numero dell'edificio deve essere compreso tra 2 e 20 caratteri!")
        String buildingNumber,
        @NotNull(message = "La posizione non può essere vuota!")
        @Size(min = 2, max = 50, message = "La posizione deve essere compresa tra 2 e 50 caratteri!")
        String location,
        @NotNull(message = "Il codice postale non può essere vuoto!")
        @Size(min = 3, max = 10, message = "Il codice postale deve essere compreso tra 3 e 10 cifre!")
        String postalCode,
        @NotNull(message = "Il codice postale non può essere vuoto!")
        @Size(min = 3, max = 10, message = "Il codice postale deve essere compreso tra 3 e 10 cifre!")
        Municipality municipality
) {
}
