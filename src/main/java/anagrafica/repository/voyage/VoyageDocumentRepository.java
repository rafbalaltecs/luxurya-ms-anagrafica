package anagrafica.repository.voyage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import anagrafica.entity.VoyageDocument;

@Repository
public interface VoyageDocumentRepository extends JpaRepository<VoyageDocument, Long>{

}
