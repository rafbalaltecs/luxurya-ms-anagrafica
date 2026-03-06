package anagrafica.repository.agent;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import anagrafica.entity.Agent;
import io.lettuce.core.dynamic.annotation.Param;

@Repository
public interface AgentRepository extends JpaRepository<Agent, Long> {
    @Query("SELECT c FROM Agent c WHERE c.user.id = :id AND c.isDeleted = false")
    Optional<Agent> findAgentFromUserId(@Param("id") Long id);
}
