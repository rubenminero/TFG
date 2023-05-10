package ruben.TFG.repository.EntitiesRepositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ruben.TFG.model.Entities.Tournament;

import java.util.Optional;

@Repository
public interface TournamentRepository extends JpaRepository<Tournament, Long> {

    Optional<Tournament> findByName(String name);

    Optional<Tournament> findById(Long id);
}