package ruben.TFG.controllers.EntitiesControllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ruben.TFG.model.DTO.WatchlistDTO;
import ruben.TFG.model.Entities.Athlete;
import ruben.TFG.model.Entities.Tournament;
import ruben.TFG.model.Entities.Watchlist;
import ruben.TFG.service.EntitiesServices.TournamentService;
import ruben.TFG.service.EntitiesServices.AthleteService;
import ruben.TFG.service.EntitiesServices.WatchlistService;

@RestController
@Slf4j
@RequestMapping("/api/watchlists")
public class WatchlistController {

    private final WatchlistService watchlistService;
    private final TournamentService tournamentService;
    private final AthleteService athleteService;
    /**
     * Constructor of the class
     * @param watchlistService, the service to manage the inscription's data
     * @param tournamentService, the service to manage the torunament's data
     * @param athleteService, the service to manage the user's data
     */
    public WatchlistController(WatchlistService watchlistService, TournamentService tournamentService, AthleteService athleteService) {
        this.watchlistService = watchlistService;
        this.athleteService = athleteService;
        this.tournamentService = tournamentService;
    }



    @GetMapping("/{id}")
    public ResponseEntity<WatchlistDTO> getWatchlistById(@PathVariable(name = "id") Long id) {

        Watchlist watchlist = watchlistService.getWatchList(id);
        if (watchlist != null) {
            log.info("The watchlist has been found");
            return ResponseEntity.ok(new WatchlistDTO(watchlist));
        }
        else {
            log.warn("The watchlist by id has not been found");
            return ResponseEntity.notFound().build();
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<WatchlistDTO> updateWatchlist(@PathVariable Long id, @RequestBody Watchlist watchlist) {
        if (!id.equals(watchlist.getId())) {
            log.warn("Bad request , the id given in the path doesnt match with the id on the watchlist");
            return ResponseEntity.badRequest().build();
        }
        if (watchlist.getTournament().isEnabled() && watchlist.getUser().isEnabled()){
            log.info("Watchlist updated successfully");
            return ResponseEntity.ok(WatchlistDTO.fromWatchlist(watchlistService.saveWatchlist(watchlist)));
        }else{
            log.warn("Bad request , the tournament  or the user  is disabled or doesnt exit.");
            return ResponseEntity.badRequest().build();
        }

    }

    @PostMapping("")
    public ResponseEntity<WatchlistDTO> createWatchlist(@RequestBody WatchlistDTO watchlistDTO ) {
        Tournament tournament = tournamentService.getTournament(watchlistDTO.getTournament());
        Athlete athlete = athleteService.getAthlete(watchlistDTO.getUser());
        Watchlist watchlist = new Watchlist(tournament, athlete);
        if (watchlist.getTournament().isEnabled() && watchlist.getUser().isEnabled()){
            log.info("Watchlist created successfully");
            return ResponseEntity.ok(WatchlistDTO.fromWatchlist(watchlistService.saveWatchlist(watchlist)));
        }else{
            log.warn("Bad request , the tournament  or the athlete  is disabled or doesnt exit.");
            return ResponseEntity.badRequest().build();
        }

    }



}
