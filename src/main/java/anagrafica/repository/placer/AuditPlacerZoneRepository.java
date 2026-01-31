package anagrafica.repository.placer;

import anagrafica.entity.audit.PlacerZoneAudit;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditPlacerZoneRepository extends MongoRepository<PlacerZoneAudit, String> {
}
