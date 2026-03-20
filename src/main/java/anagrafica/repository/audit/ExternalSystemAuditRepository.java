package anagrafica.repository.audit;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import anagrafica.entity.audit.ExternalSystemAudit;

@Repository
public interface ExternalSystemAuditRepository extends MongoRepository<ExternalSystemAudit, String>{
	
	List<ExternalSystemAudit> findByIdUser(Long idUser);
	
    Page<ExternalSystemAudit> findByIdUser(Long idUser, Pageable pageable);
	
	@Query("{ 'idUser': ?0, 'requestDate': { $gte: ?1, $lte: ?2 } }")
	List<ExternalSystemAudit> findByIdUserAndDateRange(Long idUser, LocalDateTime from, LocalDateTime to);

}
