package anagrafica.repository.agent;

import anagrafica.entity.AgentZone;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AgentZoneRepository extends JpaRepository<AgentZone, Long> {
    @Query("SELECT c FROM AgentZone c WHERE c.agent.id = :id AND c.isDeleted = false AND c.isActive = true")
    List<AgentZone> findAllZonesFromAgentId(@Param("id") Long id);

    @Query("SELECT c FROM AgentZone c WHERE c.zone.id = :id AND c.isDeleted = false")
    List<AgentZone> findAllZoneWithIdAndAgents(@Param("id") Long id);
}
