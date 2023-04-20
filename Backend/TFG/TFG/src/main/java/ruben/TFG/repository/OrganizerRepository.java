package ruben.TFG.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ruben.TFG.model.Organizer;

import java.util.Optional;

@Repository
public interface OrganizerRepository extends JpaRepository<Organizer, Long> {

    Optional<Organizer> findByUsername(String username);

    Optional<Organizer> findById(Long id);
}