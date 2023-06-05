package ruben.SPM.repository.EntitiesRepositories;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ruben.SPM.model.Entities.Athlete;
import ruben.SPM.model.Entities.Tournament;
import ruben.SPM.model.Entities.Watchlist;

import java.util.Optional;

@Repository
public interface WatchlistRepository extends JpaRepository<Watchlist, Long> {
    Optional<Watchlist> findById(Long id);

    @Modifying
    @Transactional
    @Query("update Watchlist a set  a.enabled = ?2, a.tournament = ?3, a.athlete = ?4 where a.id = ?1")
    void update(Long id,
            Boolean enabled,
            Tournament tournament,
            Athlete athlete);
}