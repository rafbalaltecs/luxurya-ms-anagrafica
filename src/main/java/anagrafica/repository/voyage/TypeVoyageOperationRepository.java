package anagrafica.repository.voyage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import anagrafica.entity.TypeVoyageOperation;

@Repository
public interface TypeVoyageOperationRepository extends JpaRepository<TypeVoyageOperation, Long> {

}
