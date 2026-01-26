package anagrafica.repository.zone;

import anagrafica.entity.Zone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ZoneRepository extends JpaRepository<Zone, Long> {
    @Query("SELECT z FROM Zone z WHERE z.name = :name and z.isDeleted = false")
    public Optional<Zone> existWithName(@Param("name") String name);
}
