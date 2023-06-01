package ruben.SPM.repository.EntitiesRepositories;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ruben.SPM.model.Entities.Organizer;
import ruben.SPM.model.Whitelist.Role;

import java.util.Date;
import java.util.Optional;

@Repository
public interface OrganizerRepository extends JpaRepository<Organizer, Long> {

    Organizer findByUsername(String username);

    Optional<Organizer> findById(Long id);

    @Modifying
    @Transactional
    @Query("update Organizer a set a.username = ?2, a.password = ?3, a.first_name = ?4, a.last_name = ?5, a.nif = ?6, a.email = ?7, a.role = ?8, a.address = ?9, a.company_name = ?10, a.disabled_at = ?11, a.enabled = ?12 where a.id = ?1")
    void update(Long id,
            String username,
            String password,
            String first_name,
            String last_name,
            String nif,
            String email,
            Role role,
            String address,
            String company_name,
            Date disabled_at,
            Boolean enabled);
}