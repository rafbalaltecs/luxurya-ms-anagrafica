package anagrafica.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Agent_Zone")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AgentZone extends AuditableEntityExt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "ZoneId")
    private Zone zone;
    @ManyToOne
    @JoinColumn(name = "AgentId")
    private Agent agent;
    @Column(name = "IsActive")
    private Boolean isActive;
}
