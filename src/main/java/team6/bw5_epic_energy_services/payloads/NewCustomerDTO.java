package team6.bw5_epic_energy_services.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import team6.bw5_epic_energy_services.entities.Address;
import team6.bw5_epic_energy_services.entities.enums.ClientType;

import java.time.LocalDate;

public record NewCustomerDTO(
        @NotEmpty(message = "The Company must have a name")
        String companyName,
        @NotEmpty(message = "The Company must have a VAT number")
        String vatNumb,
        @Email
        @NotEmpty(message = "The Company must have an email")
        String email,
        @NotNull(message = "An insert date is required")
        LocalDate insertDate,
        //TODO:dovremmo cambiare il costruttore per fare in modo che alla creazione si setti ina utomatico come insertDate
        @NotNull(message = "A last contact date date is required")
        LocalDate lastContactDate,
        @NotNull(message = "The annual revenue is required")
        Double annualRevenue,
        @Email
        @NotEmpty(message = "The Company must have a PEC")
        String pec,

        String phoneNumb,

        String contactEmail,

        String contactFirstName,
        @NotEmpty(message = "The Company contact must have a name")
        String contactLastName,

        String contactPhoneNumb,

        String companyLogo,
        @NotNull(message = "Company type type is required")
        ClientType clientType,

        @NotNull(message = "The Company must have a legal address")
        Address legalAddress,
        //TODO:dovremmo cambiare il costruttore per fare in modo che alla creazione si setti ina utomatico come legalAddress
        Address operationalAddress) {
}
