package team6.bw5_epic_energy_services.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "addresses")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Address {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    @Column(nullable = false)
    private String street;
    @Column(nullable = false, name = "building_number")
    private String buildingNumber;
    @Column(nullable = false)
    private String location;
    @Column(nullable = false, name = "postal_code")
    private String postalCode;

    @ManyToOne
    @JoinColumn(name = "municipality_id", nullable = false)
    private Municipality municipality;

    public Address(String street, String buildingNumber, String location, String postalCode, Municipality municipality) {
        this.street = street;
        this.buildingNumber = buildingNumber;
        this.location = location;
        this.postalCode = postalCode;
        this.municipality = municipality;
    }
}
