package team6.bw5_epic_energy_services.payloads;

import java.time.LocalDateTime;

public record EmailResponseDTO(String message, String recipientEmail, LocalDateTime stamp) {
}
