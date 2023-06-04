package ruben.SPM.controllers.EntitiesControllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Or;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ruben.SPM.model.DTO.Auth.PasswordChangeDTO;
import ruben.SPM.model.DTO.Entities.*;
import ruben.SPM.model.DTO.Entities.WatchlistDTO;
import ruben.SPM.model.DTO.Front.DeleteDisabledSummaryFrontDTO;
import ruben.SPM.model.DTO.Front.EventFrontDTO;
import ruben.SPM.model.DTO.Front.InscriptionFrontDTO;
import ruben.SPM.model.DTO.Front.WatchlistFrontDTO;
import ruben.SPM.model.Entities.*;
import ruben.SPM.model.JWT_Token.Token;
import ruben.SPM.service.EntitiesServices.AdminService;
import ruben.SPM.service.EntitiesServices.DeleteService;
import ruben.SPM.service.EntitiesServices.UserService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/api/admins")
@PreAuthorize("hasRole('ADMIN')")
@Tag(name="Admins")
@AllArgsConstructor
@CrossOrigin(origins = {"http://localhost:4200"})
public class AdminController {

    private final AdminService adminService;
    private final UserService userService;
    private final DeleteService deleteService;

    @GetMapping("/organizers")
    @PreAuthorize("hasAuthority('admin:read')")
    @Operation(summary = "Return all the organizers in the database.")
    public ResponseEntity getAllOrganizers_Admin() {
        User user = userService.isAuthorized();

        if (user == null){
            String msg = "This admin cant do that operation.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(msg);
        }

        List<Organizer> organizers = adminService.getAllOrganizers();
        if (organizers.size() == 0 || organizers == null) {
            String msg = "There is no organizers.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(msg);
        }
        List<OrganizerDTO> organizerDTOS = organizers.stream().map(OrganizerDTO::new).collect(Collectors.toList());
        log.info("The organizers has successfully been retrieved.");
        return ResponseEntity.ok(organizerDTOS);
    }
    @PutMapping("/organizers/{id}")
    @PreAuthorize("hasAuthority('admin:update')")
    @Operation(summary = "Disable/enable the organizer with the id provided.")
    public ResponseEntity updateOrganizer(@PathVariable Long id) {
        User user = userService.isAuthorized();

        if (user == null){
            String msg = "This admin cant do that operation.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(msg);
        }

        if (adminService.getOrganizer(id) == null) {
            String msg = "There is no organizer with this id.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(msg);
        }
        adminService.changeStateOrganizer(id);
        String msg = "The state of the organizer has changed.";
        log.warn(msg);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(msg);
    }

    @DeleteMapping("/organizers/{id}")
    @PreAuthorize("hasAuthority('admin:delete')")
    @Operation(summary = "Deletes the organizer with the id provided.")
    public ResponseEntity deleteOrganizer(@PathVariable Long id) {
        User user = userService.isAuthorized();

        if (user == null){
            String msg = "This admin cant do that operation.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(msg);
        }
        Organizer organizer = this.adminService.getOrganizer(id);
        if (organizer == null) {
            String msg = "There is no organizer with this id.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(msg);
        }
        this.deleteService.deleteOrganizer(organizer);
        String msg = "The organizer has been deleted.";
        log.warn(msg);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(msg);
    }


    @GetMapping("/athletes")
    @PreAuthorize("hasAuthority('admin:read')")
    @Operation(summary = "Return all the athletes in the database.")
    public ResponseEntity getAllAthletes_Admin() {
        User user = userService.isAuthorized();

        if (user == null){
            String msg = "This admin cant do that operation.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(msg);
        }
        List<Athlete> athletes = adminService.getAllAthletes();
        if (athletes.size() == 0 || athletes == null) {
            String msg = "There is no athletes.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(msg);
        }
        List<AthleteDTO> athleteDTOS = athletes.stream().map(AthleteDTO::new).collect(Collectors.toList());
        log.info("The athletes has successfully been retrieved.");
        return ResponseEntity.ok(athleteDTOS);
    }

