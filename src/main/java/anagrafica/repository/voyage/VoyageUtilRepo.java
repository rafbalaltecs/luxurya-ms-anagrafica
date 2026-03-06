package anagrafica.repository.voyage;

import java.util.Optional;

import org.springframework.stereotype.Component;

import anagrafica.entity.VoyageCompanyOperation;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class VoyageUtilRepo {
	
	private final EntityManager entityManager;
	
	public VoyageUtilRepo(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	public Optional<VoyageCompanyOperation> findLastOperation(Long voyageCompanyId) {

	    final TypedQuery<VoyageCompanyOperation> query = entityManager.createQuery(
	        "SELECT v FROM VoyageCompanyOperation v " +
	        "WHERE v.voyageCompany.id = :voyageCompanyId " +
	        "AND v.isDeleted = false " +
	        "ORDER BY v.createdAt DESC",
	        VoyageCompanyOperation.class
	    );

	    query.setParameter("voyageCompanyId", voyageCompanyId);
	    query.setMaxResults(1);

	    return query.getResultList().stream().findFirst();
	}
}
