package ruben.TFG.controllers.EntitiesControllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import ruben.TFG.model.DTO.Entities.InscriptionDTO;
import ruben.TFG.model.DTO.Entities.AthleteDTO;
import ruben.TFG.model.DTO.Entities.WatchlistDTO;
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
@AllArgsConstructor
public class AthleteController {

    private final AthleteService athleteService;

    private final InscriptionService inscriptionService;
    private final WatchlistService watchlistService;

    @GetMapping("")
    @PreAuthorize("hasAuthority('ahtlete:read')")
    public ResponseEntity<List<AthleteDTO>> getAllAthletes() {
        List<Athlete> athletes = athleteService.getEnabledAthletes();
        if (athletes == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        List<AthleteDTO> athleteDTOS = athletes.stream().map(AthleteDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(athleteDTOS);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ahtlete:read')")
    public ResponseEntity<AthleteDTO> getAthleteById(@PathVariable(name = "id") Long id) {

        Athlete athlete = athleteService.getAthlete(id);
        if (athlete != null) {
            return ResponseEntity.ok(new AthleteDTO(athlete));
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ahtlete:update')")
    public ResponseEntity<AthleteDTO> updateAthlete(@PathVariable Long id, @RequestBody Athlete athlete) {
        if (!id.equals(athlete.getId())) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(AthleteDTO.fromUser(athleteService.saveAthlete(athlete)));
    }

    @PostMapping("")
    @PreAuthorize("hasAuthority('ahtlete:create')")
    public ResponseEntity<AthleteDTO> createAthlete(@RequestBody Athlete athlete) {
        return ResponseEntity.ok(AthleteDTO.fromUser(athleteService.saveAthlete(athlete)));
    }

    @GetMapping("/inscriptions/{id}")
    @PreAuthorize("hasAuthority('ahtlete:read')")
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
    @PreAuthorize("hasAuthority('ahtlete:read')")
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
