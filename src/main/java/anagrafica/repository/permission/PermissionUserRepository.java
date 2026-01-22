package anagrafica.repository.permission;

import anagrafica.entity.Permission;
import anagrafica.entity.PermissionUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionUserRepository extends JpaRepository<PermissionUser, Long> {
    @Query("SELECT u FROM PermissionUser u WHERE u.user.id = :id AND u.isDeleted = false ")
    List<PermissionUser> findByUserId(
            @Param("id") final Long id);
}
