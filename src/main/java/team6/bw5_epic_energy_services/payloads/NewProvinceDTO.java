package team6.bw5_epic_energy_services.payloads;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record NewProvinceDTO(
        @NotNull(message = "Il nome non può essere vuoto!")
        @Size(min = 2, max = 50, message = "Il nome deve essere compreso tra 2 e 50 caratteri!")
        String name,
        @NotNull(message = "Il codice non può essere vuoto!")
        @Size(min = 2, max = 20, message = "Il codice deve essere compreso tra 2 e 20 caratteri!")
        String code,
        @NotNull(message = "La provincia non può essere vuota!")
        @Size(min = 2, max = 40, message = "La regione deve essere compresa tra 2 e 40 caratteri!")
        String region
) {
}
