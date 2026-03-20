package anagrafica.repository.externalsystem;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import anagrafica.entity.ExternalSystem;

@Repository
public interface ExternalSystemRepository extends JpaRepository<ExternalSystem, Long>{

	@Query("SELECT e FROM ExternalSystem e WHERE e.user.id = :idUser")
	public List<ExternalSystem> findByUserId(@Param("idUser") Long idUser);
	
}
