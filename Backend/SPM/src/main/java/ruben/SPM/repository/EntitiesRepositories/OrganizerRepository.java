package ruben.SPM.repository.EntitiesRepositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ruben.SPM.model.Entities.Organizer;

import java.util.Optional;

@Repository
public interface OrganizerRepository extends JpaRepository<Organizer, Long> {

    Organizer findByUsername(String username);

    Optional<Organizer> findById(Long id);

}