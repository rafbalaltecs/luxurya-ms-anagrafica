package anagrafica.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Agent")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Agent extends AuditableEntityExt{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;
    @Column(name = "Name")
    private String name;
    @Column(name = "Surname")
    private String surname;
    @ManyToOne
    @JoinColumn(name = "UserId")
    private User user;
    @Column(name = "Telephone")
    private String telephone;
}
