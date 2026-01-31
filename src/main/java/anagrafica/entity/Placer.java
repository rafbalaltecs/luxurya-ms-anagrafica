package anagrafica.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Placer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Placer extends AuditableEntityExt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "Name")
    private String name;
    @Column(name = "Surname")
    private String surname;
    @ManyToOne
    @JoinColumn(name = "UserId")
    private User user;
    @Column(name = "IsActive")
    private Boolean isActive;
}
