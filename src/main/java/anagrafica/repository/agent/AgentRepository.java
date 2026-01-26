package anagrafica.repository.agent;

import anagrafica.entity.Agent;
import anagrafica.entity.AgentZone;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AgentRepository extends JpaRepository<Agent, Long> {
    @Query("SELECT c FROM Agent c WHERE c.user.id = :id AND c.isDeleted = false")
    Optional<Agent> findAgentFromUserId(@Param("id") Long id);
}
