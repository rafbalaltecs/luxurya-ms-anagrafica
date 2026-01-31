package anagrafica.repository.placer;

import anagrafica.entity.AgentZone;
import anagrafica.entity.PlacerZone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlacerZoneRepository extends JpaRepository<PlacerZone, Long> {
    @Query("SELECT p FROM PlacerZone p WHERE p.placer.id = :placerId AND p.isDeleted = false")
    List<PlacerZone> findAllZonesFromPlacerId(@Param("placerId") final Long placerId);

    @Query("SELECT c FROM PlacerZone c WHERE c.zone.id = :id AND c.isDeleted = false")
    List<PlacerZone> findAllPlacerZoneFromZone(@Param("id") Long id);

    @Query("SELECT c FROM PlacerZone c WHERE c.zone.id = :zoneId AND c.placer.id = :placerId AND c.isDeleted = false")
    Optional<PlacerZone> findPlacerZoneWithSameData(@Param("placerId") Long placerId, @Param("zoneId") Long zoneId);
}
