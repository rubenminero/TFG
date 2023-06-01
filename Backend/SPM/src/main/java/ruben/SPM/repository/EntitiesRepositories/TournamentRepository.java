package ruben.SPM.repository.EntitiesRepositories;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ruben.SPM.model.Entities.Organizer;
import ruben.SPM.model.Entities.Sports_type;
import ruben.SPM.model.Entities.Tournament;

import java.util.Optional;

@Repository
public interface TournamentRepository extends JpaRepository<Tournament, Long> {

    Optional<Tournament> findByName(String name);

    Optional<Tournament> findById(Long id);

    @Modifying
    @Transactional
    @Query("update Tournament a set  a.address = ?2, a.description = ?3, a.inscription = ?4, a.capacity = ?5, a.enabled = ?6, a.location = ?7, a.name = ?8, a.organizer = ?9, a.sport_type = ?10 where a.id = ?1")
    void update(Long id,
                String address,
                String description,
                Boolean inscription,
                Integer capacity,
                Boolean enabled,
                String location,
                String name,
                Organizer organizers,
                Sports_type sportsType);
}