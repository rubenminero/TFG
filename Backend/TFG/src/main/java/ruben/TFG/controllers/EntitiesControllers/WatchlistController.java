package ruben.TFG.controllers.EntitiesControllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ruben.TFG.model.DTO.Entities.InscriptionDTO;
import ruben.TFG.model.DTO.Entities.Sports_typeDTO;
import ruben.TFG.model.DTO.Entities.WatchlistDTO;
import ruben.TFG.model.Entities.*;
import ruben.TFG.service.EntitiesServices.TournamentService;
import ruben.TFG.service.EntitiesServices.AthleteService;
import ruben.TFG.service.EntitiesServices.UserService;
import ruben.TFG.service.EntitiesServices.WatchlistService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/api/watchlists")
@AllArgsConstructor
@Tag(name="Watchlists")
public class WatchlistController {

    private final WatchlistService watchlistService;
    private final TournamentService tournamentService;
    private final AthleteService athleteService;
    private final UserService userService;


    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('athlete:read')")
    @Operation(summary = "Return a watchlist with the id provided.")
    public ResponseEntity getWatchlistById(@PathVariable(name = "id") Long id) {

        User user = userService.isAuthorized();

        if (user == null){
            String msg = "This user cant do that operation.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(msg);
        }
        Watchlist watchlist = watchlistService.getWatchList(id);
        if (watchlist != null) {
            if (watchlist.isEnabled()){
                log.info("The watchlist has been found");
                return ResponseEntity.ok(new WatchlistDTO(watchlist));
            }else {
                String msg = "The watchlist that you asked is disabled.";
                log.warn(msg);
                return ResponseEntity
                        .status(HttpStatus.FORBIDDEN)
                        .body(msg);
            }
        }
        else {
            String msg = "The watchlist that you asked doesnt exist.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .body(msg);
        }
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('athlete:read')")
    @Operation(summary = "Update a watchlist with the data provided.")
    public ResponseEntity updateWatchlist(@PathVariable Long id, @RequestBody Watchlist watchlist) {
        User user = userService.isAuthorized();

        if (user == null){
            String msg = "This user cant do that operation.";
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(msg);
        }
        if (!id.equals(watchlist.getId())) {
            String msg = "The id that you give doesnt match with the id of the watchlist.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .body(msg);
        }
        if (!watchlist.getTournament().isEnabled() || !watchlist.getUser().isEnabled()){
            if (!watchlist.getTournament().isEnabled()) {
                String msg = "The tournament of the watchlist is disabled.";
                log.warn(msg);
                return ResponseEntity
                        .status(HttpStatus.NO_CONTENT)
                        .body(msg);
            }else {
                String msg = "The user of the watchlist is disabled.";
                log.warn(msg);
                return ResponseEntity
                        .status(HttpStatus.NO_CONTENT)
                        .body(msg);
            }
        }else{
            log.info("The watchlist has successfully been updated.");
            return ResponseEntity.ok(WatchlistDTO.fromWatchlist(watchlistService.saveWatchlist(watchlist)));
        }

    }

    @PostMapping("")
    @PreAuthorize("hasAuthority('athlete:create')")
    @Operation(summary = "Create a watchlist with the data provided.")
    public ResponseEntity createWatchlist(@RequestBody WatchlistDTO watchlistDTO ) {
        User user = userService.isAuthorized();

        if (user == null){
            String msg = "This user cant do that operation.";
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(msg);
        }

        Tournament tournament = tournamentService.getTournament(watchlistDTO.getTournament());
        Athlete athlete = athleteService.getAthlete(watchlistDTO.getUser());
        Watchlist watchlist = new Watchlist(tournament, athlete);
        if (!watchlist.getTournament().isEnabled() || !watchlist.getUser().isEnabled()){
            if (!watchlist.getTournament().isEnabled()) {
                String msg = "The tournament of the watchlist is disabled.";
                log.warn(msg);
                return ResponseEntity
                        .status(HttpStatus.NO_CONTENT)
                        .body(msg);
            }else {
                String msg = "The user of the watchlist is disabled.";
                log.warn(msg);
                return ResponseEntity
                        .status(HttpStatus.NO_CONTENT)
                        .body(msg);
            }
        }else{
            log.info("The watchlist has successfully been saved.");
            return ResponseEntity.ok(WatchlistDTO.fromWatchlist(watchlistService.saveWatchlist(watchlist)));
        }



    }


    @GetMapping("")
    @PreAuthorize("hasAuthority('athlete:read')")
    @Operation(summary = "Return all the enabled watchlists.")
    public ResponseEntity getAllWatchlists() {
        User user = userService.isAuthorized();

        if (user == null){
            String msg = "This user cant do that operation.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(msg);
        }
        List<Watchlist> watchlists = watchlistService.getEnabledWatchlists(user.getId());
        if (watchlists.size() == 0 || watchlists == null) {
            String msg = "There is no watchlists.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(msg);
        }
        List<WatchlistDTO> watchlistDTOS = watchlists.stream().map(WatchlistDTO::new).collect(Collectors.toList());
        log.info("The watchlists has been retrieved.");
        return ResponseEntity.ok(watchlistDTOS);
    }





}
