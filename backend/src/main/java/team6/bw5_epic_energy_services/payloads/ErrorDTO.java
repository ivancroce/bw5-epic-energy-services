package team6.bw5_epic_energy_services.payloads;

import java.time.LocalDateTime;

public record ErrorDTO(String message, LocalDateTime stamp) {
}
