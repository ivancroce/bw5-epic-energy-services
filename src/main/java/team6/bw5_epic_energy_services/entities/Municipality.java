package team6.bw5_epic_energy_services.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "municipalities")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Municipality {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false, name = "province_code")
    private String provinceCode;
    @Column(nullable = false, name = "progressive_municipality_code")
    private String progressiveMunicipalityCode;
    @Column(nullable = false, name = "province_name")
    private String provinceName;

    @ManyToOne
    @JoinColumn(name = "province_id", nullable = false)
    private Province province;

    public Municipality(String name, String provinceCode, String progressiveMunicipalityCode, String provinceName, Province province) {
        this.name = name;
        this.provinceCode = provinceCode;
        this.progressiveMunicipalityCode = progressiveMunicipalityCode;
        this.provinceName = provinceName;
        this.province = province;
    }
}
