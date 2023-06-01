package ruben.SPM.model.DTO.Front;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ruben.SPM.model.Entities.Athlete;
import ruben.SPM.model.Entities.Inscription;
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
public class WatchlistFrontDTO {
    private Long id;
    private String tournament;
    private String athlete;
    private Long tournament_id;
    private Long athlete_id;
    private Boolean enabled;

    public WatchlistFrontDTO(Watchlist watchlist) {
        this.id = watchlist.getId();
        this.tournament = watchlist.getTournament().getName();
        this.athlete = watchlist.getAthlete().getUsername();
        this.tournament_id = watchlist.getTournament().getId();
        this.athlete_id = watchlist.getAthlete().getId();
        this.enabled = watchlist.isEnabled();
    }


    public static WatchlistFrontDTO fromWatchlist(Watchlist watchlist) {
        return new WatchlistFrontDTO(watchlist);
    }

    public static List<WatchlistFrontDTO> fromWatchlists(List<Watchlist> watchlists) {
        return watchlists.stream().map(WatchlistFrontDTO::fromWatchlist).collect(Collectors.toList());
    }

    public static Watchlist toWatchlist(WatchlistFrontDTO watchlistFrontDTO, Tournament tournament, Athlete athlete) {

        return new Watchlist(watchlistFrontDTO,tournament,athlete);
    }
}