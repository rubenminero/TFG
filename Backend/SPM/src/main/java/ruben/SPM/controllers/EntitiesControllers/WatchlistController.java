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
            String msg = "This user can't do that operation.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(msg);
        }
        Watchlist watchlist = watchlistService.getWatchList(id);
        if (watchlist != null) {
            if (watchlist.isEnabled()){
                log.info("The watchlist has been found.");
                log.info("The watchlist has been found.");
                return ResponseEntity.ok(new WatchlistFrontDTO(watchlist));
            }else {
                String msg = "The watchlist that you asked is disabled.";
                log.warn(msg);
                return ResponseEntity
                        .status(HttpStatus.FORBIDDEN)
                        .body(msg);
            }
        } else {
            String msg = "The watchlist that you asked doesnt exist.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(msg);
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
        Watchlist watchlist =new Watchlist(watchlistDTO,tournament,athlete);

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
                    String msg = "You already have this tournament/event in your watchlists.";
                    log.warn(msg);
                    return ResponseEntity
                            .status(HttpStatus.BAD_REQUEST)
                            .body(msg);
                }else{
                    log.info("The watchlist has successfully been saved.");
                    watchlistService.saveWatchlist(watchlist);
                    return ResponseEntity.ok(watchlistDTO);
                }
            }
        }
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

        if (!user.getId().equals(watchlist.getAthlete().getId())){
            String msg = "The watchlist doesnt belong to your user.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(msg);
        }

        watchlistService.deleteWatchlist(watchlist.getId());
        String msg = "The watchlist has been deleted.";
        log.info(msg);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(msg);
    }





}
