package ruben.TFG.service;

import org.springframework.stereotype.Service;
import ruben.TFG.model.Inscription;
import ruben.TFG.model.Watchlist;
import ruben.TFG.repository.InscriptionRepository;
import ruben.TFG.repository.WatchlistRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Service for Watchlist class.
 */
@Service
public class WatchlistService {

    /**
     * Access for Watchlist data.
     */
    private final WatchlistRepository watchlistRepository;

    public WatchlistService(WatchlistRepository watchlistRepository) {

        this.watchlistRepository = watchlistRepository;
    }

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
     * @param inscription the watchlist to be saved.
     */
    public Watchlist saveWatchlist(Watchlist watchlist){

        return watchlistRepository.save(watchlist);
    }

    /**
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
            if (w.isEnabled() && w.getUser().getId() == id){
                watchlists_enabled.add(w);
            }
        }
        return watchlists_enabled;
    }


    }
