package anagrafica.repository.agent;

import anagrafica.entity.AgentZone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AgentZoneRepository extends JpaRepository<AgentZone, Long> {
    @Query("SELECT c FROM AgentZone c WHERE c.agent.id = :id AND c.isDeleted = false AND c.isActive = true")
    List<AgentZone> findAllZonesFromAgentId(@Param("id") Long id);

    @Query("SELECT c FROM AgentZone c WHERE c.zone.id = :id AND c.isDeleted = false")
    List<AgentZone> findAllZoneWithIdZoneAndAgents(@Param("id") Long id);

    @Query("SELECT c FROM AgentZone c WHERE c.zone.id = :zoneId AND c.agent.id = :agentId AND c.isDeleted = false")
    Optional<AgentZone> findAgentZoneWithSameData(@Param("agentId") Long agentId, @Param("zoneId") Long zoneId);
}
