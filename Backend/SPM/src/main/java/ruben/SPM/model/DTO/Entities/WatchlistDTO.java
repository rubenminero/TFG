package ruben.SPM.model.DTO.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ruben.SPM.model.Entities.Athlete;
import ruben.SPM.model.Entities.Tournament;
import ruben.SPM.model.Entities.Watchlist;

import java.util.List;
import java.util.stream.Collectors;

/**
 * DTO to manage the watchlist data.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WatchlistDTO {

    private Long id;
    private Long tournament;
    private Long athlete;
    private Boolean enabled;

    public WatchlistDTO(Watchlist watchlist) {
        this.id = watchlist.getId();
        this.tournament = watchlist.getTournament().getId();
        this.athlete = watchlist.getAthlete().getId();
        this.enabled = watchlist.isEnabled();
    }


    public static WatchlistDTO fromWatchlist(Watchlist watchlist) {
        return new WatchlistDTO(watchlist);
    }

    public static List<WatchlistDTO> fromWatchlists(List<Watchlist> watchlists) {
        return watchlists.stream().map(WatchlistDTO::fromWatchlist).collect(Collectors.toList());
    }

    public static Watchlist toWatchlist(WatchlistDTO watchlistDTO, Tournament tournament, Athlete athlete) {

        return new Watchlist(watchlistDTO,tournament,athlete);
    }
}