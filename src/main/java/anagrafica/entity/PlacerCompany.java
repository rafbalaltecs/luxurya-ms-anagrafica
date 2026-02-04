package anagrafica.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Placer_Company")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlacerCompany extends AuditableEntityExt{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "PlacerId")
    private Placer placer;
    @ManyToOne
    @JoinColumn(name = "CompanyId")
    private Company company;
}
