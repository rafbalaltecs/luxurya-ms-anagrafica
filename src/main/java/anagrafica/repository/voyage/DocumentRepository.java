package anagrafica.repository.voyage;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import anagrafica.entity.Document;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long>{
	@Query("SELECT d FROM Document d WHERE d.id = :id AND d.isDeleted = false")
	public Optional<Document> findByIdNotDeleted(@Param("id") Long id);
}
