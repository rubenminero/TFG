package ruben.TFG.repository.EntitiesRepositories;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import ruben.TFG.model.Entities.Athlete;

@Repository
public interface AthleteRepository extends JpaRepository<Athlete, Long> {

    Optional<Athlete> findByUsername(String username);

    Optional<Athlete> findById(Long id);
}