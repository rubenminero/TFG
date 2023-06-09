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
import ruben.SPM.model.DTO.Entities.AthleteDTO;
import ruben.SPM.model.DTO.Entities.InscriptionDTO;
import ruben.SPM.model.DTO.Front.InscriptionFrontDTO;
import ruben.SPM.model.Entities.*;
import ruben.SPM.service.EntitiesServices.AthleteService;
import ruben.SPM.service.EntitiesServices.InscriptionService;
import ruben.SPM.service.EntitiesServices.TournamentService;
import ruben.SPM.service.EntitiesServices.UserService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/api/inscriptions")
@AllArgsConstructor
@Tag(name="Inscriptions")
@CrossOrigin(origins = {"http://localhost:4200"})
public class InscriptionController {

    private final InscriptionService inscriptionService;
    private final TournamentService tournamentService;
    private final AthleteService athleteService;
    private final UserService userService;



    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('athlete:read')")
    @Operation(summary = "Return the inscription with the id provided.")
    public ResponseEntity getInscriptionById(@PathVariable(name = "id") Long id) {
        Inscription inscription = inscriptionService.getInscription(id);
        if (inscription != null) {
            if (inscription.isEnabled() == false){
                String msg = "The inscription that you asked is disabled.";
                log.warn(msg);
                return ResponseEntity
                        .status(HttpStatus.FORBIDDEN)
                        .body(msg);
            }else {
                log.info("The inscription has successfully been retrieved.");
                return ResponseEntity.ok(inscription);
            }
        }
        else {
            String msg = "The inscription that you asked doesnt exit.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(msg);
        }
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('athlete:update')")
    @Operation(summary = "Update a inscription with the data provided.")
    public ResponseEntity updateInscription(@PathVariable Long id, @RequestBody InscriptionDTO inscriptionDTO) {
        Inscription inscription = inscriptionService.getInscription(inscriptionDTO.getId());
        if (!id.equals(inscriptionDTO.getId())) {
            String msg = "The id that you give doesnt match with the id of the inscription.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(msg);
        }

        if (inscription == null) {
            String msg = "Bad request ,there is no inscription for that id.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(msg);
        }
        Tournament tournament = tournamentService.getTournament(inscriptionDTO.getTournament());
        Athlete athlete = athleteService.getAthlete(inscriptionDTO.getAthlete());

        if (inscriptionService.validInscription(athlete,tournament)){
            String msg = "You already have this tournament in your watchlists.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(msg);
        }

        if (inscription.getTournament() == null || inscription.getAthlete() == null){
            if (inscription.getTournament() == null ) {
                String msg = "The tournament of the inscription doesnt exist.";
                log.warn(msg);
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(msg);
            }else {
                String msg = "The user of the inscription doesnt exist.";
                log.warn(msg);
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(msg);
            }
        }else{
            if (!inscription.getTournament().isEnabled() || !inscription.getAthlete().isEnabled()){
                if (!inscription.getTournament().isEnabled()) {
                    String msg = "The tournament of the inscription is disabled.";
                    log.warn(msg);
                    return ResponseEntity
                            .status(HttpStatus.NOT_FOUND)
                            .body(msg);
                }else {
                    String msg = "The user of the inscription is disabled.";
                    log.warn(msg);
                    return ResponseEntity
                            .status(HttpStatus.NOT_FOUND)
                            .body(msg);
                }
            }else{
                log.info("The inscription has successfully been updated.");
                return ResponseEntity.ok(InscriptionDTO.fromInscription(inscriptionService.saveInscription(inscription)));
            }
        }

    }

    @PostMapping("")
    @PreAuthorize("hasAuthority('athlete:create')")
    @Operation(summary = "Create a inscription with the data provided.")
        public ResponseEntity createInscription(@RequestBody InscriptionFrontDTO inscriptionFrontDTO) {
        Tournament tournament = tournamentService.getTournament(inscriptionFrontDTO.getTournament_id());
        Athlete athlete = athleteService.getAthlete(inscriptionFrontDTO.getAthlete_id());
        Inscription inscription = InscriptionFrontDTO.toInscription(inscriptionFrontDTO,tournament,athlete);
        inscription.setEnabled(true);

        if (inscriptionService.validInscription(athlete,tournament)){
            String msg = "You already have this tournament in your inscriptions.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(msg);
        }

        if (tournament == null || athlete == null){
            if (tournament == null ) {
                String msg = "The tournament of the inscription doesnt exist.";
                log.warn(msg);
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(msg);
            }else {
                String msg = "The user of the inscription doesnt exist.";
                log.warn(msg);
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(msg);
            }
        }else{
            if (!tournament.isEnabled() || !athlete.isEnabled()){
                if (!tournament.isEnabled()) {
                    String msg = "The tournament of the inscription is disabled.";
                    log.warn(msg);
                    return ResponseEntity
                            .status(HttpStatus.NOT_FOUND)
                            .body(msg);
                }else {
                    String msg = "The user of the inscription is disabled.";
                    log.warn(msg);
                    return ResponseEntity
                            .status(HttpStatus.NOT_FOUND)
                            .body(msg);
                }
            }else{
                log.info("The inscription has successfully been saved.");
                return ResponseEntity.ok(InscriptionDTO.fromInscription(inscriptionService.saveInscription(inscription)));
            }
        }

    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('athlete:delete')")
    @Operation(summary = "Disable/enable the inscription with the id provided.")
    public ResponseEntity deleteInscription(@PathVariable Long id) {
        User user = userService.isAuthorized();
        Inscription inscription = inscriptionService.getInscription(id);
        if (inscription == null) {
            String msg = "There is no inscription with this id.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(msg);
        }
        Tournament tournament = inscription.getTournament();
        Organizer organizer = tournament.getOrganizer();
        Athlete athlete = inscription.getAthlete();
        if (user.getId() != athlete.getId() && organizer.getId() != user.getId()){
            String msg = "The inscription doesnt belong to your user.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(msg);
        }
        inscriptionService.deleteInscription(inscription);
        tournamentService.moreCapacity(tournament);
        String msg = "The inscription has been deleted.";
        log.info(msg);
        InscriptionFrontDTO inscriptionFrontDTO = new InscriptionFrontDTO(inscription);
        return ResponseEntity.ok(inscriptionFrontDTO);
    }
}
