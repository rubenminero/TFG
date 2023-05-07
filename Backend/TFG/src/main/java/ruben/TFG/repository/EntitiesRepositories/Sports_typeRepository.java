package ruben.TFG.repository.EntitiesRepositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ruben.TFG.model.Entities.Sports_type;

import java.util.Optional;

@Repository
public interface Sports_typeRepository extends JpaRepository<Sports_type, Long> {

    Optional<Sports_type> findByName(String name);

    Optional<Sports_type> findById(Long id);
}