package anagrafica.repository.company;

import anagrafica.entity.Agent;
import anagrafica.entity.Company;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    @Query("SELECT c FROM Company c WHERE c.piva = :piva AND c.isDeleted = false")
    Optional<Company> findCompanyWithSamePiva(@Param("piva") String piva);

    @Query("SELECT c FROM Company c WHERE c.name = :name AND c.isDeleted = false")
    Optional<Company> findCompanyWithSameName(@Param("name") String name);

    @Query("SELECT c FROM Company c WHERE c.code = :code AND c.isDeleted = false")
    Optional<Company> findCompanyWithSameCode(@Param("code") String code);
}
