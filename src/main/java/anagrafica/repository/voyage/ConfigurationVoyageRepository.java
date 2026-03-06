package anagrafica.repository.voyage;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import anagrafica.entity.ConfigurationVoyage;

@Repository
public interface ConfigurationVoyageRepository extends JpaRepository<ConfigurationVoyage, Long>{
	@Query("SELECT v FROM ConfigurationVoyage v WHERE v.agent.id = :idAgent AND v.isDeleted = false")
	public List<ConfigurationVoyage> findAllConfigurationVoyageFromAgentId(@Param("idAgent") Long idAgent);
	
	@Query("SELECT v FROM ConfigurationVoyage v WHERE v.agent.id = :idAgent AND v.week = :week AND v.isDeleted = false")
	public List<ConfigurationVoyage> findAllConfigurationVoyageFromAgentIdAndWeek(@Param("idAgent") Long idAgent, @Param("week") Integer week);
}
