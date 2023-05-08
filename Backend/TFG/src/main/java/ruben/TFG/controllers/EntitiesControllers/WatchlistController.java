package ruben.TFG.controllers.EntitiesControllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ruben.TFG.model.DTO.Entities.WatchlistDTO;
import ruben.TFG.model.Entities.Athlete;
import ruben.TFG.model.Entities.Tournament;
import ruben.TFG.model.Entities.Watchlist;
import ruben.TFG.service.EntitiesServices.TournamentService;
import ruben.TFG.service.EntitiesServices.AthleteService;
import ruben.TFG.service.EntitiesServices.WatchlistService;

@RestController
@Slf4j
@RequestMapping("/api/watchlists")
@AllArgsConstructor
public class WatchlistController {

    private final WatchlistService watchlistService;
    private final TournamentService tournamentService;
    private final AthleteService athleteService;


    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('athlete:read')")
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
    @PreAuthorize("hasAuthority('athlete:read')")
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
    @PreAuthorize("hasAuthority('athlete:create')")
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
