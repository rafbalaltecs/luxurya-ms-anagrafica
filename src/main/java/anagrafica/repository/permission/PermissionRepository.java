package anagrafica.repository.permission;

import anagrafica.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {
    @Query("SELECT u FROM Permission u WHERE u.typeUser.id = :id AND u.isDeleted = false ")
    List<Permission> findPermissionFromTypeUserId(
            @Param("id") final Long id);
    
    @Query("SELECT u FROM Permission u WHERE u.isDeleted = false ")
    List<Permission> findAllNotDeleted();
    
    @Query("SELECT u FROM Permission u WHERE u.code = :code AND u.isDeleted = false ")
    Optional<Permission> findPermissionFromCode(
            @Param("code") final String code);
}
