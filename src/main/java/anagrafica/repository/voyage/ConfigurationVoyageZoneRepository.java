package anagrafica.repository.voyage;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import anagrafica.entity.ConfigurationVoyageZone;

@Repository
public interface ConfigurationVoyageZoneRepository extends JpaRepository<ConfigurationVoyageZone, Long>{
	@Query("SELECT v FROM ConfigurationVoyageZone v WHERE v.voyage.id = :idVoyage AND v.isDeleted = false")
	public List<ConfigurationVoyageZone> findAllConfigurationVoyageZoneConfVoyageId(@Param("idVoyage") Long idVoyage);
	
	@Query("SELECT v FROM ConfigurationVoyageZone v WHERE v.voyage.id = :idVoyage AND v.zone.id = :idZone AND v.isDeleted = false")
	public List<ConfigurationVoyageZone> findAllConfigurationVoyageZoneConfVoyageIdAndZoneId(@Param("idVoyage") Long idVoyage, @Param("idZone") Long idZone);
}
