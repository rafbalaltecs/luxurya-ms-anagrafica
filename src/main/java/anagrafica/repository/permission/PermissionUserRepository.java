package anagrafica.repository.permission;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import anagrafica.entity.PermissionUser;

@Repository
public interface PermissionUserRepository extends JpaRepository<PermissionUser, Long> {
    @Query("SELECT u FROM PermissionUser u WHERE u.user.id = :id AND u.isDeleted = false ")
    List<PermissionUser> findByUserId(
            @Param("id") final Long id);
    
    
}
