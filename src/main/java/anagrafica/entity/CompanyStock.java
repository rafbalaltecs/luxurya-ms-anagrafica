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
@Table(name = "Company_Stock")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompanyStock extends AuditableEntityExt{
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
    private Long id;
	@ManyToOne
    @JoinColumn(name = "CompanyId")
	private Company company;
	@Column(name = "ProductIdExt")
	private Long productIdExt;
	@Column(name = "Quantity")
	private Long quantity;
	@ManyToOne
    @JoinColumn(name = "OperationId")
	private VoyageCompanyOperation operation;
}
