package anagrafica.entity;

import java.time.LocalDate;

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
@Table(name = "Voyage")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Voyage extends AuditableEntityExt{
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	@ManyToOne
    @JoinColumn(name = "AgentId")
	private Agent agent;
    @ManyToOne
    @JoinColumn(name = "ZoneId")
	private Zone zone;
    @Column(name = "StartDate")
	private LocalDate startDate;
    @Column(name = "EndDate")
	private LocalDate endDate;
    @Column(name = "Finished")
    private Boolean isFinished;
    @Column(name = "Code")
    private String code;
	
}
