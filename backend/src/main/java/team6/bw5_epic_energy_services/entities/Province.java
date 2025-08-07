package team6.bw5_epic_energy_services.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "provinces")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Province {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private String region;

    public Province(String name, String code, String region) {
        this.name = name;
        this.code = code;
        this.region = region;
    }
}
