package team6.bw5_epic_energy_services.payloads;

import jakarta.validation.constraints.Size;

public record UserUpdateDTO(
        @Size(min = 3, max = 20, message = "The username must be between 3 and 20 characters")
        String username,
        @Size(min = 3, max = 30, message = "The name must be between 3 and 30 characters")
        String name,
        @Size(min = 3, max = 30, message = "The surname must be between 3 and 30 characters")
        String surname,
        String avatar) {
}
