package anagrafica.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Zone")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Zone extends AuditableEntityExt{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "Name")
    private String name;
    @ManyToOne
    @JoinColumn(name = "CittaId")
    private Citta citta;
}
