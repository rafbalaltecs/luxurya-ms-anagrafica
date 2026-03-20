package anagrafica.repository.company;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import anagrafica.entity.TypeCompany;

@Repository
public interface TypeCompanyRepository extends JpaRepository<TypeCompany, Long>{

	@Query("SELECT t FROM TypeCompany t WHERE t.isDeleted = false")
	List<TypeCompany> findAllNotDeleted();
	
}
