package anagrafica.repository.geography;

import anagrafica.entity.Citta;
import anagrafica.entity.Provincia;
import anagrafica.entity.Regione;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CittaRepository extends JpaRepository<Citta, Long> {
    Optional<Citta> findByCodice(String codice);

    List<Citta> findByNomeContainingIgnoreCase(String nome);

    List<Citta> findByProvincia(Provincia provincia);

    List<Citta> findByProvinciaId(Long provinciaId);

    List<Citta> findByRegione(Regione regione);

    List<Citta> findByRegioneId(Long regioneId);

    List<Citta> findByCap(String cap);

    List<Citta> findAllByIsDeletedFalse();

    @Query("SELECT c FROM Citta c WHERE c.provincia.sigla = :sigla AND c.isDeleted = false")
    List<Citta> findByProvinciaSigla(@Param("sigla") String sigla);

    @Query("SELECT c FROM Citta c WHERE c.regione.codice = :codice AND c.isDeleted = false")
    List<Citta> findByRegioneCodice(@Param("codice") String codice);

    @Query("SELECT c FROM Citta c WHERE LOWER(c.nome) LIKE LOWER(CONCAT('%', :nome, '%')) AND c.isDeleted = false")
    List<Citta> searchByNome(@Param("nome") String nome);
}
