package team6.bw5_epic_energy_services.payloads;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record NewInvoiceDTO(
        @NotNull(message = "An invoice date date is required")
        LocalDate date,
        @NotNull(message = "An invoice amount is required")
        Double amount,

        int number,
        @NotNull(message = "An invoice status ID is required")
        UUID invoiceStatusId,

        @NotNull(message = "A customer ID is required")
        UUID customerId) {
}
