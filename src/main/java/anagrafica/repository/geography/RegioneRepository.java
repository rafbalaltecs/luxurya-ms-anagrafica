package anagrafica.repository.geography;

import anagrafica.entity.Regione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RegioneRepository extends JpaRepository<Regione, Long> {
    Optional<Regione> findByCodice(String codice);

    Optional<Regione> findByNome(String nome);

    List<Regione> findAllByIsDeletedFalse();

    boolean existsByCodice(String codice);
}
