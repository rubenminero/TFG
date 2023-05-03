package ruben.TFG.controllers;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ruben.TFG.controllers.DTO.InscriptionDTO;
import ruben.TFG.controllers.DTO.WatchlistDTO;
import ruben.TFG.model.Inscription;
import ruben.TFG.model.Tournament;
import ruben.TFG.model.User;
import ruben.TFG.model.Watchlist;
import ruben.TFG.service.InscriptionService;
import ruben.TFG.service.TournamentService;
import ruben.TFG.service.UserService;
import ruben.TFG.service.WatchlistService;

@RestController
@Slf4j
@RequestMapping("/api/watchlists")
public class WatchlistController {

    private final WatchlistService watchlistService;
    private final TournamentService tournamentService;
    private final UserService userService;
    /**
     * Constructor of the class
     * @param watchlistService, the service to manage the inscription's data
     * @param tournamentService, the service to manage the torunament's data
     * @param userService, the service to manage the user's data
     */
    public WatchlistController(WatchlistService watchlistService, TournamentService tournamentService, UserService userService) {
        this.watchlistService = watchlistService;
        this.userService = userService;
        this.tournamentService = tournamentService;
    }



    @GetMapping("/{id}")
    @ApiOperation("Get a watchlist by his id")
    public ResponseEntity<WatchlistDTO> getWatchlistById(@ApiParam("The id of the watchlist") @PathVariable(name = "id") Long id) {

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
    @ApiOperation("Change a watchlist by his id.")
    public ResponseEntity<WatchlistDTO> updateWatchlist(@ApiParam("The id of the watchlist")  @PathVariable Long id, @ApiParam("Modified watchlist object") @RequestBody Watchlist watchlist) {
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
    @ApiOperation("Create a watchlist.")
    public ResponseEntity<WatchlistDTO> createWatchlist(@ApiParam("New watchlist object") @RequestBody WatchlistDTO watchlistDTO ) {
        Tournament tournament = tournamentService.getTournament(watchlistDTO.getTournament());
        User user = userService.getUser(watchlistDTO.getUser());
        Watchlist watchlist = new Watchlist(tournament,user);
        if (watchlist.getTournament().isEnabled() && watchlist.getUser().isEnabled()){
            log.info("Watchlist created successfully");
            return ResponseEntity.ok(WatchlistDTO.fromWatchlist(watchlistService.saveWatchlist(watchlist)));
        }else{
            log.warn("Bad request , the tournament  or the user  is disabled or doesnt exit.");
            return ResponseEntity.badRequest().build();
        }

    }



}
