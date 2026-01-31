package anagrafica.repository.placer;

import anagrafica.entity.Placer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlaceRepository extends JpaRepository<Placer, Long> {

    @Query("SELECT p FROM Placer p where p.user.id = :userId AND p.isDeleted = false")
    Optional<Placer> findPlacerWithUserId(@Param("userId") Long userId);

}
