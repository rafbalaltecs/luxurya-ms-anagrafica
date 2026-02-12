package anagrafica.repository.voyage;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import anagrafica.entity.Voyage;

@Repository
public interface VoyageRepository extends JpaRepository<Voyage, Long> {
	
	@Query("SELECT c FROM Voyage c WHERE c.isDeleted = false AND c.isFinished = false")
    Page<Voyage> findAllNotDeleted(Pageable pageable);
	
	@Query("SELECT c FROM Voyage c WHERE c.agent.id = :id AND c.isDeleted = false AND c.isFinished = false")
	List<Voyage> findPresentVoyageFromAgentId(@Param("id") Long id);
	
	@Query("SELECT c FROM Voyage c WHERE c.code = :code AND c.isDeleted = false AND c.isFinished = false")
	Optional<Voyage> findVoyageByCode(@Param("code") Long code);
	
	@Query("SELECT c FROM Voyage c WHERE c.agent.id = :agentId AND c.zone.id = :zoneId AND c.startDate >= :startDate AND c.endDate <= :endDate AND c.isDeleted = false AND c.isFinished = false")
	List<Voyage> findAllVoyageFromExistAgentAndZoneWeek(
			@Param("agentId") Long agentId,
			@Param("zoneId") Long zoneId,
			@Param("startDate") LocalDate startDate,
			@Param("endDate") LocalDate endDate
			);

}
