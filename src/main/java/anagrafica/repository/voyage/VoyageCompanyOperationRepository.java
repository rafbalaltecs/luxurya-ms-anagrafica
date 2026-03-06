package anagrafica.repository.voyage;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import anagrafica.entity.VoyageCompanyOperation;

@Repository
public interface VoyageCompanyOperationRepository extends JpaRepository<VoyageCompanyOperation, Long>{
	@Query("SELECT v FROM VoyageCompanyOperation v WHERE v.voyageCompany.id = :voyageCompanyId AND v.isDeleted = FALSE")
	public List<VoyageCompanyOperation> findAllOperationFromVoyageCompanyId(@Param("voyageCompanyId") final Long voyageCompanyId);

}
