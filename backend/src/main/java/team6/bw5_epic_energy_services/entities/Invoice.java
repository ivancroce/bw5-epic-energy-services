package team6.bw5_epic_energy_services.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "invoices")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Invoice {
    @Id
    @GeneratedValue
    @Column(name = "invoice_id", nullable = false)
    @Setter(AccessLevel.NONE)
    private UUID id;
    @Column(name = "date")
    private LocalDate date;
    @Column(name = "amount")
    private Double amount;
    @Column(name = "number")
    private int number;
    @ManyToOne
    @JoinColumn(name = "invoice_status_id", nullable = false)
    private InvoiceStatus invoiceStatusId;
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    public Invoice(LocalDate date, Double amount, int number, InvoiceStatus invoiceStatusId, Customer customer) {
        this.date = date;
        this.amount = amount;
        this.number = number;
        this.invoiceStatusId = invoiceStatusId;
        this.customer = customer;
    }
}
