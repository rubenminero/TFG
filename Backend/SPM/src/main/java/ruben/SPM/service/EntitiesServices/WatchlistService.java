package ruben.SPM.service.EntitiesServices;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
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

    private final DeleteService deleteService;


    /**
     * Recover a watchlist from the database.
     * @param id the id of the watchlist.
     * @return watchlist the watchlist with the id.
     */
    public Watchlist getWatchList(Long id){
        return watchlistRepository.findById(id).orElse(null);
    }

    /**
     * Save a watchlist in the database.
     * @param watchlist the watchlist to be saved.
     */
    public Watchlist saveWatchlist(Watchlist watchlist){

        return watchlistRepository.save(watchlist);
    }

    /**
<<<<<<< Updated upstream
=======
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
     * @param id the id of the watchlist.
     */
    public void deleteWatchlist(Long id) {
        this.watchlistRepository.delete(this.getWatchList(id));
    }

    /**
>>>>>>> Stashed changes
     * Disable or enable a watchlist in the database,depends on the last state.
     * @param id the id of the watchlist to be changed.
     */
    public void changeStateWatchlist(Long id){
        Watchlist watchlist = this.getWatchList(id);
        watchlist.setEnabled(!watchlist.isEnabled());
        watchlistRepository.save(watchlist);
    }

    /**
     * Gets all the watchlists from the database.
     * Only for admins.
     * @return A list with all the inscriptions.
     */
    public List<Watchlist> getAllWatchlists() {

        return watchlistRepository.findAll();
    }
    /**
     * Gets all the enabled watchlists for a user.ç
     * @param id the id for the user.
     * @return A list with all the watchlists for this user.
     */
    public List<Watchlist> getEnabledWatchlists(Long id) {
        List<Watchlist> watchlists_enabled = new ArrayList<Watchlist>();
        List<Watchlist> watchlists = this.getAllWatchlists();
        for (Watchlist w : watchlists) {
            if (w.isEnabled() && w.getAthlete().getId() == id){
                watchlists_enabled.add(w);
            }
        }
        return watchlists_enabled;
    }


<<<<<<< Updated upstream
=======


    /**
     * Delete watchlists from the database.
     * @param id the id of the user for delete.
     */
    public void deleteWatchlistsUser(Long id) {

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
>>>>>>> Stashed changes
    }
