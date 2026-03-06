package anagrafica.repository.agent;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import anagrafica.entity.AgentCurrentVoyage;

@Repository
public interface AgentCurrentVoyageRepository extends JpaRepository<AgentCurrentVoyage, Long>{
	@Query("SELECT a FROM AgentCurrentVoyage a WHERE a.agent.id = :idAgent AND a.isDeleted = false")
	List<AgentCurrentVoyage> findCurrentVoyageFromAgentId(@Param("idAgent") Long idAgent);
}
