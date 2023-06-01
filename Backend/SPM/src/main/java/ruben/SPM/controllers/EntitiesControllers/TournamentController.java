package ruben.SPM.controllers.EntitiesControllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ruben.SPM.model.DTO.Entities.TournamentDTO;
import ruben.SPM.model.DTO.Front.EventFrontDTO;
import ruben.SPM.model.DTO.Front.TournamentFrontDTO;
import ruben.SPM.model.Entities.Organizer;
import ruben.SPM.model.Entities.Sports_type;
import ruben.SPM.model.Entities.Tournament;
import ruben.SPM.model.Entities.User;
import ruben.SPM.service.EntitiesServices.OrganizerService;
import ruben.SPM.service.EntitiesServices.Sports_typeService;
import ruben.SPM.service.EntitiesServices.TournamentService;
import ruben.SPM.service.EntitiesServices.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@AllArgsConstructor
@Tag(name="Tournaments")
@CrossOrigin(origins = {"http://localhost:4200"})
public class TournamentController {

    private final TournamentService tournamentService;
    private final OrganizerService organizerService;
    private final Sports_typeService sportsTypeService;
    private final UserService userService;

    @GetMapping("/api/tournaments")
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
        List<TournamentFrontDTO> tournamentFrontDTOS = new ArrayList<TournamentFrontDTO>();
        for (Tournament t : tournaments) {
            TournamentFrontDTO tournamentFrontDTO = new TournamentFrontDTO(t);
            tournamentFrontDTOS.add(tournamentFrontDTO);
        }
        log.info("The tournaments has successfully been retrieved.");
        return ResponseEntity.ok(tournamentFrontDTOS);
    }

    @GetMapping("/api/events")
    @PreAuthorize("hasAuthority('athlete:read')")
    @Operation(summary = "Return the enabled events.")
    public ResponseEntity getAllEvents() {
        User user = userService.isAuthorized();

        if (user == null){
            String msg = "This user cant do that operation.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(msg);
        }
        List<Tournament> tournaments = tournamentService.getEnabledEvents();
        if (tournaments.size() == 0 || tournaments == null) {
            String msg = "There is no events.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(msg);
        }
        List<EventFrontDTO> events = new ArrayList<EventFrontDTO>();
        for (Tournament t : tournaments) {
            EventFrontDTO event = new EventFrontDTO(t);
            events.add(event);
        }
        log.info("The events has successfully been retrieved.");
        return ResponseEntity.ok(events);
    }

    @GetMapping("/api/tournaments/{id}")
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
                if (tournament.getInscription()){
                    log.info("The tournament has been found");
                    return ResponseEntity.ok(new TournamentFrontDTO(tournament));
                }else{
                    log.info("The event has been found");
                    return ResponseEntity.ok(new EventFrontDTO(tournament));
                }
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

    @PutMapping("/api/tournaments/{id}")
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
                return ResponseEntity.ok(TournamentDTO.fromTournament(tournamentService.updateTournament(tournament)));
            }
        }

    }

    @PostMapping("/api/tournaments")
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
                TournamentDTO tournamentDTO1 = TournamentDTO.fromTournament(tournamentService.saveTournament(tournament));
                log.info(Boolean.toString(tournamentDTO1.getInscription()));
                return ResponseEntity.ok(tournamentDTO1);
            }
        }

    }



}
