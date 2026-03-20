package anagrafica.entity;

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
@Table(name = "Voyage_Document")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VoyageDocument extends AuditableEntityExt{
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	@ManyToOne
    @JoinColumn(name = "TypeDocumentId")
	private TypeDocumentVoyage typeDocument;
	@ManyToOne
    @JoinColumn(name = "VoyageId")
	private Voyage voyage;
	@ManyToOne
    @JoinColumn(name = "DocumentId")
	private Document document;
}
