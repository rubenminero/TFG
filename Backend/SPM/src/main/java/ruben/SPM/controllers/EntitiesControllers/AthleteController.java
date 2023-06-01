package ruben.SPM.controllers.EntitiesControllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import ruben.SPM.model.DTO.Auth.AuthResponseDTO;
import ruben.SPM.model.DTO.Entities.InscriptionDTO;
import ruben.SPM.model.DTO.Entities.AthleteDTO;
import ruben.SPM.model.DTO.Entities.WatchlistDTO;
import ruben.SPM.model.DTO.Front.InscriptionFrontDTO;
import ruben.SPM.model.DTO.Front.WatchlistFrontDTO;
import ruben.SPM.model.Entities.Athlete;
import ruben.SPM.model.Entities.Inscription;
import ruben.SPM.model.Entities.User;
import ruben.SPM.model.Entities.Watchlist;
import ruben.SPM.service.Auth.AuthService;
import ruben.SPM.service.EntitiesServices.InscriptionService;
import ruben.SPM.service.EntitiesServices.AthleteService;

import lombok.extern.slf4j.Slf4j;
import ruben.SPM.service.EntitiesServices.UserService;
import ruben.SPM.service.EntitiesServices.WatchlistService;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/api/athletes")
@AllArgsConstructor
@Tag(name="Athletes")
@CrossOrigin(origins = {"http://localhost:4200"})
public class AthleteController {

    private final AthleteService athleteService;

    private final InscriptionService inscriptionService;
    private final WatchlistService watchlistService;
    private final UserService userService;
    private final AuthService authService;

