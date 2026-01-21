package anagrafica.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "Company_Insurance_Info")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompanyInsuranceInfo extends AuditableEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "CompanyId")
    private Company company;
    private String sdi;
    private String pec;
    private Boolean isValidate;
    private Boolean isSdi;
    private String email;
}
