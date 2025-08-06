package team6.bw5_epic_energy_services.payloads;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import team6.bw5_epic_energy_services.entities.Province;

public record NewMunicipalityDTO(
        @NotNull(message = "Municipality must have a name")
        @Size(min = 2, max = 50, message = "The name must be between 2 and 50 characters!")
        String name,
        @NotNull(message = "The province code cannot be empty!")
        @Size(min = 2, max = 20, message = "The province code must be between 2 and 20 characters!")
        String provinceCode,
        @NotNull(message = "The code cannot be empty!")
        @Size(min = 2, max = 20, message = "The code must be between 2 and 20 characters!")
        String progressiveMunicipalityCode,
        @NotNull(message = "The province name cannot be empty!")
        @Size(min = 2, max = 50, message = "The province name must be between 2 and 50 characters!")
        String provinceName,
        @NotNull(message = "The province cannot be empty!")
        @Size(min = 2, max = 50, message = "The province must be between 2 and 50 characters!")
        Province province
) {
}
