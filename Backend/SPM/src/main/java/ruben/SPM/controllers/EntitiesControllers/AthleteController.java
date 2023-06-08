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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import ruben.SPM.model.DTO.Auth.AuthResponseDTO;
import ruben.SPM.model.DTO.Auth.PasswordChangeDTO;
import ruben.SPM.model.DTO.Entities.InscriptionDTO;
import ruben.SPM.model.DTO.Entities.AthleteDTO;
import ruben.SPM.model.DTO.Entities.OrganizerDTO;
import ruben.SPM.model.DTO.Entities.WatchlistDTO;
import ruben.SPM.model.DTO.Front.InscriptionFrontDTO;
import ruben.SPM.model.DTO.Front.WatchlistFrontDTO;
import ruben.SPM.model.Entities.*;
import ruben.SPM.model.Whitelist.Role;
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
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('athlete:read')")
    @Operation(summary = "Return a athletes in the database.")
    public ResponseEntity getAthleteById(@PathVariable(name = "id") Long id) {
        Athlete athlete = athleteService.getAthlete(id);
        if (athlete != null && athlete.isEnabled()) {
            log.info("The athlete has successfully been retrieved.");
            return ResponseEntity.ok(new AthleteDTO(athlete));
        }
        else {
            String msg = "The athlete that you asked doesnt exist.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(msg);
        }
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('athlete:update')")
    @Operation(summary = "Update a athlete with the data provided.")
    public ResponseEntity updateAthlete(@PathVariable Long id, @RequestBody Athlete athlete) {
        Athlete athlete_saved = this.athleteService.getAthlete(id);
        if (athlete_saved == null){
            String msg = "There is no user with that data.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(msg);
        }
        Boolean username_check = userService.validUsername(athlete.getUsername());
        if (athlete_saved.getUsername().equals(athlete.getUsername()) && athlete.getId().equals(athlete_saved.getId())){
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
            log.info("The athlete has successfully been updated.");
            return ResponseEntity.ok(AthleteDTO.fromUser(athleteService.updateAthlete(athlete,athlete_saved)));
        }
    }

    @GetMapping("/inscriptions/{id}")
    @PreAuthorize("hasAuthority('athlete:read')")
    @Operation(summary = "Return the inscriptions for that user.")
    public ResponseEntity getInscriptionById(@PathVariable(name = "id") Long id) {
        List<Inscription> inscriptions = inscriptionService.getEnabledInscriptions(id);
        if (inscriptions.size() == 0 || inscriptions == null) {
            String msg = "The athlete that you asked doesnt have inscriptions.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(msg);
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
        List<Watchlist> watchlists = watchlistService.getEnabledWatchlists(id);
        if (watchlists.size() == 0 || watchlists == null) {
                String msg = "The athlete that you asked doesnt have watchlists.";
                log.warn(msg);
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(msg);
        } else {
            List<WatchlistFrontDTO> watchlistDTOs = watchlists.stream().map(WatchlistFrontDTO::new).collect(Collectors.toList());
            log.info("The watchlists for that athlete has successfully been retrieved.");
            return ResponseEntity.ok(watchlistDTOs);
        }
    }

    @PutMapping("/password")
    @Operation(summary = "Changes the password for the athlete")
    @PreAuthorize("hasAuthority('athlete:update')")
    public ResponseEntity changePassword(@RequestBody PasswordChangeDTO passwordChangeDTO){
        User user = userService.isAuthorized();
        Athlete athlete = this.athleteService.getAthlete(passwordChangeDTO.getId_user());
        Athlete athlete_logged = athleteService.getAthlete(user.getId());
        if (athlete != null && athlete_logged.getId().equals(athlete.getId()) && passwordEncoder.matches(passwordChangeDTO.getOldpassword(),athlete_logged.getPassword())){
            if (passwordChangeDTO.getPassword().equals(passwordChangeDTO.getConfirmpassword())){
                athlete = this.athleteService.setPasswordHashed(athlete, passwordChangeDTO.getPassword());
                this.athleteService.updateAthlete(athlete);
                if (athlete != null ){
                    String msg = "The password has been changed.";
                    log.info(msg);
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
            String msg = "The user with the id provided doesnt exist or doesnt match with the user logged.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(msg);
        }
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('athlete:delete')")
    @Operation(summary = "Delete the athlete with the id provided.")
    public ResponseEntity deleteAthlete(@PathVariable Long id) {
        Athlete athlete = athleteService.getAthlete(id);
        User user = userService.isAuthorized();
        if (athlete == null){
            String msg = "This user cant do that operation.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(msg);
        }
        if (athlete.getId() != user.getId()){
            String msg = "The id provided doesnt doesnt belong to your user.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(msg);
        }
        athleteService.deleteAthlete(athlete);
        String msg = "The user has been deleted.";
        log.info(msg);
        AthleteDTO athleteDTO = new AthleteDTO(athlete);
        return ResponseEntity.ok(athleteDTO);
    }

}
