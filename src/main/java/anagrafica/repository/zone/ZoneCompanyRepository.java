package anagrafica.repository.zone;

import anagrafica.entity.ZoneCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ZoneCompanyRepository extends JpaRepository<ZoneCompany, Long> {
    @Query("SELECT c FROM ZoneCompany c WHERE c.zone.id = :zoneId AND c.isDeleted = false")
    List<ZoneCompany> findAllCompanyFromZone(@Param("zoneId") Long zoneId);

    @Query("SELECT c FROM ZoneCompany c WHERE c.company.id = :companyId AND c.isDeleted = false")
    List<ZoneCompany> findZoneFromCompany(@Param("companyId") Long companyId);
    
}
