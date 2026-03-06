package anagrafica.repository.voyage;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import anagrafica.entity.TypeDocumentVoyage;

@Repository
public interface TypeDocumentVoyageRepository extends JpaRepository<TypeDocumentVoyage, Long> {
	@Query("SELECT t FROM TypeDocumentVoyage t WHERE t.isDeleted = false")
	List<TypeDocumentVoyage> findAllNotDeleted();
}
