package team6.bw5_epic_energy_services.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    @Column(name = "role_id", nullable = false)
    private UUID id;
    @Column(name = "name")
    private String name;

    public Role(String name) {
        this.name = name;
    }
}
