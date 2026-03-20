package anagrafica.repository.zone;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import anagrafica.entity.Zone;

@Repository
public interface ZoneRepository extends JpaRepository<Zone, Long> {
    @Query("SELECT z FROM Zone z WHERE z.name = :name and z.isDeleted = false")
    public Optional<Zone> existWithName(@Param("name") String name);
    
    @Query("SELECT z FROM Zone z WHERE z.isDeleted = false")
    public Page<Zone> findAllNotDeleted(Pageable pageble);
    
    @Query("SELECT z FROM Zone z WHERE z.isDeleted = false AND z.lat is null")
    public Page<Zone> findAllNotDeletedWithNotCoordinate(Pageable pageble);
    
    @Query("SELECT z FROM Zone z WHERE z.isDeleted = false AND (:name IS NULL OR LOWER(z.name) LIKE LOWER(CONCAT('%', :name, '%')))")
    public Page<Zone> searchAllNotDeleted(@Param("name") String name, Pageable pageable);
    
}
