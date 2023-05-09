package ruben.TFG.controllers.EntitiesControllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ruben.TFG.model.DTO.Entities.InscriptionDTO;
import ruben.TFG.model.DTO.Entities.Sports_typeDTO;
import ruben.TFG.model.Entities.*;
import ruben.TFG.service.EntitiesServices.AthleteService;
import ruben.TFG.service.EntitiesServices.InscriptionService;
import ruben.TFG.service.EntitiesServices.TournamentService;
import ruben.TFG.service.EntitiesServices.UserService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/api/inscriptions")
@AllArgsConstructor
@Tag(name="Inscriptions")
public class InscriptionController {

    private final InscriptionService inscriptionService;
    private final TournamentService tournamentService;
    private final AthleteService athleteService;
    private final UserService userService;



    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('athlete:read')")
    @Operation(summary = "Return the inscription with the id provided.")
    public ResponseEntity getInscriptionById(@PathVariable(name = "id") Long id) {
        User user = userService.isAuthorized();

        if (user == null){
            String msg = "This user cant do that operation.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(msg);
        }

        Inscription inscription = inscriptionService.getInscription(id);
        if (inscription == null) {
            if (inscription.isEnabled() == false){
                String msg = "The inscription that you asked is disabled.";
                log.warn(msg);
                return ResponseEntity
                        .status(HttpStatus.FORBIDDEN)
                        .body(msg);
            }else {
                String msg = "The inscription that you asked doesnt exit.";
                log.warn(msg);
                return ResponseEntity
                        .status(HttpStatus.NO_CONTENT)
                        .body(msg);
            }
        }
        else {
            log.info("The inscription has successfully been retrieved.");
            return ResponseEntity.ok(inscription);
        }
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('athlete:update')")
    @Operation(summary = "Update a inscription with the data provided.")
    public ResponseEntity updateInscription(@PathVariable Long id, @RequestBody Inscription inscription) {
        User user = userService.isAuthorized();

        if (user == null){
            String msg = "This user cant do that operation.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(msg);
        }
        if (!id.equals(inscription.getId())) {
            String msg = "The id that you give doesnt match with the id of the inscription.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .body(msg);
        }
        if (!inscription.getTournament().isEnabled() || !inscription.getUser().isEnabled()){
            if (!inscription.getTournament().isEnabled()) {
                String msg = "The tournament of the inscription is disabled.";
                log.warn(msg);
                return ResponseEntity
                        .status(HttpStatus.NO_CONTENT)
                        .body(msg);
            }else {
                String msg = "The user of the inscription is disabled.";
                log.warn(msg);
                return ResponseEntity
                        .status(HttpStatus.NO_CONTENT)
                        .body(msg);
            }
        }else{
            log.info("The inscription has successfully been updated.");
            return ResponseEntity.ok(InscriptionDTO.fromInscription(inscriptionService.saveInscription(inscription)));
        }

    }

    @PostMapping("")
    @PreAuthorize("hasAuthority('athlete:create')")
    @Operation(summary = "Create a inscription with the data provided.")
    public ResponseEntity createInscription(@RequestBody InscriptionDTO inscriptionDTO ) {
        User user = userService.isAuthorized();

        if (user == null){
            String msg = "This user cant do that operation.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(msg);
        }

        Tournament tournament = tournamentService.getTournament(inscriptionDTO.getTournament());
        Athlete athlete = athleteService.getAthlete(inscriptionDTO.getUser());
        Inscription inscription = new Inscription(tournament, athlete);
        if (!inscription.getTournament().isEnabled() || !inscription.getUser().isEnabled()){
            if (!inscription.getTournament().isEnabled()) {
                String msg = "The tournament of the inscription is disabled.";
                log.warn(msg);
                return ResponseEntity
                        .status(HttpStatus.NO_CONTENT)
                        .body(msg);
            }else {
                String msg = "The user of the inscription is disabled.";
                log.warn(msg);
                return ResponseEntity
                        .status(HttpStatus.NO_CONTENT)
                        .body(msg);
            }
        }else{
            log.info("The inscription has successfully been saved.");
            return ResponseEntity.ok(InscriptionDTO.fromInscription(inscriptionService.saveInscription(inscription)));
        }

    }

    @GetMapping("")
    @PreAuthorize("hasAuthority('athlete:read')")
    @Operation(summary = "Return all the enabled inscriptions.")
    public ResponseEntity getAllInscriptions() {
        User user = userService.isAuthorized();

        if (user == null){
            String msg = "This user cant do that operation.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(msg);
        }
        List<Inscription> inscriptions = inscriptionService.getEnabledInscriptions(user.getId());
        if (inscriptions.size() == 0 || inscriptions == null) {
            String msg = "There is no inscriptions.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(msg);
        }

        // Convert the list of sport types to a list of Sports_typeDTOs
        List<InscriptionDTO> inscriptionDTOS = inscriptions.stream().map(InscriptionDTO::new).collect(Collectors.toList());
        log.info("The inscriptions has been retrieved.");
        return ResponseEntity.ok(inscriptionDTOS);
    }



}
