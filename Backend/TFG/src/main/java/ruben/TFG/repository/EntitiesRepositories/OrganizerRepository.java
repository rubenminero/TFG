package ruben.TFG.repository.EntitiesRepositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ruben.TFG.model.Entities.Organizer;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrganizerRepository extends JpaRepository<Organizer, Long> {

    Organizer findByUsername(String username);

    Optional<Organizer> findById(Long id);

}