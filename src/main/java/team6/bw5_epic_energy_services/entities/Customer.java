package team6.bw5_epic_energy_services.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import team6.bw5_epic_energy_services.entities.enums.ClientType;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "customers")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Customer {
    @Id
    @GeneratedValue
    @Column(name = "customer_id", nullable = false)
    @Setter(AccessLevel.NONE)
    private UUID id;
    @NotEmpty(message = "Company Name is required")
    private String companyName;

    private String vatNumb;
    @Email
    @NotEmpty(message = "Company email is required")
    private String email;
    @NotNull(message = "Customer's acceptance date is required")
    private LocalDate insertDate;

    private LocalDate lastContactDate;
    @NotNull(message = "Annual revenue is required")
    private Double annualRevenue;
    @Email
    @NotEmpty(message = "Company PEC is required")
    private String pec;

    private String phoneNumb;
    @Email
    @NotEmpty(message = "Contact email is required")
    private String contactEmail;
    @NotEmpty(message = "Contact Name is required")
    private String contactFirstName;
    @NotEmpty(message = "Contact Surname is required")
    private String contactLastName;

    private String contactPhoneNumb;

    private String companyLogo;
    @Enumerated(EnumType.STRING)
    private ClientType clientType;

//    @OneToOne
//    @ManyToOne
//    @JoinColumn(name = "legal_address_id", nullable = false)
//    private Address legalAddress;
//
//    @OneToOne
//    @ManyToOne
//    @JoinColumn(name = "operational_address_id", nullable = false)
//    private Address operationalAddress;

    public Customer(String companyName, String vatNumb, String email, LocalDate insertDate, LocalDate lastContactDate, double annualRevenue, String pec, String phoneNumb, String contactEmail, String contactFirstName, String contactLastName, String contactPhoneNumb, String companyLogo, ClientType clientType) {
        this.companyName = companyName;
        this.vatNumb = vatNumb;
        this.email = email;
        this.insertDate = insertDate;
        this.lastContactDate = lastContactDate;
        this.annualRevenue = annualRevenue;
        this.pec = pec;
        this.phoneNumb = phoneNumb;
        this.contactEmail = contactEmail;
        this.contactFirstName = contactFirstName;
        this.contactLastName = contactLastName;
        this.contactPhoneNumb = contactPhoneNumb;
        this.companyLogo = companyLogo;
        this.clientType = clientType;
//        this.legalAddress = legalAddress;
//        this.operationalAddress = operationalAddress;
    }
}
