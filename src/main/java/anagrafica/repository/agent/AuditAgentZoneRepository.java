package anagrafica.repository.agent;

import anagrafica.entity.audit.AgentZoneAudit;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditAgentZoneRepository extends MongoRepository<AgentZoneAudit, String> {
}
