package anagrafica.repository.user;

import anagrafica.entity.TypeUser;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeUserRepository extends JpaRepository<TypeUser, Long> {
	  @Query("SELECT t FROM TypeUser t WHERE t.code = :code AND t.isDeleted = false")
	  Optional<TypeUser> findByCode(@Param("code") String code);
	  
	  @Query("SELECT t FROM TypeUser t WHERE t.isDeleted = false")
	  List<TypeUser> findAllNotDeleted();
}