    @PutMapping("/athletes/{id}")
    @PreAuthorize("hasAuthority('admin:update')")
    @Operation(summary = "Disable/enable the athlete with the id provided.")
    public ResponseEntity updateAthlete(@PathVariable Long id) {
        User user = userService.isAuthorized();

        if (user == null){
            String msg = "This admin cant do that operation.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(msg);
        }

        if (adminService.getAthlete(id) == null) {
            String msg = "There is no athlete with this id.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(msg);
        }
        adminService.changeStateAthlete(id);
        String msg = "The state of the athlete has changed.";
        log.warn(msg);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(msg);
    }

    @DeleteMapping("/athletes/{id}")
    @PreAuthorize("hasAuthority('admin:delete')")
    @Operation(summary = "Deletes the athlete with the id provided.")
    public ResponseEntity deleteAthlete(@PathVariable Long id) {
        User user = userService.isAuthorized();

        if (user == null){
            String msg = "This admin cant do that operation.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(msg);
        }
        Athlete athlete = this.adminService.getAthlete(id);
        if (athlete == null) {
            String msg = "There is no athlete with this id.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(msg);
        }
        this.deleteService.deleteAthlete(athlete);
        String msg = "The athlete has been deleted.";
        log.warn(msg);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(msg);
    }

    @GetMapping("/tournaments")
    @PreAuthorize("hasAuthority('admin:read')")
    @Operation(summary = "Return all the tournaments in the database.")
    public ResponseEntity getAllTournaments_Admin() {
        User user = userService.isAuthorized();

        if (user == null){
            String msg = "This admin cant do that operation.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(msg);
        }

        List<Tournament> tournaments = adminService.getAllTournaments();
        if (tournaments.size() == 0 || tournaments == null) {
            String msg = "There is no tournaments.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(msg);
        }
        List<TournamentDTO> tournamentDTOS = tournaments.stream().map(TournamentDTO::new).collect(Collectors.toList());
        log.info("The tournaments has successfully been retrieved.");
        return ResponseEntity.ok(tournamentDTOS);
    }

    @PutMapping("/tournaments/{id}")
    @PreAuthorize("hasAuthority('admin:update')")
    @Operation(summary = "Disable/enable the tournaments with the id provided.")
    public ResponseEntity updateTournament(@PathVariable Long id) {
        User user = userService.isAuthorized();

        if (user == null){
            String msg = "This admin cant do that operation.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(msg);
        }

        if (adminService.getTournament(id) == null) {
            String msg = "There is no tournament with this id.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(msg);
        }
        adminService.changeStateTournament(id);
        String msg = "The state of the tournament has changed.";
        log.warn(msg);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(msg);
    }


    @DeleteMapping("/tournaments/{id}")
    @PreAuthorize("hasAuthority('admin:delete')")
    @Operation(summary = "Deletes the tournament with the id provided.")
    public ResponseEntity deleteTournament(@PathVariable Long id) {
        User user = userService.isAuthorized();

        if (user == null){
            String msg = "This admin cant do that operation.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(msg);
        }
        Tournament tournament = this.adminService.getTournament(id);
        if (tournament == null) {
            String msg = "There is no tournament with this id.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(msg);
        }
        this.deleteService.deleteTournament(tournament.getId());
        String msg = "The tournament has been deleted.";
        log.warn(msg);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(msg);
    }
    @GetMapping("/events")
    @PreAuthorize("hasAuthority('admin:read')")
    @Operation(summary = "Return all the events in the database.")
    public ResponseEntity getAllEvents_Admin() {
        User user = userService.isAuthorized();

        if (user == null){
            String msg = "This admin cant do that operation.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(msg);
        }

        List<Tournament> tournaments = adminService.getAllEvents();
        if (tournaments.size() == 0 || tournaments == null) {
            String msg = "There is no tournaments.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(msg);
        }
        List<EventFrontDTO> eventFrontDTOS = tournaments.stream().map(EventFrontDTO::new).collect(Collectors.toList());
        log.info("The events has successfully been retrieved.");
        return ResponseEntity.ok(eventFrontDTOS);
    }

