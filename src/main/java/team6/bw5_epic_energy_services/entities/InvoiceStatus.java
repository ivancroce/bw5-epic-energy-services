package team6.bw5_epic_energy_services.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "invoices_status")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class InvoiceStatus {
    @Id
    @GeneratedValue
    @Column(name = "invoices_status_id", nullable = false)
    @Setter(AccessLevel.NONE)
    private UUID id;
    @Column(name = "status_name")
    private String statusName;

    public InvoiceStatus(String statusName) {
        this.statusName = statusName;
    }
}
