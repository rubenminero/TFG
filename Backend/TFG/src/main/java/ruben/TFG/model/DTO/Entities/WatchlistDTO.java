package ruben.TFG.model.DTO.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import ruben.TFG.model.Entities.Watchlist;

import java.util.List;
import java.util.stream.Collectors;

/**
 * DTO to manage the watchlist data.
 */
@Data
@AllArgsConstructor
public class WatchlistDTO {

    private Long id;
    private Long tournament;
    private Long user;
    private Boolean enabled;

    public WatchlistDTO(Long tournament, Long user) {
        this.tournament = tournament;
        this.user = user;
        this.enabled = true;
    }
    public WatchlistDTO(Watchlist watchlist) {
        this.id = watchlist.getId();
        this.tournament = watchlist.getTournament().getId();
        this.user = watchlist.getUser().getId();
        this.enabled = watchlist.isEnabled();
    }

    public WatchlistDTO() {

    }


    public static WatchlistDTO fromWatchlist(Watchlist watchlist) {
        return new WatchlistDTO(watchlist);
    }

    public static List<WatchlistDTO> fromWatchlists(List<Watchlist> watchlists) {
        return watchlists.stream().map(WatchlistDTO::fromWatchlist).collect(Collectors.toList());
    }

    public static Watchlist toWatchlist() {
        return new Watchlist();
    }
}