    @PutMapping("/events/{id}")
    @PreAuthorize("hasAuthority('admin:update')")
    @Operation(summary = "Disable/enable the event with the id provided.")
    public ResponseEntity updateEvent(@PathVariable Long id) {
        User user = userService.isAuthorized();

        if (user == null){
            String msg = "This admin cant do that operation.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(msg);
        }

        if (adminService.getTournament(id) == null) {
            String msg = "There is no event with this id.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(msg);
        }
        adminService.changeStateTournament(id);
        String msg = "The state of the event has changed.";
        log.warn(msg);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(msg);
    }

    @DeleteMapping("/events/{id}")
    @PreAuthorize("hasAuthority('admin:delete')")
    @Operation(summary = "Deletes the event with the id provided.")
    public ResponseEntity deleteEvent(@PathVariable Long id) {
        User user = userService.isAuthorized();

        if (user == null){
            String msg = "This admin cant do that operation.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(msg);
        }
        Tournament tournament = this.adminService.getTournament(id);
        if (tournament == null) {
            String msg = "There is no event with this id.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(msg);
        }
        this.deleteService.deleteTournament(tournament.getId());
        String msg = "The event has been deleted.";
        log.warn(msg);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(msg);
    }

    @GetMapping("/sports_types")
    @PreAuthorize("hasAuthority('admin:read')")
    @Operation(summary = "Return all the sports types in the database.")
    public ResponseEntity getAllSports_types_Admin() {
        User user = userService.isAuthorized();

        if (user == null){
            String msg = "This admin cant do that operation.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(msg);
        }

        List<Sports_type> sportsTypes = adminService.getAllSports_types();
        if (sportsTypes.size() == 0 || sportsTypes == null) {
            String msg = "There is no sports types.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(msg);
        }
        List<Sports_typeDTO> sportsTypeDTOS = sportsTypes.stream().map(Sports_typeDTO::new).collect(Collectors.toList());
        log.info("The sports types has successfully been retrieved.");
        return ResponseEntity.ok(sportsTypeDTOS);
    }

    @PutMapping("/sports_types/{id}")
    @PreAuthorize("hasAuthority('admin:update')")
    @Operation(summary = "Disable/enable the sports types with the id provided.")
    public ResponseEntity updateSport_type(@PathVariable Long id) {
        User user = userService.isAuthorized();

        if (user == null){
            String msg = "This admin cant do that operation.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(msg);
        }
        if (adminService.getSport_type(id) == null) {
            String msg = "There is no sport type with this id.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(msg);
        }
        adminService.changeStateSport_type(id);
        String msg = "The state of the sport type has changed.";
        log.warn(msg);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(msg);
    }

    @DeleteMapping("/sports_types/{id}")
    @PreAuthorize("hasAuthority('admin:delete')")
    @Operation(summary = "Deletes the sport type with the id provided.")
    public ResponseEntity deleteSport_type(@PathVariable Long id) {
        User user = userService.isAuthorized();

        if (user == null){
            String msg = "This admin cant do that operation.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(msg);
        }
        Sports_type sportsType = this.adminService.getSport_type(id);
        if (sportsType == null) {
            String msg = "There is no sport type with this id.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(msg);
        }
        this.deleteService.deleteSportsType(sportsType.getId());
        String msg = "The sport type has been deleted.";
        log.warn(msg);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(msg);
    }

    @GetMapping("/inscriptions")
    @PreAuthorize("hasAuthority('admin:read')")
    @Operation(summary = "Return all the inscriptions in the database.")
    public ResponseEntity getAllInscriptionsAdmin() {
        User user = userService.isAuthorized();

        if (user == null){
            String msg = "This admin cant do that operation.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(msg);
        }
        List<Inscription> inscriptions = adminService.getAllInscriptions();
        if (inscriptions.size() == 0 || inscriptions == null) {
            String msg = "There is no inscriptions.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(msg);
        }
        List<InscriptionFrontDTO> inscriptionDTOS = inscriptions.stream().map(InscriptionFrontDTO::new).collect(Collectors.toList());
        log.info("The inscriptions has successfully been retrieved.");
        return ResponseEntity.ok(inscriptionDTOS);
    }

