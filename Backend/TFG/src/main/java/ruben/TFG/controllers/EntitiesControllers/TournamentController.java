package ruben.TFG.controllers.EntitiesControllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ruben.TFG.model.DTO.Entities.TournamentDTO;
import ruben.TFG.model.Entities.Organizer;
import ruben.TFG.model.Entities.Sports_type;
import ruben.TFG.model.Entities.Tournament;
import ruben.TFG.model.Entities.User;
import ruben.TFG.service.EntitiesServices.OrganizerService;
import ruben.TFG.service.EntitiesServices.Sports_typeService;
import ruben.TFG.service.EntitiesServices.TournamentService;
import ruben.TFG.service.EntitiesServices.UserService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/api/tournaments")
@AllArgsConstructor
@Tag(name="Tournaments")
public class TournamentController {

    private final TournamentService tournamentService;
    private final OrganizerService organizerService;
    private final Sports_typeService sportsTypeService;
    private final UserService userService;

    @GetMapping("")
    @PreAuthorize("hasAuthority('athlete:read')")
    @Operation(summary = "Return the enabled tournaments.")
    public ResponseEntity getAllTournaments() {
        User user = userService.isAuthorized();

        if (user == null){
            String msg = "This user cant do that operation.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(msg);
        }
        List<Tournament> tournaments = tournamentService.getEnabledTournaments();
        if (tournaments.size() == 0 || tournaments == null) {
            String msg = "There is no tournaments.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(msg);
        }

        List<TournamentDTO> tournamentDTOs = tournaments.stream().map(TournamentDTO::new).collect(Collectors.toList());
        log.info("The tournaments has successfully been retrieved.");
        return ResponseEntity.ok(tournamentDTOs);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('athlete:read')")
    @Operation(summary = "Return a tournament with the id provided.")
    public ResponseEntity getTournamentById(@PathVariable(name = "id") Long id) {
        User user = userService.isAuthorized();

        if (user == null){
            String msg = "This user cant do that operation.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(msg);
        }

        Tournament tournament = tournamentService.getTournament(id);
        if (tournament != null) {
            if (tournament.isEnabled()){
                log.info("The tournament has been found");
                return ResponseEntity.ok(new TournamentDTO(tournament));
            }else {
                String msg = "The tournament that you asked is disabled.";
                log.warn(msg);
                return ResponseEntity
                        .status(HttpStatus.FORBIDDEN)
                        .body(msg);
            }
        }
        else {
            String msg = "The tournament by id has not been found";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(msg);
        }
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('organizer:update')")
    @Operation(summary = "Update a tournament with the data provided.")
    public ResponseEntity updateTournament(@PathVariable Long id, @RequestBody TournamentDTO tournamentDTO) {
        User user = userService.isAuthorized();

        if (user == null){
            String msg = "This user cant do that operation.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(msg);
        }
        if (!id.equals(tournamentDTO.getId())) {
            String msg = "Bad request ,the id given in the path doesnt match with the id on the organizer.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(msg);
        }

        if (tournamentService.getTournament(id) == null) {
            String msg = "Bad request ,there is no tournament for that id.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(msg);
        }
        Organizer organizer = organizerService.getOrganizer(tournamentDTO.getOrganizer());
        Sports_type sportType = sportsTypeService.getSport_type(tournamentDTO.getSport_type());
        Tournament tournament = TournamentDTO.toTournament(tournamentDTO,organizer,sportType);
        tournament.setOrganizer(organizer);
        tournament.setSport_type(sportType);
        if (tournament.getOrganizer() == null || tournament.getSport_type() == null){
            if (tournament.getOrganizer() == null ) {
                String msg = "The organizer of the tournament doesnt exist.";
                log.warn(msg);
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(msg);
            }else {
                String msg = "The sport type for the tournament doesnt exist.";
                log.warn(msg);
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(msg);
            }
        }else{
            if (!tournament.getOrganizer().isEnabled() || !tournament.getSport_type().isEnabled()){
                if (!tournament.getOrganizer().isEnabled()) {
                    String msg = "The organizer of the tournament is disabled.";
                    log.warn(msg);
                    return ResponseEntity
                            .status(HttpStatus.NOT_FOUND)
                            .body(msg);
                }else {
                    String msg = "The sport type for the tournament is disabled.";
                    log.warn(msg);
                    return ResponseEntity
                            .status(HttpStatus.NOT_FOUND)
                            .body(msg);
                }
            }else{
                log.info("The tournament has successfully been updated.");
                return ResponseEntity.ok(TournamentDTO.fromTournament(tournamentService.saveTournament(tournament)));
            }
        }

    }

    @PostMapping("")
    @PreAuthorize("hasAuthority('organizer:create')")
    @Operation(summary = "Create a tournament with the data provided.")
    public ResponseEntity createTournament(@RequestBody TournamentDTO tournamentDTO ) {
        User user = userService.isAuthorized();

        if (user == null){
            String msg = "This user cant do that operation.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(msg);
        }
        Organizer organizer = organizerService.getOrganizer(tournamentDTO.getOrganizer());
        Sports_type sportType = sportsTypeService.getSport_type(tournamentDTO.getSport_type());
        Tournament tournament = TournamentDTO.toTournament(tournamentDTO,organizer,sportType);
        tournament.setEnabled(true);
        if (tournament.getOrganizer() == null || tournament.getSport_type() == null){
            if (tournament.getOrganizer() == null ) {
                String msg = "The organizer of the tournament doesnt exist.";
                log.warn(msg);
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(msg);
            }else {
                String msg = "The sport type for the tournament doesnt exist.";
                log.warn(msg);
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(msg);
            }
        }else{
            if (!tournament.getOrganizer().isEnabled() || !tournament.getSport_type().isEnabled()){
                if (!tournament.getOrganizer().isEnabled()) {
                    String msg = "The organizer of the tournament is disabled.";
                    log.warn(msg);
                    return ResponseEntity
                            .status(HttpStatus.NOT_FOUND)
                            .body(msg);
                }else {
                    String msg = "The sport type for the tournament is disabled.";
                    log.warn(msg);
                    return ResponseEntity
                            .status(HttpStatus.NOT_FOUND)
                            .body(msg);
                }
            }else{
                log.info("The tournament has successfully been saved.");
                return ResponseEntity.ok(TournamentDTO.fromTournament(tournamentService.saveTournament(tournament)));
            }
        }

    }



}
