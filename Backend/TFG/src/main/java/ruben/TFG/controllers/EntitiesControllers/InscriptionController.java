package ruben.TFG.controllers.EntitiesControllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ruben.TFG.model.DTO.InscriptionDTO;
import ruben.TFG.model.Entities.Athlete;
import ruben.TFG.model.Entities.Inscription;
import ruben.TFG.model.Entities.Tournament;
import ruben.TFG.service.EntitiesServices.AthleteService;
import ruben.TFG.service.EntitiesServices.InscriptionService;
import ruben.TFG.service.EntitiesServices.TournamentService;

@RestController
@Slf4j
@RequestMapping("/api/inscriptions")
public class InscriptionController {

    private final InscriptionService inscriptionService;
    private final TournamentService tournamentService;
    private final AthleteService athleteService;
    /**
     * Constructor of the class
     * @param inscriptionService, the service to manage the inscription's data
     */
    public InscriptionController(InscriptionService inscriptionService, TournamentService tournamentService, AthleteService athleteService) {
        this.inscriptionService = inscriptionService;
        this.athleteService = athleteService;
        this.tournamentService = tournamentService;
    }



    @GetMapping("/{id}")
    public ResponseEntity<InscriptionDTO> getInscriptionById(@PathVariable(name = "id") Long id) {

        Inscription inscription = inscriptionService.getInscription(id);
        if (inscription != null) {
            return ResponseEntity.ok(new InscriptionDTO(inscription));
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<InscriptionDTO> updateInscription(@PathVariable Long id, @RequestBody Inscription inscription) {
        if (!id.equals(inscription.getId())) {
            return ResponseEntity.badRequest().build();
        }
        if (inscription.getTournament().isEnabled() && inscription.getUser().isEnabled()){
            return ResponseEntity.ok(InscriptionDTO.fromInscription(inscriptionService.saveInscription(inscription)));
        }else{
            return ResponseEntity.badRequest().build();
        }

    }

    @PostMapping("")
    public ResponseEntity<InscriptionDTO> createInscription(@RequestBody InscriptionDTO inscriptionDTO ) {
        Tournament tournament = tournamentService.getTournament(inscriptionDTO.getTournament());
        Athlete athlete = athleteService.getAthlete(inscriptionDTO.getUser());
        Inscription inscription = new Inscription(tournament, athlete);
        if (inscription.getTournament().isEnabled() && inscription.getUser().isEnabled()){
            log.info("Inscription created successfully");
            return ResponseEntity.ok(InscriptionDTO.fromInscription(inscriptionService.saveInscription(inscription)));
        }else{
            log.warn("Bad request , the tournament  or the athlete  is disabled or doesnt exit.");
            return ResponseEntity.badRequest().build();
        }

    }



}
