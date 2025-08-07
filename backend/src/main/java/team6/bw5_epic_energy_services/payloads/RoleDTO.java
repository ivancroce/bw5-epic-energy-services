package team6.bw5_epic_energy_services.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record RoleDTO(
        @NotEmpty(message = "The role name is mandatory.")
        @Size(min = 3, max = 20, message = "The role name must be between 3 and 20 characters.")
        String name) {
}
