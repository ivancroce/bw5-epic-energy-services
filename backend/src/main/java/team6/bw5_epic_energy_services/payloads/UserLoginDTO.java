package team6.bw5_epic_energy_services.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record UserLoginDTO(
        @Email(message = "The email is not valid")
        @NotEmpty(message = "Email is mandatory")
        String email,
        @NotEmpty(message = "Password is mandatory")
        String password) {
}
