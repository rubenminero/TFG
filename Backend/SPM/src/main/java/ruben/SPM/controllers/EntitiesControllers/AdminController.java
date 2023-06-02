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
import ruben.SPM.model.DTO.Auth.PasswordChangeDTO;
import ruben.SPM.model.DTO.Entities.*;
import ruben.SPM.model.DTO.Entities.WatchlistDTO;
import ruben.SPM.model.Entities.*;
import ruben.SPM.service.EntitiesServices.AdminService;
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
    @DeleteMapping("/organizers/{id}")
    @PreAuthorize("hasAuthority('admin:delete')")
    @Operation(summary = "Disable/enable the organizer with the id provided.")
    public ResponseEntity deleteOrganizer(@PathVariable Long id) {
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

    @DeleteMapping("/athletes/{id}")
    @PreAuthorize("hasAuthority('admin:delete')")
    @Operation(summary = "Disable/enable the athlete with the id provided.")
    public ResponseEntity deleteAthlete(@PathVariable Long id) {
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

    @DeleteMapping("/tournaments/{id}")
    @PreAuthorize("hasAuthority('admin:delete')")
    @Operation(summary = "Disable/enable the tournaments with the id provided.")
    public ResponseEntity deleteTournament(@PathVariable Long id) {
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

    @DeleteMapping("/sports_types/{id}")
    @PreAuthorize("hasAuthority('admin:delete')")
    @Operation(summary = "Disable/enable the sports types with the id provided.")
    public ResponseEntity deleteSport_type(@PathVariable Long id) {
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
        List<InscriptionDTO> inscriptionDTOS = inscriptions.stream().map(InscriptionDTO::new).collect(Collectors.toList());
        log.info("The inscriptions has successfully been retrieved.");
        return ResponseEntity.ok(inscriptionDTOS);
    }

    @DeleteMapping("/inscriptions/{id}")
    @PreAuthorize("hasAuthority('admin:delete')")
    @Operation(summary = "Disable/enable the inscriptions with the id provided.")
    public ResponseEntity deleteInscription(@PathVariable Long id) {
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
        List<WatchlistDTO> watchlistDTOS = watchlists.stream().map(WatchlistDTO::new).collect(Collectors.toList());
        log.info("The watchlists has successfully been retrieved.");
        return ResponseEntity.ok(watchlistDTOS);
    }

    @DeleteMapping("/watchlists/{id}")
    @PreAuthorize("hasAuthority('admin:delete')")
    @Operation(summary = "Disable/enable the watchlists with the id provided.")
    public ResponseEntity deleteWatchlist(@PathVariable Long id) {
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
}
