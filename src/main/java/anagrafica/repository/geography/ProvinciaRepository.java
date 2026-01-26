package anagrafica.repository.geography;

import anagrafica.entity.Provincia;
import anagrafica.entity.Regione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProvinciaRepository extends JpaRepository<Provincia, Long> {
    Optional<Provincia> findBySigla(String sigla);

    Optional<Provincia> findByCodice(String codice);

    List<Provincia> findByRegione(Regione regione);

    List<Provincia> findByRegioneId(Long regioneId);

    List<Provincia> findAllByIsDeletedFalse();

    boolean existsBySigla(String sigla);
}
