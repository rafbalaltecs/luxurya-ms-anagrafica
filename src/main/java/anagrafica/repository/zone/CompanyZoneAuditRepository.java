package anagrafica.repository.zone;

import anagrafica.entity.audit.CompanyZoneAudit;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyZoneAuditRepository extends MongoRepository<CompanyZoneAudit, String> {
}
