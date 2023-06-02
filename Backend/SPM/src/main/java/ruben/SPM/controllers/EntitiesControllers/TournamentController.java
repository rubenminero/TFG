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
<<<<<<< Updated upstream
import ruben.SPM.model.Entities.Organizer;
import ruben.SPM.model.Entities.Sports_type;
import ruben.SPM.model.Entities.Tournament;
import ruben.SPM.model.Entities.User;
import ruben.SPM.service.EntitiesServices.OrganizerService;
import ruben.SPM.service.EntitiesServices.Sports_typeService;
import ruben.SPM.service.EntitiesServices.TournamentService;
import ruben.SPM.service.EntitiesServices.UserService;
=======
import ruben.SPM.model.DTO.Front.EventFrontDTO;
import ruben.SPM.model.DTO.Front.InscriptionFrontDTO;
import ruben.SPM.model.DTO.Front.TournamentFrontDTO;
import ruben.SPM.model.Entities.*;
import ruben.SPM.service.EntitiesServices.*;
>>>>>>> Stashed changes

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
    private final InscriptionService inscriptionService;
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

<<<<<<< Updated upstream
    @GetMapping("/{id}")
=======
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
        List<EventFrontDTO> eventFrontDTOS = new ArrayList<EventFrontDTO>();
        for (Tournament t : tournaments) {
            EventFrontDTO event = new EventFrontDTO(t);
            eventFrontDTOS.add(event);
        }
        log.info("The events has successfully been retrieved.");
        return ResponseEntity.ok(eventFrontDTOS);
    }

    @GetMapping("/api/tournaments/{id}")
>>>>>>> Stashed changes
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
    public ResponseEntity updateTournament(@PathVariable Long id, @RequestBody TournamentFrontDTO tournamentDTO) {
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

        Organizer organizer = this.organizerService.getOrganizerByCompany_name(tournamentDTO.getOrganizer());
        Sports_type sportsType = this.sportsTypeService.getSport_typeByName(tournamentDTO.getSport_type());
        Tournament tournament = new Tournament(tournamentDTO,organizer,sportsType);
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
<<<<<<< Updated upstream
                return ResponseEntity.ok(TournamentDTO.fromTournament(tournamentService.saveTournament(tournament)));
=======
                TournamentFrontDTO tournamentFrontDTO = TournamentFrontDTO.fromTournament(tournamentService.saveTournament(tournament));
                System.out.println(tournamentFrontDTO);
                return ResponseEntity.ok(tournamentFrontDTO);
>>>>>>> Stashed changes
            }
        }

    }

<<<<<<< Updated upstream
    @PostMapping("")
=======

    @PostMapping("/api/tournaments")
