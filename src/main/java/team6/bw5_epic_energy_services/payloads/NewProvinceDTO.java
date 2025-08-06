package team6.bw5_epic_energy_services.payloads;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record NewProvinceDTO(
        @NotNull(message = "A province must have a name")
        @Size(min = 2, max = 50, message = "The province name must be between 2 and 50 characters!")
        String name,
        @NotNull(message = "A province must have a code!")
        @Size(min = 2, max = 20, message = "The province code must be between 2 and 20 characters!")
        String code,
        @NotNull(message = "A province must have a region")
        @Size(min = 2, max = 40, message = "The region must be between 2 and 50 characters!")
        String region
) {
}
