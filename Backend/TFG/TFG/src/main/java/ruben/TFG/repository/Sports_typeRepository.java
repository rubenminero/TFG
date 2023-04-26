package ruben.TFG.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ruben.TFG.model.Sports_type;
import ruben.TFG.model.Tournament;

import java.util.Optional;

@Repository
public interface Sports_typeRepository extends JpaRepository<Sports_type, Long> {

    Optional<Sports_type> findByName(String name);

    Optional<Sports_type> findById(Long id);
}