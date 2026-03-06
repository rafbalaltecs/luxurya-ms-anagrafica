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
@Table(name = "Voyage_Company_Operation")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VoyageCompanyOperation extends AuditableEntityExt {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	@ManyToOne
    @JoinColumn(name = "VoyageCompanyId")
	private VoyageCompany voyageCompany;
	@ManyToOne
    @JoinColumn(name = "OperationId")
	private TypeVoyageOperation operation;
	@Column(name = "ProductIdExt")
	private Long productIdExt;
	@Column(name = "OperationValue")
	private Double operationValue;
	@ManyToOne
    @JoinColumn(name = "TypePaymentId")
	private TypePayment typePayment;
	
}
