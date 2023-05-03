package ruben.TFG.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ruben.TFG.model.Inscription;
import ruben.TFG.model.Tournament;

import java.util.Optional;

@Repository
public interface InscriptionRepository extends JpaRepository<Inscription, Long> {
    Optional<Inscription> findById(Long id);
}