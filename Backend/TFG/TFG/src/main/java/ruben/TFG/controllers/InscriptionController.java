package ruben.TFG.controllers;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ruben.TFG.controllers.DTO.InscriptionDTO;
import ruben.TFG.model.Inscription;
import ruben.TFG.model.Tournament;
import ruben.TFG.model.User;
import ruben.TFG.service.*;

@RestController
@Slf4j
@RequestMapping("/api/inscriptions")
public class InscriptionController {

    private final InscriptionService inscriptionService;
    private final TournamentService tournamentService;
    private final UserService userService;
    /**
     * Constructor of the class
     * @param inscriptionService, the service to manage the inscription's data
     */
    public InscriptionController(InscriptionService inscriptionService, TournamentService tournamentService, UserService userService) {
        this.inscriptionService = inscriptionService;
        this.userService = userService;
        this.tournamentService = tournamentService;
    }



    @GetMapping("/{id}")
    @ApiOperation("Get a inscription by his id")
    public ResponseEntity<InscriptionDTO> getInscriptionById(@ApiParam("The id of the inscription") @PathVariable(name = "id") Long id) {

        Inscription inscription = inscriptionService.getInscription(id);
        if (inscription != null) {
            log.info("The inscription has been found");
            return ResponseEntity.ok(new InscriptionDTO(inscription));
        }
        else {
            log.warn("The inscription by id has not been found");
            return ResponseEntity.notFound().build();
        }
    }


    @PutMapping("/{id}")
    @ApiOperation("Change a inscription by his id.")
    public ResponseEntity<InscriptionDTO> updateInscription(@ApiParam("The id of the inscription")  @PathVariable Long id, @ApiParam("Modified inscription object") @RequestBody Inscription inscription) {
        if (!id.equals(inscription.getId())) {
            log.warn("Bad request , the id given in the path doesnt match with the id on the inscription");
            return ResponseEntity.badRequest().build();
        }
        if (inscription.getTournament().isEnabled() && inscription.getUser().isEnabled()){
            log.info("Inscription updated successfully");
            return ResponseEntity.ok(InscriptionDTO.fromInscription(inscriptionService.saveInscription(inscription)));
        }else{
            log.warn("Bad request , the tournament  or the user  is disabled or doesnt exit.");
            return ResponseEntity.badRequest().build();
        }

    }

    @PostMapping("")
    @ApiOperation("Create a inscription.")
    public ResponseEntity<InscriptionDTO> createInscription(@ApiParam("New inscription object") @RequestBody InscriptionDTO inscriptionDTO ) {
        Tournament tournament = tournamentService.getTournament(inscriptionDTO.getTournament());
        User user = userService.getUser(inscriptionDTO.getUser());
        Inscription inscription = new Inscription(tournament,user);
        if (inscription.getTournament().isEnabled() && inscription.getUser().isEnabled()){
            log.info("Inscription created successfully");
            return ResponseEntity.ok(InscriptionDTO.fromInscription(inscriptionService.saveInscription(inscription)));
        }else{
            log.warn("Bad request , the tournament  or the user  is disabled or doesnt exit.");
            return ResponseEntity.badRequest().build();
        }

    }



}
