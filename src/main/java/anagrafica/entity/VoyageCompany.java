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
@Table(name = "Voyage_Company")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VoyageCompany extends AuditableEntityExt{
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	@ManyToOne
    @JoinColumn(name = "VoyageId")
	private Voyage voyage;
	@ManyToOne
    @JoinColumn(name = "CompanyId")
	private Company company;
	@ManyToOne
    @JoinColumn(name = "AgentId")
	private Agent agent;
	@Column(name = "IsCompleted")
	private Boolean isCompleted = false;
	@ManyToOne
    @JoinColumn(name = "TypePaymentId")
	private TypePayment typePayment;
	@ManyToOne
    @JoinColumn(name = "TypeDocumentVoyageId")
	private TypeDocumentVoyage typeDocumentVoyage;
	@Column(name = "IsShipping")
	private Boolean isShipping = false;
	@Column(name = "IsBusinessClosure")
	private Boolean isBusinessClosure = false;
	@Column(name = "IsTravel")
	private Boolean isTravel = false;
	@Column(name = "IsTotalPickup")
	private Boolean isTotalPickup = false;
	@Column(name = "IsExternal")
	private Boolean isExternal = false;
}
