package anagrafica.repository.payment;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import anagrafica.entity.TypePayment;

@Repository
public interface TypePaymentRepository extends JpaRepository<TypePayment, Long> {

	@Query("SELECT t FROM TypePayment t WHERE t.isDeleted = false")
	List<TypePayment> findAllNotDeleted();
	
	
	@Query("SELECT t FROM TypePayment t WHERE t.code = :code AND t.isDeleted = false")
	TypePayment findCodeNotDeleted(@Param("code") String code);
}
