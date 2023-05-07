package ruben.TFG.controllers.EntitiesControllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ruben.TFG.model.DTO.TournamentDTO;
import ruben.TFG.model.Entities.Organizer;
import ruben.TFG.model.Entities.Sports_type;
import ruben.TFG.model.Entities.Tournament;
import ruben.TFG.service.EntitiesServices.OrganizerService;
import ruben.TFG.service.EntitiesServices.Sports_typeService;
import ruben.TFG.service.EntitiesServices.TournamentService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/api/tournaments")
public class TournamentController {

    private final TournamentService tournamentService;
    private final OrganizerService organizerService;
    private final Sports_typeService sportsTypeService;
    /**
     * Constructor of the class
     * @param tournamentService, the service to manage the tournament's data
     */
    public TournamentController(TournamentService tournamentService, OrganizerService organizerService, Sports_typeService sportsTypeService ) {
        this.tournamentService = tournamentService;
        this.organizerService = organizerService;
        this.sportsTypeService = sportsTypeService;
    }

    @GetMapping("")
    public ResponseEntity<List<TournamentDTO>> getAllTournaments() {
        List<Tournament> tournaments = tournamentService.getEnabledTournaments();
        if (tournaments == null) {
            log.warn("There is no tournaments available.");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        // Convert the list of tournaments to a list of tournamentDTOs
        List<TournamentDTO> tournamentDTOs = tournaments.stream().map(TournamentDTO::new).collect(Collectors.toList());
        log.info("The tournaments has successfully been retrieved.");
        return ResponseEntity.ok(tournamentDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TournamentDTO> getTournamentById(@PathVariable(name = "id") Long id) {

        Tournament tournament = tournamentService.getTournament(id);
        if (tournament != null) {
            log.info("The tournament has been found");
            return ResponseEntity.ok(new TournamentDTO(tournament));
        }
        else {
            log.warn("The tournament by id has not been found");
            return ResponseEntity.notFound().build();
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<TournamentDTO> updateTournament(@PathVariable Long id, @RequestBody Tournament tournament) {
        if (!id.equals(tournament.getId())) {
            log.warn("Bad request , the id given in the path doesnt match with the id on the tournament");
            return ResponseEntity.badRequest().build();
        }
        if (tournament.getSport_type().isEnabled() && tournament.getOrganizer().isEnabled()){
            log.info("Athlete updated successfully");
            return ResponseEntity.ok(TournamentDTO.fromTournament(tournamentService.saveTournament(tournament)));
        }else{
            log.warn("Bad request , the sport type or the organizer of the tournament is disabled or doesnt exit.");
            return ResponseEntity.badRequest().build();
        }

    }

    @PostMapping("")
    public ResponseEntity<TournamentDTO> createTournament(@RequestBody TournamentDTO tournamentDTO ) {
        Organizer organizer = organizerService.getOrganizer(tournamentDTO.getOrganizer());
        Sports_type sportType = sportsTypeService.getSport_type(tournamentDTO.getSport_type());
        Tournament tournament = TournamentDTO.toTournament(tournamentDTO);
        tournament.setOrganizer(organizer);
        tournament.setSport_type(sportType);
        if (tournament.getSport_type().isEnabled() && tournament.getOrganizer().isEnabled()){
            log.info("Tournament created successfully");
            return ResponseEntity.ok(TournamentDTO.fromTournament(tournamentService.saveTournament(tournament)));
        }else{
            log.warn("Bad request , the sport type or the organizer of the tournament is disabled or doesnt exit.");
            return ResponseEntity.badRequest().build();
        }

    }



}
