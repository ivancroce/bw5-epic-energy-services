package team6.bw5_epic_energy_services.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserRegistrationDTO(
        @NotEmpty(message = "The name is mandatory.")
        @Size(min = 3, max = 30, message = "The name must be between 3 and 30 characters.")
        String name,
        @NotEmpty(message = "The surname is mandatory.")
        @Size(min = 3, max = 30, message = "The surname must have between 3 and 30 characters.")
        String surname,
        @NotEmpty(message = "L'username is required.")
        @Size(min = 3, max = 20, message = "Username must be between 3 and  20 characters. ")
        String username,
        @Email(message = "The entered email is not valid.")
        @NotEmpty(message = "Email is required.")
        String email,
        @NotEmpty(message = "Password is required.")
        @Size(min = 8, message = "Password must be at least 8 characters")
        @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9]).{4,}$", message = "The password must contain: 1 uppercase letter, one lowercase...")
        String password) {
}