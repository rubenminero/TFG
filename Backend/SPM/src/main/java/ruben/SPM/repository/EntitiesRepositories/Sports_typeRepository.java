package ruben.SPM.repository.EntitiesRepositories;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ruben.SPM.model.Entities.Sports_type;

import java.util.Optional;

@Repository
public interface Sports_typeRepository extends JpaRepository<Sports_type, Long> {

    Optional<Sports_type> findByName(String name);

    Optional<Sports_type> findById(Long id);

    @Modifying
    @Transactional
    @Query("update Sports_type a set  a.enabled = ?2, a.name = ?3 where a.id = ?1")
    void update(Long id,
            Boolean enabled,
            String name);
}