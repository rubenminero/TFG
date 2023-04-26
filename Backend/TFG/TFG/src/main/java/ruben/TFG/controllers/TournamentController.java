package ruben.TFG.controllers;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ruben.TFG.controllers.DTO.TournamentDTO;
import ruben.TFG.model.Organizer;
import ruben.TFG.model.Sports_type;
import ruben.TFG.model.Tournament;
import ruben.TFG.service.OrganizerService;
import ruben.TFG.service.Sports_typeService;
import ruben.TFG.service.TournamentService;

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
    @ApiOperation("Get all tournaments")
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
    @ApiOperation("Get a tournament by his id")
    public ResponseEntity<TournamentDTO> getTournamentById(@ApiParam("The id of the tournament") @PathVariable(name = "id") Long id) {

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
    @ApiOperation("Change a tournament by his id.")
    public ResponseEntity<TournamentDTO> updateTournament(@ApiParam("The id of the tournament")  @PathVariable Long id, @ApiParam("Modified tournament object") @RequestBody Tournament tournament) {
        if (!id.equals(tournament.getId())) {
            log.warn("Bad request , the id given in the path doesnt match with the id on the tournament");
            return ResponseEntity.badRequest().build();
        }
        if (tournament.getSport_type().isEnabled() && tournament.getOrganizer().isEnabled()){
            log.info("User updated successfully");
            return ResponseEntity.ok(TournamentDTO.fromTournament(tournamentService.saveTournament(tournament)));
        }else{
            log.warn("Bad request , the sport type or the organizer of the tournament is disabled or doesnt exit.");
            return ResponseEntity.badRequest().build();
        }

    }

    @PostMapping("")
    @ApiOperation("Create a tournament.")
    public ResponseEntity<TournamentDTO> createTournament(@ApiParam("New tournament object") @RequestBody TournamentDTO tournamentDTO ) {
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
