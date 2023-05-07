package ruben.TFG.controllers.EntitiesControllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import ruben.TFG.model.DTO.InscriptionDTO;
import ruben.TFG.model.DTO.AthleteDTO;
import ruben.TFG.model.DTO.WatchlistDTO;
import ruben.TFG.model.Entities.Athlete;
import ruben.TFG.model.Entities.Inscription;
import ruben.TFG.model.Entities.Watchlist;
import ruben.TFG.service.EntitiesServices.InscriptionService;
import ruben.TFG.service.EntitiesServices.AthleteService;

import lombok.extern.slf4j.Slf4j;
import ruben.TFG.service.EntitiesServices.WatchlistService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/api/athletes")
public class AthleteController {

    private final AthleteService athleteService;

    private final InscriptionService inscriptionService;
    private final WatchlistService watchlistService;
    /**
     * Constructor of the class
     * @param athleteService, the service to manage the athlete's data
     * @param inscriptionService, the service to manage the inscription's data
     * @param watchlistService, the service to manage the watchlist's data
     */
    public AthleteController(AthleteService athleteService, InscriptionService inscriptionService, WatchlistService watchlistService) {
        this.athleteService = athleteService;
        this.inscriptionService = inscriptionService;
        this.watchlistService = watchlistService;
    }

    @GetMapping("")
    public ResponseEntity<List<AthleteDTO>> getAllAthletes() {
        List<Athlete> athletes = athleteService.getEnabledAthletes();
        if (athletes == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        List<AthleteDTO> athleteDTOS = athletes.stream().map(AthleteDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(athleteDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AthleteDTO> getAthleterById(@PathVariable(name = "id") Long id) {

        Athlete athlete = athleteService.getAthlete(id);
        if (athlete != null) {
            return ResponseEntity.ok(new AthleteDTO(athlete));
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<AthleteDTO> updateAthlete(@PathVariable Long id, @RequestBody Athlete athlete) {
        if (!id.equals(athlete.getId())) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(AthleteDTO.fromUser(athleteService.saveAthlete(athlete)));
    }

    @PostMapping("")
    public ResponseEntity<AthleteDTO> createAthlete(@RequestBody Athlete athlete) {
        return ResponseEntity.ok(AthleteDTO.fromUser(athleteService.saveAthlete(athlete)));
    }

    @GetMapping("/inscriptions/{id}")
    public ResponseEntity<List<InscriptionDTO>> getInscriptionById(@PathVariable(name = "id") Long id) {

        List<Inscription> inscriptions = inscriptionService.getEnabledInscriptions(id);
        if (inscriptions == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        else {
            List<InscriptionDTO> inscriptionDTOS = inscriptions.stream().map(InscriptionDTO::new).collect(Collectors.toList());
            return ResponseEntity.ok(inscriptionDTOS);
        }
    }

    @GetMapping("/watchlists/{id}")
    public ResponseEntity<List<WatchlistDTO>> getWatchlistById(@PathVariable(name = "id") Long id) {

        List<Watchlist> watchlists = watchlistService.getEnabledWatchlists(id);
        if (watchlists == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        else {
            List<WatchlistDTO> watchlistDTOs = watchlists.stream().map(WatchlistDTO::new).collect(Collectors.toList());
            return ResponseEntity.ok(watchlistDTOs);
        }
    }



}
