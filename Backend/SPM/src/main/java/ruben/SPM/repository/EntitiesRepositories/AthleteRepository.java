package ruben.SPM.repository.EntitiesRepositories;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.Optional;
import ruben.SPM.model.Entities.Athlete;
import ruben.SPM.model.Whitelist.Role;

@Repository
public interface AthleteRepository extends JpaRepository<Athlete, Long> {

    Optional<Athlete> findByUsername(String username);

    Optional<Athlete> findById(Long id);

    @Modifying
    @Transactional
    @Query("update Athlete a set  a.username = ?2, a.password = ?3, a.first_name = ?4, a.last_name = ?5, a.nif = ?6, a.email = ?7, a.role = ?8, a.disabled_at = ?9, a.enabled = ?10, a.phone_number = ?11 where a.id = ?1")
    void update(Long id,
            String username,
            String password,
            String first_name,
            String last_name,
            String nif,
            String email,
            Role role,
            Date disabled_at,
            Boolean enabled,
            String phone_number);
}