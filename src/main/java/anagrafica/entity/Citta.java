package anagrafica.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Provincia")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Citta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Codice", nullable = false, unique = true, length = 10)
    private String codice;

    @Column(name = "Nome", nullable = false, length = 100)
    private String nome;

    @Column(name = "CodiceCatastale", length = 10)
    private String codiceCatastale;

    @Column(name = "CAP", length = 10)
    private String cap;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ProvinciaId", nullable = false)
    private Provincia provincia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RegioneId", nullable = false)
    private Regione regione;

    @Column(name = "Popolazione")
    private Integer popolazione;

    @Column(name = "CreatedAt", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "UpdatedAt")
    private LocalDateTime updatedAt;

    @Column(name = "IsDeleted")
    private Boolean isDeleted = false;

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
