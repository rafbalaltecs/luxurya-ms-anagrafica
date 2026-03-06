package anagrafica.repository.voyage;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import anagrafica.entity.VoyageCompany;

@Repository
public interface VoyageCompanyRepository extends JpaRepository<VoyageCompany, Long>{
	@Query("SELECT v FROM VoyageCompany v WHERE v.company.id = :idCompany AND v.voyage.id = :idVoyage AND v.isDeleted = false and v.isCompleted = false")
	public List<VoyageCompany> findAllVoyageCompanyWithCompanyAndVoyage(
			@Param("idCompany") Long idCompany,
			@Param("idVoyage") Long idVoyage
			);
	
	@Query("SELECT v FROM VoyageCompany v WHERE v.agent.id = :idAgent AND v.isDeleted = false")
	public List<VoyageCompany> findAllVoyageCompanyFromAgentId(
			@Param("idAgent") Long idAgent,
			Pageable pageable
			);
	
	@Query("SELECT v FROM VoyageCompany v WHERE v.voyage.id = :voyageId AND v.isDeleted = false")
	public List<VoyageCompany> findAllVoyageCompanyFromVoyageId(
			@Param("voyageId") Long voyageId,
			Pageable pageable
			);
	
	@Query("SELECT v FROM VoyageCompany v WHERE v.voyage.id = :voyageId AND v.isExternal = true AND v.isDeleted = false")
	public List<VoyageCompany> findAllVoyageCompanyFromVoyageIdAndExternal(
			@Param("voyageId") Long voyageId
			);
	
	
	@Query("SELECT v FROM VoyageCompany v WHERE v.company.id = :idCompany AND v.isDeleted = false and v.isCompleted = false")
	public List<VoyageCompany> findAllVoyageCompanyFromCompanyId(
			@Param("idCompany") Long idAgent
			);
	
	@Query("SELECT v FROM VoyageCompany v WHERE v.company.id = :idCompany AND v.agent.id = :idAgent AND v.isDeleted = false and v.isCompleted = false")
	public List<VoyageCompany> findAllVoyageCompanyFromCompanyIdAndAgent(
			@Param("idCompany") Long idCompany,
			@Param("idAgent") Long idAgent
			);
	
}
