package anagrafica.repository.company;

import anagrafica.entity.CompanyAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyAddressRepository extends JpaRepository<CompanyAddress, Long> {
    @Query("SELECT a FROM CompanyAddress a WHERE a.company.id = :id AND a.isDeleted = false")
    public List<CompanyAddress> findAllCompanyAddressFromCompanyId(@Param("id") final Long id);

}
