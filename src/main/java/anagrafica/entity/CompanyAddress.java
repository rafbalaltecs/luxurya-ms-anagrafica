package anagrafica.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Company_Address")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompanyAddress extends AuditableEntityExt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "AddressId")
    private Address address;
    @ManyToOne
    @JoinColumn(name = "CompanyId")
    private Company company;
}
