package anagrafica.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Agent_Current_Voyage")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AgentCurrentVoyage extends AuditableEntityExt{
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
    @JoinColumn(name = "AgentId")
	private Agent agent;
	@Column(name = "VoyageNumber")
	private Integer voyageNumber;
}
