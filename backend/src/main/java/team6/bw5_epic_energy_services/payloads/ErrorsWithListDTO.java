package team6.bw5_epic_energy_services.payloads;

import java.time.LocalDateTime;
import java.util.List;

public record ErrorsWithListDTO(String message, LocalDateTime stamp, List<String> errorsList) {
}
