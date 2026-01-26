package anagrafica.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Regione")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Regione {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Codice", nullable = false, unique = true, length = 10)
    private String codice;

    @Column(name = "Nome", nullable = false, length = 100)
    private String nome;

    @Column(name = "Sigla", length = 10)
    private String sigla;

    @Column(name = "CreatedAt", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "UpdatedAt")
    private LocalDateTime updatedAt;

    @Column(name = "IsDeleted")
    private Boolean isDeleted = false;

    // Relazioni
    @OneToMany(mappedBy = "regione", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Provincia> province;

    @OneToMany(mappedBy = "regione", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Citta> citta;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (isDeleted == null) {
            isDeleted = false;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