>>>>>>> Stashed changes
    @PreAuthorize("hasAuthority('organizer:create')")
    @Operation(summary = "Create a tournament with the data provided.")
    public ResponseEntity createTournament(@RequestBody TournamentFrontDTO tournamentFrontDTO ) {
        User user = userService.isAuthorized();

        if (user == null){
            String msg = "This user cant do that operation.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(msg);
        }
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Organizer organizer = organizerService.getOrganizerByUsername(username);
        Sports_type sportType = sportsTypeService.getSport_typeByName(tournamentFrontDTO.getSport_type());
        Tournament tournament = TournamentFrontDTO.toTournament(tournamentFrontDTO,organizer,sportType);
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
<<<<<<< Updated upstream
                return ResponseEntity.ok(TournamentDTO.fromTournament(tournamentService.saveTournament(tournament)));
=======
                TournamentDTO tournamentDTO1 = TournamentDTO.fromTournament(tournamentService.saveTournament(tournament));
                return ResponseEntity.ok(tournamentDTO1);
>>>>>>> Stashed changes
            }
        }

    }

    @PutMapping("/api/events/{id}")
    @PreAuthorize("hasAuthority('organizer:update')")
    @Operation(summary = "Update a event with the data provided.")
    public ResponseEntity updateEvent(@PathVariable Long id, @RequestBody EventFrontDTO eventFrontDTO) {
        User user = userService.isAuthorized();

        if (user == null){
            String msg = "This user cant do that operation.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(msg);
        }
        if (!id.equals(eventFrontDTO.getId())) {
            String msg = "Bad request ,the id given in the path doesnt match with the id on the organizer.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(msg);
        }

        if (tournamentService.getTournament(id) == null) {
            String msg = "Bad request ,there is no event for that id.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(msg);
        }
        Organizer organizer = this.organizerService.getOrganizerByCompany_name(eventFrontDTO.getOrganizer());
        Sports_type sportsType = this.sportsTypeService.getSport_typeByName(eventFrontDTO.getSport_type());
        Tournament tournament = new Tournament(eventFrontDTO,organizer,sportsType);
        if (tournament.getOrganizer() == null || tournament.getSport_type() == null){
            if (tournament.getOrganizer() == null ) {
                String msg = "The organizer of the event doesnt exist.";
                log.warn(msg);
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(msg);
            }else {
                String msg = "The sport type for the event doesnt exist.";
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
                EventFrontDTO eventFrontDTO1 = EventFrontDTO.fromTournament(tournamentService.saveTournament(tournament));
                System.out.println(eventFrontDTO1);
                return ResponseEntity.ok(eventFrontDTO1);
            }
        }

    }
    @PostMapping("/api/events")
    @PreAuthorize("hasAuthority('organizer:create')")
    @Operation(summary = "Create a event with the data provided.")
    public ResponseEntity createEvent(@RequestBody EventFrontDTO eventFrontDTO ) {
        User user = userService.isAuthorized();

        if (user == null){
            String msg = "This user cant do that operation.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(msg);
        }
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Organizer organizer = organizerService.getOrganizerByUsername(username);
        Sports_type sportType = sportsTypeService.getSport_typeByName(eventFrontDTO.getSport_type());
        Tournament tournament = EventFrontDTO.toTournament(eventFrontDTO,organizer,sportType);
        tournament.setEnabled(true);
        if (tournament.getOrganizer() == null || tournament.getSport_type() == null){
            if (tournament.getOrganizer() == null ) {
                String msg = "The organizer of the event doesnt exist.";
                log.warn(msg);
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(msg);
            }else {
                String msg = "The sport type for the event doesnt exist.";
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
                EventFrontDTO eventFrontDTO1 = EventFrontDTO.fromTournament(tournamentService.saveTournament(tournament));
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
        if (user == null){
            String msg = "This user cant do that operation.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(msg);
        }

        if (tournament.getOrganizer().getId() != user.getId()){
            String msg = "Bad request ,the organizer id for the tournament doesnt match with the id on the organizer.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(msg);
        }

        if (tournament == null) {
            String msg = "Bad request ,there is no tournament for that id.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(msg);
        }
        this.tournamentService.deleteTournament(id);
        log.info("The tournament has successfully been deleted.");
        return ResponseEntity.ok(TournamentDTO.fromTournament(tournamentService.updateTournament(tournament)));
    }

    @GetMapping("/api/tournaments/inscriptions/{id}")
    @PreAuthorize("hasAuthority('organizer:read')")
    @Operation(summary = "Return the inscriptions for the tournament with the id provided.")
    public ResponseEntity getInscriptionsforTournament(@PathVariable(name = "id") Long id) {
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
               List<Inscription> inscriptions = this.inscriptionService.getInscriptionsforTournament(id);
               List<InscriptionFrontDTO> inscriptionFrontDTOS = inscriptions.stream().map(InscriptionFrontDTO::new).collect(Collectors.toList());
               log.info("The insctiptions for that tournament has successfully been retrieved.");
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
            String msg = "The tournament by id has not been found";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(msg);
        }
    }

}