    @GetMapping("")
    @PreAuthorize("hasAuthority('athlete:read')")
    @Operation(summary = "Return all the enabled athletes in the database.")
    public ResponseEntity<?> getAllAthletes() {
        User user = userService.isAuthorized();

        if (user == null){
            String msg = "This user cant do that operation.";
            log.warn(msg);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(msg);
        }
        List<Athlete> athletes = athleteService.getEnabledAthletes();
        if (athletes.size() == 0 ) {
            String msg = "There is no athletes.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(msg);
        }

        List<AthleteDTO> athleteDTOS = athletes.stream().map(AthleteDTO::new).collect(Collectors.toList());
        log.info("The athletes has successfully been retrieved.");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(athleteDTOS);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('athlete:read')")
    @Operation(summary = "Return a athletes in the database.")
    public ResponseEntity getAthleteById(@PathVariable(name = "id") Long id) {
        User user = userService.isAuthorized();

        if (user == null){
            String msg = "This user cant do that operation.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(msg);
        }

        Athlete athlete = athleteService.getAthlete(id);
        if (athlete != null && athlete.isEnabled()) {
            log.info("The athlete has successfully been retrieved.");
            return ResponseEntity.ok(new AthleteDTO(athlete));
        }
        else {
            if (user.isEnabled() == false){
                String msg = "The athlete that you asked is disabled.";
                log.warn(msg);
                return ResponseEntity
                        .status(HttpStatus.FORBIDDEN)
                        .body(msg);
            }else {
                String msg = "The athlete that you asked doesnt exist.";
                log.warn(msg);
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(msg);
            }

        }
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('athlete:update')")
    @Operation(summary = "Update a athlete with the data provided.")
    public ResponseEntity updateAthlete(@PathVariable Long id, @RequestBody Athlete athlete) {
        User user = userService.isAuthorized();
        Athlete athlete_saved = this.athleteService.getAthlete(id);
        if (user == null){
            String msg = "This user cant do that operation.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(msg);
        }
        if (athlete_saved == null){
            String msg = "There is no user with that data.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(msg);
        }
        Boolean username_check = userService.validUsername(athlete.getUsername());
        if (athlete_saved.getUsername().equals(athlete.getUsername()) && athlete.getId() == athlete_saved.getId()){
            log.info("The athlete has successfully been updated.");
            return ResponseEntity.ok(AthleteDTO.fromUser(athleteService.updateAthlete(athlete,athlete_saved)));
        }else {
            if (username_check){
                String msg = "This username is already taken.";
                log.warn(msg);
                return ResponseEntity
                        .status(HttpStatus.FORBIDDEN)
                        .body(msg);
            }
            if (!id.equals(athlete.getId())) {
                String msg = "The id that you give doesnt match with the id of the user.";
                log.warn(msg);
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(msg);
            }

            if (athleteService.getAthlete(id) == null) {
                String msg = "Bad request ,there is no athlete for that id.";
                log.warn(msg);
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(msg);
            }
            log.info("The athlete has successfully been updated.");
            return ResponseEntity.ok(AthleteDTO.fromUser(athleteService.updateAthlete(athlete,athlete_saved)));
        }
    }

    @PostMapping("")
    @PreAuthorize("hasAuthority('athlete:create')")
    @Operation(summary = "Create a athlete with the data provided.")
    public ResponseEntity createAthlete(@RequestBody Athlete athlete) {
        User user = userService.isAuthorized();

        if (user == null){
            String msg = "This user cant do that operation.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(msg);
        }
        Boolean username_check = userService.validUsername(athlete.getUsername());
        if (username_check){
            String msg = "This username is already taken.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(msg);
        }
        log.info("The athletes has successfully been created.");
        return ResponseEntity.ok(AthleteDTO.fromUser(athleteService.saveAthlete(athlete)));
    }

    @GetMapping("/inscriptions/{id}")
    @PreAuthorize("hasAuthority('athlete:read')")
    @Operation(summary = "Return the inscriptions for that user.")
    public ResponseEntity getInscriptionById(@PathVariable(name = "id") Long id) {
        User user = userService.isAuthorized();

        if (user == null){
            String msg = "This user cant do that operation.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(msg);
        }

        List<Inscription> inscriptions = inscriptionService.getEnabledInscriptions(id);
        if (inscriptions.size() == 0 || inscriptions == null) {
            if (user.isEnabled() == false){
                String msg = "The athlete that you asked is disabled.";
                log.warn(msg);
                return ResponseEntity
                        .status(HttpStatus.FORBIDDEN)
                        .body(msg);
            }else {
                String msg = "The athlete that you asked doesnt have inscriptions.";
                log.warn(msg);
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(msg);
            }
        }
        else {
            List<InscriptionFrontDTO> inscriptionDTOS = inscriptions.stream().map(InscriptionFrontDTO::new).collect(Collectors.toList());
            log.info("The inscriptions for that athlete has successfully been retrieved.");
            return ResponseEntity.ok(inscriptionDTOS);
        }
    }

    @GetMapping("/watchlists/{id}")
    @PreAuthorize("hasAuthority('athlete:read')")
    @Operation(summary = "Return the watchlists for that user.")
    public ResponseEntity getWatchlistById(@PathVariable(name = "id") Long id) {
        User user = userService.isAuthorized();

        if (user == null){
            String msg = "This user cant do that operation.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(msg);
        }

        List<Watchlist> watchlists = watchlistService.getEnabledWatchlists(id);
        if (watchlists.size() == 0 || watchlists == null) {
            if (user.isEnabled() == false){
                String msg = "The athlete that you asked is disabled.";
                log.warn(msg);
                return ResponseEntity
                        .status(HttpStatus.FORBIDDEN)
                        .body(msg);
            }else {
                String msg = "The athlete that you asked doesnt have watchlists.";
                log.warn(msg);
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(msg);
            }
        }
        else {
            List<WatchlistFrontDTO> watchlistDTOs = watchlists.stream().map(WatchlistFrontDTO::new).collect(Collectors.toList());
            log.info("The watchlists for that athlete has successfully been retrieved.");
            return ResponseEntity.ok(watchlistDTOs);
        }
    }



}