    @PutMapping("/inscriptions/{id}")
    @PreAuthorize("hasAuthority('admin:update')")
    @Operation(summary = "Disable/enable the inscriptions with the id provided.")
    public ResponseEntity updateInscription(@PathVariable Long id) {
        User user = userService.isAuthorized();

        if (user == null){
            String msg = "This admin cant do that operation.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(msg);
        }
        if (adminService.getInscription(id) == null) {
            String msg = "There is no inscription with this id.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(msg);
        }
        adminService.changeStateInscription(id);
        String msg = "The state of the inscription has changed.";
        log.warn(msg);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(msg);
    }


    @DeleteMapping("/inscriptions/{id}")
    @PreAuthorize("hasAuthority('admin:delete')")
    @Operation(summary = "Deletes the inscription the id provided.")
    public ResponseEntity deleteInscription(@PathVariable Long id) {
        User user = userService.isAuthorized();

        if (user == null){
            String msg = "This admin cant do that operation.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(msg);
        }
        Inscription inscription = this.adminService.getInscription(id);
        if (inscription == null) {
            String msg = "There is no inscription with this id.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(msg);
        }
        this.deleteService.deleteInscription(inscription.getId());
        String msg = "The inscription has been deleted.";
        log.warn(msg);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(msg);
    }

    @GetMapping("/watchlists")
    @PreAuthorize("hasAuthority('admin:read')")
    @Operation(summary = "Return all the watchlists in the database.")
    public ResponseEntity getAllWatchlists_Admin() {
        User user = userService.isAuthorized();

        if (user == null){
            String msg = "This admin cant do that operation.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(msg);
        }

        List<Watchlist> watchlists = adminService.getAllWatchlists();
        if (watchlists.size() == 0 || watchlists == null) {
            String msg = "There is no watchlists.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(msg);
        }
        List<WatchlistFrontDTO> watchlistDTOS = watchlists.stream().map(WatchlistFrontDTO::new).collect(Collectors.toList());
        log.info("The watchlists has successfully been retrieved.");
        return ResponseEntity.ok(watchlistDTOS);
    }

    @PutMapping("/watchlists/{id}")
    @PreAuthorize("hasAuthority('admin:update')")
    @Operation(summary = "Disable/enable the watchlists with the id provided.")
    public ResponseEntity updateWatchlist(@PathVariable Long id) {
        User user = userService.isAuthorized();

        if (user == null){
            String msg = "This admin cant do that operation.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(msg);
        }
        if (adminService.getWatchlist(id) == null) {
            String msg = "There is no watchlist with this id.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(msg);
        }
        adminService.changeStateWatchlist(id);
        String msg = "The state of the watchlist has changed.";
        log.warn(msg);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(msg);
    }

    @DeleteMapping("/watchlists/{id}")
    @PreAuthorize("hasAuthority('admin:delete')")
    @Operation(summary = "Deletes the watchlist the id provided.")
    public ResponseEntity deleteWatchlist(@PathVariable Long id) {
        User user = userService.isAuthorized();

        if (user == null){
            String msg = "This admin cant do that operation.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(msg);
        }
        Watchlist watchlist = this.adminService.getWatchlist(id);
        if (watchlist == null) {
            String msg = "There is no watchlist with this id.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(msg);
        }
        this.deleteService.deleteWatchlist(watchlist.getId());
        String msg = "The watchlist has been deleted.";
        log.warn(msg);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(msg);
    }

