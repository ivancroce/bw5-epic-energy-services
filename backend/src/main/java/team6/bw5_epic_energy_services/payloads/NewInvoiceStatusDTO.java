package team6.bw5_epic_energy_services.payloads;

import jakarta.validation.constraints.NotEmpty;

public record NewInvoiceStatusDTO(
        @NotEmpty(message = "An invoice status is required")
        String statusName
) {
}
