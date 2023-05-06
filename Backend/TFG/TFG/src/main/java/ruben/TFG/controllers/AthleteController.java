package ruben.TFG.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import ruben.TFG.controllers.DTO.InscriptionDTO;
import ruben.TFG.controllers.DTO.AthleteDTO;
import ruben.TFG.controllers.DTO.WatchlistDTO;
import ruben.TFG.model.Athlete;
import ruben.TFG.model.Inscription;
import ruben.TFG.model.Watchlist;
import ruben.TFG.service.InscriptionService;
import ruben.TFG.service.AthleteService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import lombok.extern.slf4j.Slf4j;
import ruben.TFG.service.WatchlistService;

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
    @ApiOperation("Get all athletes")
    public ResponseEntity<List<AthleteDTO>> getAllAthletes() {
        List<Athlete> athletes = athleteService.getEnabledAthletes();
        if (athletes == null) {
            log.warn("There is no athletes available.");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        // Convert the list of athletes to a list of UserDTOs
        List<AthleteDTO> athleteDTOS = athletes.stream().map(AthleteDTO::new).collect(Collectors.toList());
        log.info("The athletes has successfully been retrieved.");
        return ResponseEntity.ok(athleteDTOS);
    }

    @GetMapping("/{id}")
    @ApiOperation("Get a athlete by his id")
    public ResponseEntity<AthleteDTO> getAthleterById(@ApiParam("The id of the athlete") @PathVariable(name = "id") Long id) {

        Athlete athlete = athleteService.getAthlete(id);
        if (athlete != null) {
            log.info("The athlete has been found");
            return ResponseEntity.ok(new AthleteDTO(athlete));
        }
        else {
            log.warn("The athlete by id has not been found");
            return ResponseEntity.notFound().build();
        }
    }


    @PutMapping("/{id}")
    @ApiOperation("Change a athlete by his id.")
    public ResponseEntity<AthleteDTO> updateAthlete(@ApiParam("The id of the athlete")  @PathVariable Long id, @ApiParam("Modified athlete object") @RequestBody Athlete athlete) {
        if (!id.equals(athlete.getId())) {
            log.warn("Bad request , the id given in the path doesnt match with the id on the athlete");
            return ResponseEntity.badRequest().build();
        }
        log.info("Athlete updated successfully");
        return ResponseEntity.ok(AthleteDTO.fromUser(athleteService.saveAthlete(athlete)));
    }

    @PostMapping("")
    @ApiOperation("Create a athlete.")
    public ResponseEntity<AthleteDTO> createAthlete(@ApiParam("Modified athlete object") @RequestBody Athlete athlete) {
        log.info("Athlete created successfully");
        return ResponseEntity.ok(AthleteDTO.fromUser(athleteService.saveAthlete(athlete)));
    }

    @GetMapping("/inscriptions/{id}")
    @ApiOperation("Get the inscriptions for this athlete")
    public ResponseEntity<List<InscriptionDTO>> getInscriptionById(@ApiParam("The id of the athlete") @PathVariable(name = "id") Long id) {

        List<Inscription> inscriptions = inscriptionService.getEnabledInscriptions(id);
        if (inscriptions == null) {
            log.info("There is not inscriptions");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        else {
            // Convert the list of inscriptions to a list of inscriptionsDTO
            List<InscriptionDTO> inscriptionDTOS = inscriptions.stream().map(InscriptionDTO::new).collect(Collectors.toList());
            log.info("The inscriptions has successfully been retrieved.");
            return ResponseEntity.ok(inscriptionDTOS);
        }
    }

    @GetMapping("/watchlists/{id}")
    @ApiOperation("Get the watchlists for this athlete")
    public ResponseEntity<List<WatchlistDTO>> getWatchlistById(@ApiParam("The id of the athlete") @PathVariable(name = "id") Long id) {

        List<Watchlist> watchlists = watchlistService.getEnabledWatchlists(id);
        if (watchlists == null) {
            log.info("There is not watchlists");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        else {
            // Convert the list of watchlists to a list of watchlistDTOs
            List<WatchlistDTO> watchlistDTOs = watchlists.stream().map(WatchlistDTO::new).collect(Collectors.toList());
            log.info("The watchlists has successfully been retrieved.");
            return ResponseEntity.ok(watchlistDTOs);
        }
    }



}
