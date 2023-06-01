package ruben.SPM.repository.EntitiesRepositories;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ruben.SPM.model.Entities.User;
import ruben.SPM.model.Whitelist.Role;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findById(Long id);

    @Modifying
    @Transactional
    @Query("update User a set  a.username = ?2, a.password = ?3, a.first_name = ?4, a.last_name = ?5, a.nif = ?6, a.email = ?7, a.role = ?8 where a.id = ?1")
    void update(Long id,
            String username,
            String password,
            String first_name,
            String last_name,
            String nif,
            String email,
            Role role);
}
