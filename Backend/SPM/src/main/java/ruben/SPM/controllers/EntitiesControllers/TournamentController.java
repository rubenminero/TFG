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
import ruben.SPM.model.DTO.Entities.Sports_typeDTO;
import ruben.SPM.model.DTO.Entities.TournamentDTO;
import ruben.SPM.model.Entities.Organizer;
import ruben.SPM.model.Entities.Sports_type;
import ruben.SPM.model.Entities.Tournament;
import ruben.SPM.model.Entities.User;
import ruben.SPM.service.EntitiesServices.OrganizerService;
import ruben.SPM.service.EntitiesServices.Sports_typeService;
import ruben.SPM.service.EntitiesServices.TournamentService;
import ruben.SPM.service.EntitiesServices.UserService;
import ruben.SPM.model.DTO.Front.EventFrontDTO;
import ruben.SPM.model.DTO.Front.InscriptionFrontDTO;
import ruben.SPM.model.DTO.Front.TournamentFrontDTO;
import ruben.SPM.model.Entities.*;
import ruben.SPM.service.EntitiesServices.*;

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
    private final InscriptionService inscriptionService;
    private final UserService userService;

    @GetMapping("/api/tournaments")
    @PreAuthorize("hasAuthority('athlete:read')")
    @Operation(summary = "Return the enabled tournaments.")
    public ResponseEntity getAllTournaments() {
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
            TournamentFrontDTO tournament = new TournamentFrontDTO(t);
            tournamentFrontDTOS.add(tournament);
        }
        log.info("The tournaments has successfully been retrieved.");
        return ResponseEntity.ok(tournamentFrontDTOS);
    }



    @GetMapping("/api/events")
    @PreAuthorize("hasAuthority('athlete:read')")
    @Operation(summary = "Return the enabled events.")
    public ResponseEntity getAllEvents() {
        List<Tournament> tournaments = tournamentService.getEnabledEvents();
        if (tournaments.size() == 0 || tournaments == null) {
            String msg = "There is no events.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(msg);
        }
        List<EventFrontDTO> eventFrontDTOS = new ArrayList<EventFrontDTO>();
        for (Tournament t : tournaments) {
            EventFrontDTO event = new EventFrontDTO(t);
            eventFrontDTOS.add(event);
        }
        log.info("The events has successfully been retrieved.");
        return ResponseEntity.ok(eventFrontDTOS);
    }

    @GetMapping("/api/tournaments/{id}")
    @PreAuthorize("hasAuthority('athlete:read')")
    @Operation(summary = "Return a tournament with the id provided.")
    public ResponseEntity getTournamentById(@PathVariable(name = "id") Long id) {
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


    @PutMapping("/api/tournaments/{id}")
    @PreAuthorize("hasAuthority('organizer:update')")
    @Operation(summary = "Update a tournament with the data provided.")
    public ResponseEntity updateTournament(@PathVariable Long id, @RequestBody TournamentFrontDTO tournamentDTO) {
        if (!id.equals(tournamentDTO.getId())) {
            String msg = "Bad request, the id given in the path doesn't match with the id on the tournament.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(msg);
        }
        Tournament tournament = tournamentService.getTournament(id);
        if (tournament == null) {
            String msg = "Bad request, there is no tournament for that id.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(msg);
        }
        Organizer organizer = this.organizerService.getOrganizerByCompany_name(tournamentDTO.getOrganizer());
        Sports_type sportsType = this.sportsTypeService.getSport_typeByName(tournamentDTO.getSport_type());
        tournament = new Tournament(tournamentDTO,organizer,sportsType);
        if (tournament.getOrganizer() == null || tournament.getSport_type() == null){
            if (tournament.getOrganizer() == null ) {
                String msg = "The organizer of the tournament doesn't exist.";
                log.warn(msg);
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(msg);
            }else {
                String msg = "The sport type for the tournament doesn't exist.";
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
                tournamentService.updateTournament(tournament);
                TournamentFrontDTO tournamentFrontDTO = TournamentFrontDTO.fromTournament(tournament);
                return ResponseEntity.ok(tournamentFrontDTO);
            }
        }

    }


    @PostMapping("/api/tournaments")
    @PreAuthorize("hasAuthority('organizer:create')")
    @Operation(summary = "Create a tournament with the data provided.")
    public ResponseEntity createTournament(@RequestBody TournamentFrontDTO tournamentFrontDTO ) {
        User user = userService.isAuthorized();
        Organizer organizer = organizerService.getOrganizerByUsername(user.getUsername());
        Sports_type sportType = sportsTypeService.getSport_typeByName(tournamentFrontDTO.getSport_type());
        Tournament tournament = new Tournament(tournamentFrontDTO,organizer,sportType);
        if (tournament.getOrganizer() == null || tournament.getSport_type() == null){
            if (tournament.getOrganizer() == null ) {
                String msg = "The organizer of the tournament doesn't exist.";
                log.warn(msg);
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(msg);
            }else {
                String msg = "The sport type for the tournament doesn't exist.";
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
                tournamentService.saveTournament(tournament);
                TournamentFrontDTO tournamentFrontDTO1 = TournamentFrontDTO.fromTournament(tournament);
                return ResponseEntity.ok(tournamentFrontDTO1);
            }
        }

    }

    @PutMapping("/api/events/{id}")
    @PreAuthorize("hasAuthority('organizer:update')")
    @Operation(summary = "Update a event with the data provided.")
    public ResponseEntity updateEvent(@PathVariable Long id, @RequestBody EventFrontDTO eventFrontDTO) {
        if (!id.equals(eventFrontDTO.getId())) {
            String msg = "Bad request, the id given in the path doesn't match with the id on the event.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(msg);
        }
        Tournament tournament = tournamentService.getTournament(id);
        if (tournament == null) {
            String msg = "Bad request, there is no event for that id.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(msg);
        }
        Organizer organizer = this.organizerService.getOrganizerByCompany_name(eventFrontDTO.getOrganizer());
        Sports_type sportsType = this.sportsTypeService.getSport_typeByName(eventFrontDTO.getSport_type());
        tournament = new Tournament(eventFrontDTO,organizer,sportsType);
        if (tournament.getOrganizer() == null || tournament.getSport_type() == null){
            if (tournament.getOrganizer() == null ) {
                String msg = "The organizer of the event doesn't exist.";
                log.warn(msg);
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(msg);
            }else {
                String msg = "The sport type for the event doesn't exist.";
                log.warn(msg);
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(msg);
            }
        }else{
            if (!tournament.getOrganizer().isEnabled() || !tournament.getSport_type().isEnabled()){
                if (!tournament.getOrganizer().isEnabled()) {
                    String msg = "The organizer of the event is disabled.";
                    log.warn(msg);
                    return ResponseEntity
                            .status(HttpStatus.NOT_FOUND)
                            .body(msg);
                }else {
                    String msg = "The sport type for the event is disabled.";
                    log.warn(msg);
                    return ResponseEntity
                            .status(HttpStatus.NOT_FOUND)
                            .body(msg);
                }
            }else{
                log.info("The event has successfully been updated.");
                tournamentService.updateTournament(tournament);
                EventFrontDTO eventFrontDTO1 = EventFrontDTO.fromTournament(tournament);;
                return ResponseEntity.ok(eventFrontDTO1);
            }
        }

    }
    @PostMapping("/api/events")
    @PreAuthorize("hasAuthority('organizer:create')")
    @Operation(summary = "Create a event with the data provided.")
    public ResponseEntity createEvent(@RequestBody EventFrontDTO eventFrontDTO ) {
        User user = userService.isAuthorized();
        Organizer organizer = organizerService.getOrganizerByUsername(user.getUsername());
        Sports_type sportType = sportsTypeService.getSport_typeByName(eventFrontDTO.getSport_type());
        Tournament tournament = new Tournament(eventFrontDTO,organizer,sportType);
        if (tournament.getOrganizer() == null || tournament.getSport_type() == null){
            if (tournament.getOrganizer() == null ) {
                String msg = "The organizer of the event doesn't exist.";
                log.warn(msg);
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(msg);
            }else {
                String msg = "The sport type for the event doesn't exist.";
                log.warn(msg);
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(msg);
            }
        }else{
            if (!tournament.getOrganizer().isEnabled() || !tournament.getSport_type().isEnabled()){
                if (!tournament.getOrganizer().isEnabled()) {
                    String msg = "The organizer of the event is disabled.";
                    log.warn(msg);
                    return ResponseEntity
                            .status(HttpStatus.NOT_FOUND)
                            .body(msg);
                }else {
                    String msg = "The sport type for the event is disabled.";
                    log.warn(msg);
                    return ResponseEntity
                            .status(HttpStatus.NOT_FOUND)
                            .body(msg);
                }
            }else{
                log.info("The event has successfully been saved.");
                tournamentService.saveTournament(tournament);
                EventFrontDTO eventFrontDTO1 = EventFrontDTO.fromTournament(tournament);
                return ResponseEntity.ok(eventFrontDTO1);
            }
        }

    }


    @DeleteMapping("/api/tournaments/{id}")
    @PreAuthorize("hasAuthority('organizer:delete')")
    @Operation(summary = "Delete a tournament with the data provided.")
    public ResponseEntity deleteTournament(@PathVariable Long id) {
        User user = userService.isAuthorized();
        Tournament tournament = tournamentService.getTournament(id);
        if (tournament == null) {
            String msg = "Bad request, there is no tournament for that id.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(msg);
        }
        if (tournament.getOrganizer().getId() != user.getId()){
            String msg = "Bad request, the organizer id for the tournament doesn't match with the id on the organizer.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(msg);
        }
        this.tournamentService.deleteTournament(id);
        log.info("The tournament has successfully been deleted.");
        tournamentService.deleteTournament(tournament.getId());
        TournamentDTO tournamentDTO = new TournamentDTO(tournament);
        return ResponseEntity.ok(tournamentDTO);
    }

    @GetMapping("/api/tournaments/inscriptions/{id}")
    @PreAuthorize("hasAuthority('organizer:read')")
    @Operation(summary = "Return the inscriptions for the tournament with the id provided.")
    public ResponseEntity getInscriptionsforTournament(@PathVariable(name = "id") Long id) {
        User user = userService.isAuthorized();
        Tournament tournament = tournamentService.getTournament(id);

        if (user == null){
            String msg = "This user can't do that operation.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(msg);
        }
        if (tournament != null) {
            if (tournament.isEnabled()){
               List<Inscription> inscriptions = this.inscriptionService.getInscriptionsforTournament(id);
               List<InscriptionFrontDTO> inscriptionFrontDTOS = new ArrayList<>();
                for (Inscription i : inscriptions) {
                    InscriptionFrontDTO inscriptionFrontDTO = new InscriptionFrontDTO(i);
                    inscriptionFrontDTOS.add(inscriptionFrontDTO);
                }
               log.info("The inscriptions for that tournament has successfully been retrieved.");
               return ResponseEntity.ok(inscriptionFrontDTOS);
            }else {
                String msg = "The tournament that you asked is disabled.";
                log.warn(msg);
                return ResponseEntity
                        .status(HttpStatus.FORBIDDEN)
                        .body(msg);
            }
        }
        else {
            String msg = "The tournament by id has not been found.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(msg);
        }
    }

}
