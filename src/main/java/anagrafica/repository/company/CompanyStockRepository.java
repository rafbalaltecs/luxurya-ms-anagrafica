package anagrafica.repository.company;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import anagrafica.entity.CompanyStock;

@Repository
public interface CompanyStockRepository extends JpaRepository<CompanyStock, Long>{

	@Query("SELECT c FROM CompanyStock c WHERE c.isDeleted = false")
    Page<CompanyStock> findAllNotDeleted(Pageable pageable);
	
	@Query("SELECT c FROM CompanyStock c WHERE c.company.id = :companyId AND c.productIdExt = :productId AND c.isDeleted = false")
    Optional<CompanyStock> findCompanyStockFromCompanyAndProduct(@Param("companyId") Long companyId, @Param("productId") Long productId);
	
	@Query("SELECT c FROM CompanyStock c WHERE c.company.id = :companyId AND c.isDeleted = false")
    List<CompanyStock> findAllStockFromCompanyId(@Param("companyId") Long companyId);
	
}
