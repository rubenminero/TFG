package ruben.SPM.service.EntitiesServices;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ruben.SPM.model.Entities.Athlete;
import ruben.SPM.model.Entities.Inscription;
import ruben.SPM.model.Entities.Tournament;
import ruben.SPM.model.Entities.Watchlist;
import ruben.SPM.repository.EntitiesRepositories.WatchlistRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Service for Watchlist class.
 */
@Service
@AllArgsConstructor
public class WatchlistService {

    /**
     * Access for Watchlist data.
     */
    private final WatchlistRepository watchlistRepository;
    private final TournamentService tournamentService;

    /**
     * Recover a watchlist from the database.
     * 
     * @param id the id of the watchlist.
     * @return watchlist the watchlist with the id.
     */
    public Watchlist getWatchList(Long id) {
        return watchlistRepository.findById(id).orElse(null);
    }

    /**
     * Save a watchlist in the database.
     * 
     * @param watchlist the watchlist to be saved.
     */
    public Watchlist saveWatchlist(Watchlist watchlist) {
        this.tournamentService.lessCapacity(watchlist.getTournament());
        return watchlistRepository.save(watchlist);
    }

    /**
     * Update a watchlist in the database.
     * 
     * @param watchlist the watchlist to be updated.
     */
    public Watchlist updateWatchlist(Watchlist watchlist) {

        watchlistRepository.update(watchlist.getId(),
                watchlist.isEnabled(),
                watchlist.getTournament(),
                watchlist.getAthlete());
        return watchlist;
    }

    /**
     * Delete a watchlist from the database.
     * 
     * @param id the id of the watchlist.
     */
    public void deleteWatchlist(Long id) {
        Watchlist watchlist = this.getWatchList(id);
        this.tournamentService.moreCapacity(watchlist.getTournament());
        this.watchlistRepository.delete(watchlist);
    }

    /**
     * Disable or enable a watchlist in the database,depends on the last state.
     * 
     * @param id the id of the watchlist to be changed.
     */
    public void changeStateWatchlist(Long id) {
        Watchlist watchlist = this.getWatchList(id);
        watchlist.setEnabled(!watchlist.isEnabled());
        if (!watchlist.isEnabled()){
            this.tournamentService.moreCapacity(watchlist.getTournament());
        }else{
            this.tournamentService.lessCapacity(watchlist.getTournament());
        }
        watchlistRepository.save(watchlist);
    }

    /**
     * Gets all the watchlists from the database.
     * Only for admins.
     * 
     * @return A list with all the inscriptions.
     */
    public List<Watchlist> getAllWatchlists() {

        return watchlistRepository.findAll();
    }

    /**
     * Gets all the enabled watchlists for a user.ç
     * 
     * @param id the id for the user.
     * @return A list with all the watchlists for this user.
     */
    public List<Watchlist> getEnabledWatchlists(Long id) {
        List<Watchlist> watchlists_enabled = new ArrayList<Watchlist>();
        List<Watchlist> watchlists = this.getAllWatchlists();
        for (Watchlist w : watchlists) {
            if (w.isEnabled() && w.getAthlete().getId() == id) {
                watchlists_enabled.add(w);
            }
        }
        return watchlists_enabled;
    }

    /**
     * Checks if the watchlist is valid.
     * 
     * @param watchlist the watchlist.
     * @return true if is valid, false otherwise.
     */
    public Boolean isValid(Watchlist watchlist) {
        List<Watchlist> watchlists = this.getAllWatchlists();
        for (Watchlist w : watchlists) {
            if (w.getTournament().getId() == watchlist.getTournament().getId()
                    && w.getAthlete().getId() == watchlist.getAthlete().getId()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if the watchlist is valid to be saved.
     * @return true if is valid, false otherwise.
     */
    public Boolean validWatchlist(Athlete athlete, Tournament tournament) {
        List<Watchlist> watchlists = this.getAllWatchlists();
        for (Watchlist w : watchlists) {
            if (athlete.getId() == w.getAthlete().getId() && tournament.getId() == w.getTournament().getId()) {
                return true;
            }
        }
        return false;
    }
}
