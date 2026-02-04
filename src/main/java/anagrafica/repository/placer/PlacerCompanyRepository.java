package anagrafica.repository.placer;

import anagrafica.entity.PlacerCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlacerCompanyRepository extends JpaRepository<PlacerCompany, Long> {
    @Query("SELECT p FROM PlacerCompany p WHERE p.company.id = :id AND p.isDeleted = false")
    List<PlacerCompany> findPlacerCompanyFromCompanyId(@Param("id") final Long id);

    @Query("SELECT p FROM PlacerCompany p WHERE p.placer.id = :id AND p.isDeleted = false")
    List<PlacerCompany> findPlacerCompanyFromPlacerId(@Param("id") final Long id);

    @Query("SELECT p FROM PlacerCompany p WHERE p.placer.id = :placerId AND p.company.id = :companyId")
    List<PlacerCompany> existPlacerCompany(@Param("placerId") Long placerId,
                                               @Param("companyId") Long companyId);

}
