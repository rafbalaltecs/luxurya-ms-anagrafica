package anagrafica.repository.zone;

import anagrafica.entity.ZoneCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ZoneCompanyRepository extends JpaRepository<ZoneCompany, Long> {
}
