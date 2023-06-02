package ruben.SPM.controllers.EntitiesControllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ruben.SPM.model.DTO.Entities.WatchlistDTO;
import ruben.SPM.model.DTO.Front.WatchlistFrontDTO;
import ruben.SPM.model.Entities.*;
import ruben.SPM.service.EntitiesServices.TournamentService;
import ruben.SPM.service.EntitiesServices.AthleteService;
import ruben.SPM.service.EntitiesServices.UserService;
import ruben.SPM.service.EntitiesServices.WatchlistService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/api/watchlists")
@AllArgsConstructor
@Tag(name="Watchlists")
@CrossOrigin(origins = {"http://localhost:4200"})
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
                return ResponseEntity.ok(new WatchlistFrontDTO(watchlist));
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
                    .status(HttpStatus.NOT_FOUND)
                    .body(msg);
        }
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('athlete:read')")
    @Operation(summary = "Update a watchlist with the data provided.")
    public ResponseEntity updateWatchlist(@PathVariable Long id, @RequestBody WatchlistDTO watchlistDTO) {
        User user = userService.isAuthorized();

        if (user == null){
            String msg = "This user cant do that operation.";
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(msg);
        }
        if (!id.equals(watchlistDTO.getId())) {
            String msg = "Bad request ,the id given in the path doesnt match with the id on the watchlist provided.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(msg);
        }

        if (watchlistService.getWatchList(id) == null) {
            String msg = "Bad request ,there is no watchlist for that id.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(msg);
        }
        Tournament tournament = tournamentService.getTournament(watchlistDTO.getTournament());
        Athlete athlete = athleteService.getAthlete(watchlistDTO.getAthlete());
        Watchlist watchlist = WatchlistDTO.toWatchlist(watchlistDTO,tournament,athlete);
        if (watchlistService.validWatchlist(athlete,tournament)){
            String msg = "You already have this tournament/event in your watchlists.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(msg);
        }
        if (watchlist.getTournament() == null || watchlist.getAthlete() == null){
            if (watchlist.getTournament() == null ) {
                String msg = "The tournament of the watchlist doesnt exist.";
                log.warn(msg);
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(msg);
            }else {
                String msg = "The user of the watchlist doesnt exist.";
                log.warn(msg);
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(msg);
            }
        }else{
            if (!watchlist.getTournament().isEnabled() || !watchlist.getAthlete().isEnabled()){
                if (!watchlist.getTournament().isEnabled()) {
                    String msg = "The tournament of the watchlist is disabled.";
                    log.warn(msg);
                    return ResponseEntity
                            .status(HttpStatus.NOT_FOUND)
                            .body(msg);
                }else {
                    String msg = "The user of the watchlist is disabled.";
                    log.warn(msg);
                    return ResponseEntity
                            .status(HttpStatus.NOT_FOUND)
                            .body(msg);
                }
            }else{
                if (!watchlistService.isValid(watchlist)){
                    String msg = "The tournament is already on your whitelist.";
                    log.warn(msg);
                    return ResponseEntity
                            .status(HttpStatus.BAD_REQUEST)
                            .body(msg);
                }else{
                    log.info("The watchlist has successfully been updated.");
                    return ResponseEntity.ok(WatchlistDTO.fromWatchlist(watchlistService.updateWatchlist(watchlist)));
                }
            }
        }
    }

    @PostMapping("")
    @PreAuthorize("hasAuthority('athlete:create')")
    @Operation(summary = "Create a watchlist with the data provided.")
    public ResponseEntity createWatchlist(@RequestBody WatchlistDTO watchlistDTO) {
        User user = userService.isAuthorized();

        if (user == null){
            String msg = "This user cant do that operation.";
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(msg);
        }

        Tournament tournament = tournamentService.getTournament(watchlistDTO.getTournament());
        Athlete athlete = athleteService.getAthlete(watchlistDTO.getAthlete());
        Watchlist watchlist = WatchlistDTO.toWatchlist(watchlistDTO,tournament,athlete);
        watchlist.setEnabled(true);
        if (watchlistService.validWatchlist(athlete,tournament)){
            String msg = "You already have this tournament/event in your watchlists.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(msg);
        }
        if (watchlist.getTournament() == null || watchlist.getAthlete() == null){
            if (watchlist.getTournament() == null ) {
                String msg = "The tournament of the watchlist doesnt exist.";
                log.warn(msg);
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(msg);
            }else {
                String msg = "The user of the watchlist doesnt exist.";
                log.warn(msg);
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(msg);
            }
        }else{
            if (!watchlist.getTournament().isEnabled() || !watchlist.getAthlete().isEnabled()){
                if (!watchlist.getTournament().isEnabled()) {
                    String msg = "The tournament of the watchlist is disabled.";
                    log.warn(msg);
                    return ResponseEntity
                            .status(HttpStatus.NOT_FOUND)
                            .body(msg);
                }else {
                    String msg = "The user of the watchlist is disabled.";
                    log.warn(msg);
                    return ResponseEntity
                            .status(HttpStatus.NOT_FOUND)
                            .body(msg);
                }
            }else{
                if (!watchlistService.isValid(watchlist)){
                    String msg = "The tournament is already on your whitelist.";
                    log.warn(msg);
                    return ResponseEntity
                            .status(HttpStatus.BAD_REQUEST)
                            .body(msg);
                }else{
                    log.info("The watchlist has successfully been saved.");
                    return ResponseEntity.ok(WatchlistDTO.fromWatchlist(watchlistService.saveWatchlist(watchlist)));
                }
            }
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
        List<WatchlistFrontDTO> watchlistDTOS = watchlists.stream().map(WatchlistFrontDTO::new).collect(Collectors.toList());
        log.info("The watchlists has been retrieved.");
        return ResponseEntity.ok(watchlistDTOS);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('athlete:delete')")
    @Operation(summary = "Deletes the watchlist with the id provided.")
    public ResponseEntity deleteWatchlist(@PathVariable Long id) {
        User user = userService.isAuthorized();
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (user == null){
            String msg = "This user cant do that operation.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(msg);
        }

        user = userService.getUserByUsername(username);
        Watchlist watchlist = watchlistService.getWatchList(id);
        if (watchlist == null) {
            String msg = "There is no watchlist with this id.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(msg);
        }

        if (user.getId() != watchlist.getAthlete().getId()){
            String msg = "The watchlist doesnt belong to your user.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(msg);
        }

        watchlistService.deleteWatchlist(watchlist.getId());
        String msg = "The watchlist has been deleted.";
        log.warn(msg);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(msg);
    }





}
