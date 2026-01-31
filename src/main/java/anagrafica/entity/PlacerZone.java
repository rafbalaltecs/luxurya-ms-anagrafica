package anagrafica.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Placer_Zone")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlacerZone extends AuditableEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "ZoneId")
    private Zone zone;
    @ManyToOne
    @JoinColumn(name = "PlacerId")
    private Placer placer;
    @Column(name = "IsActive")
    private Boolean isActive;
}