    @GetMapping("/tokens")
    @PreAuthorize("hasAuthority('admin:read')")
    @Operation(summary = "Return all the tokens in the database.")
    public ResponseEntity getAllTokens_Admin() {
        User user = userService.isAuthorized();

        if (user == null){
            String msg = "This admin cant do that operation.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(msg);
        }

        List<Token> tokens = adminService.getAllTokens();
        if (tokens.size() == 0 || tokens == null) {
            String msg = "There is no tokens.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(msg);
        }
        List<TokenDTO> tokenDTOS = tokens.stream().map(TokenDTO::new).collect(Collectors.toList());
        log.info("The tokens has successfully been retrieved.");
        return ResponseEntity.ok(tokenDTOS);
    }

    @PutMapping("/tokens/{id}")
    @PreAuthorize("hasAuthority('admin:update')")
    @Operation(summary = "Disable/enable the token with the id provided.")
    public ResponseEntity updateToken(@PathVariable Integer id) {
        User user = userService.isAuthorized();

        if (user == null){
            String msg = "This admin cant do that operation.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(msg);
        }
        if (adminService.getToken(id) == null) {
            String msg = "There is no token with this id.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(msg);
        }
        adminService.changeStateToken(id);
        String msg = "The state of the token has changed.";
        log.info(msg);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(msg);
    }

    @DeleteMapping("/tokens/{id}")
    @PreAuthorize("hasAuthority('admin:delete')")
    @Operation(summary = "Deletes the token the id provided.")
    public ResponseEntity deleteToken(@PathVariable Integer id) {
        User user = userService.isAuthorized();

        if (user == null){
            String msg = "This admin cant do that operation.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(msg);
        }
        Token token = this.adminService.getToken(id);
        if (token == null) {
            String msg = "There is no token with this id.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(msg);
        }
        this.deleteService.deleteToken(token.getId());
        String msg = "The token has been deleted.";
        log.warn(msg);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(msg);
    }


    @PutMapping("/password")
    @Operation(summary = "Changes the password for the admin")
    @PreAuthorize("hasAuthority('admin:update')")
    public ResponseEntity changePassword(@RequestBody PasswordChangeDTO passwordChangeDTO){
        Admin admin = this.adminService.getAdmin(passwordChangeDTO.getId_user());

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Admin admin_logged = this.adminService.getAdminByUsername(username);

        if (admin != null && admin_logged.getId() == admin.getId() ){
            System.out.println(passwordChangeDTO.toString());
            if (passwordChangeDTO.getPassword().equals(passwordChangeDTO.getConfirmpassword())){
                admin = this.adminService.setPasswordHashed(admin, passwordChangeDTO.getPassword());
                this.adminService.updateAdmin(admin);
                if (admin != null ){
                    String msg = "The password has been changed.";
                    log.warn(msg);
                    return ResponseEntity
                            .status(HttpStatus.OK)
                            .body(msg);
                }else{
                    String msg = "The password has not been changed.";
                    log.warn(msg);
                    return ResponseEntity
                            .status(HttpStatus.BAD_REQUEST)
                            .body(msg);
                }
            }else{
                String msg = "The passwords provided doesnt match.";
                log.warn(msg);
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(msg);
            }
        }else {
            String msg = "The admin with the id provided doesnt exist or doesnt match with the user logged.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(msg);
        }
    }

    @GetMapping("/disableds")
    @PreAuthorize("hasAuthority('admin:read')")
    @Operation(summary = "Gets a summary with the entities disabled on the database.")
    public ResponseEntity countDisableds() {
        User user = userService.isAuthorized();

        if (user == null){
            String msg = "This admin cant do that operation.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(msg);
        }
        DeleteDisabledSummaryFrontDTO disabledsSummary = this.deleteService.countDisableds();
        String msg = "There is the summary";
        log.info(msg);
        return ResponseEntity.ok(disabledsSummary);
    }
    @DeleteMapping("/disableds")
    @PreAuthorize("hasAuthority('admin:delete')")
    @Operation(summary = "Deletes all the entities disabled.")
    public ResponseEntity deleteDisableds() {
        User user = userService.isAuthorized();

        if (user == null){
            String msg = "This admin cant do that operation.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(msg);
        }
        this.deleteService.deleteDisableds();
        String msg = "All the entities disabled have been deleted";
        log.info(msg);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(msg);
    }
}
