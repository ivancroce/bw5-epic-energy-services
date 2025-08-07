package team6.bw5_epic_energy_services.entities;

import jakarta.persistence.*;
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
    @Column(name = "company_name")
    private String companyName;
    @Column(name = "vat_number")
    private String vatNumb;
    @Column(name = "email")
    private String email;
    @Column(name = "insert_date")
    private LocalDate insertDate;
    @Column(name = "last_contact_date")
    private LocalDate lastContactDate;
    @Column(name = "annual_revenue")
    private Double annualRevenue;
    @Column(name = "pec")
    private String pec;
    @Column(name = "phone_number")
    private String phoneNumb;
    @Column(name = "contact_email")
    private String contactEmail;
    @Column(name = "contact_first_name")
    private String contactFirstName;
    @Column(name = "contact_last_name")
    private String contactLastName;
    @Column(name = "contact_phone_number")
    private String contactPhoneNumb;
    @Column(name = "company_logo")
    private String companyLogo;
    @Enumerated(EnumType.STRING)
    @Column(name = "client_type")
    private ClientType clientType;

    @ManyToOne
    @JoinColumn(name = "legal_address_id", nullable = false)
    private Address legalAddress;
    
    @ManyToOne
    @JoinColumn(name = "operational_address_id", nullable = false)
    private Address operationalAddress;

    public Customer(String companyName, String vatNumb, String email, LocalDate insertDate, LocalDate lastContactDate, Double annualRevenue, String pec, String phoneNumb, String contactEmail, String contactFirstName, String contactLastName, String contactPhoneNumb, String companyLogo, ClientType clientType, Address legalAddress, Address operationalAddress) {
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
        this.legalAddress = legalAddress;
        this.operationalAddress = operationalAddress;
    }
}
