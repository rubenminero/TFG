package ruben.SPM.model.DTO.Front;

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
public class WatchlistUserFrontDTO {
    private Long id;
    private String athlete;
    private Long athlete_id;
    private Boolean enabled;
    private Long tournament_id;
    private String tournament_name;
    private String tournament_location;
    private String tournament_address;
    private String tournament_description;
    private Boolean tournament_enabled;
    private Boolean tournament_inscription;
    private int tournament_capacity;
    private String tournament_organizer;
    private String tournament_sport_type;

    public WatchlistUserFrontDTO(Watchlist watchlist) {
        this.id = watchlist.getId();
        this.athlete = watchlist.getAthlete().getUsername();
        this.athlete_id = watchlist.getAthlete().getId();
        this.enabled = watchlist.isEnabled();
        this.tournament_id = watchlist.getTournament().getId();
        this.tournament_name = watchlist.getTournament().getName();
        this.tournament_location = watchlist.getTournament().getLocation();
        this.tournament_address = watchlist.getTournament().getAddress();
        this.tournament_description = watchlist.getTournament().getDescription();
        this.tournament_enabled = watchlist.getTournament().isEnabled();
        this.tournament_inscription = watchlist.getTournament().getInscription();
        this.tournament_capacity = watchlist.getTournament().getCapacity();
        this.tournament_organizer = watchlist.getTournament().getOrganizer().getCompany();
        this.tournament_sport_type = watchlist.getTournament().getSport_type().getName();
    }


    public static WatchlistUserFrontDTO fromWatchlist(Watchlist watchlist) {
        return new WatchlistUserFrontDTO(watchlist);
    }

    public static List<WatchlistUserFrontDTO> fromWatchlists(List<Watchlist> watchlists) {
        return watchlists.stream().map(WatchlistUserFrontDTO::fromWatchlist).collect(Collectors.toList());
    }

    public static Watchlist toWatchlist(WatchlistUserFrontDTO watchlistFrontDTO, Tournament tournament, Athlete athlete) {

        return new Watchlist(watchlistFrontDTO,tournament,athlete);
    }
}