package anagrafica.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Zone_Company")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ZoneCompany extends AuditableEntityExt{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "CompanyId")
    private Company company;
    @ManyToOne
    @JoinColumn(name = "ZoneId")
    private Zone zone;
}
