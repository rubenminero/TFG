package ruben.TFG.controllers.EntitiesControllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ruben.TFG.model.DTO.Entities.*;
import ruben.TFG.model.Entities.*;
import ruben.TFG.service.EntitiesServices.AdminService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/api/admins")
@PreAuthorize("hasRole('ADMIN')")
@Tag(name="Admin")
@AllArgsConstructor
public class AdminController {

    private final AdminService adminService;
    @GetMapping("/organizers")
    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<List<OrganizerDTO>> getAllOrganizers_Admin() {
        List<Organizer> organizers = adminService.getAllOrganizers();
        if (organizers == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        List<OrganizerDTO> organizerDTOS = organizers.stream().map(OrganizerDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(organizerDTOS);
    }
    @DeleteMapping("/organizers/{id}")
    @PreAuthorize("hasAuthority('admin:delete')")
    public ResponseEntity<OrganizerDTO> deleteOrganizer(@PathVariable Long id) {
        if (adminService.getOrganizer(id) == null) {
            return ResponseEntity.notFound().build();
        }
        adminService.changeStateOrganizer(id);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/athletes")
    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<List<AthleteDTO>> getAllAthletes_Admin() {
        List<Athlete> athletes = adminService.getAllAthletes();
        if (athletes == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        List<AthleteDTO> athleteDTOS = athletes.stream().map(AthleteDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(athleteDTOS);
    }

    @DeleteMapping("/athletes/{id}")
    @PreAuthorize("hasAuthority('admin:delete')")
    public ResponseEntity<AthleteDTO> deleteAthlete(@PathVariable Long id) {
        if (adminService.getAthlete(id) == null) {
            return ResponseEntity.notFound().build();
        }
        adminService.changeStateAthlete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/tournaments")
    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<List<TournamentDTO>> getAllTournaments_Admin() {
        List<Tournament> tournaments = adminService.getAllTournaments();
        if (tournaments == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        List<TournamentDTO> tournamentDTOS = tournaments.stream().map(TournamentDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(tournamentDTOS);
    }

    @DeleteMapping("/tournaments/{id}")
    @PreAuthorize("hasAuthority('admin:delete')")
    public ResponseEntity<AthleteDTO> deleteTournament(@PathVariable Long id) {
        if (adminService.getTournament(id) == null) {
            return ResponseEntity.notFound().build();
        }
        adminService.changeStateTournament(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/sports_types")
    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<List<Sports_typeDTO>> getAllSports_types_Admin() {
        List<Sports_type> sportsTypes = adminService.getAllSports_types();
        if (sportsTypes == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        List<Sports_typeDTO> sportsTypeDTOS = sportsTypes.stream().map(Sports_typeDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(sportsTypeDTOS);
    }

    @DeleteMapping("/sports_types/{id}")
    @PreAuthorize("hasAuthority('admin:delete')")
    public ResponseEntity<Sports_typeDTO> deleteSport_type(@PathVariable Long id) {
        if (adminService.getSport_type(id) == null) {
            return ResponseEntity.notFound().build();
        }
        adminService.changeStateSport_type(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/inscriptions")
    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<List<InscriptionDTO>> getAllInscriptionsAdmin() {
        List<Inscription> inscriptions = adminService.getAllInscriptions();
        if (inscriptions == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        List<InscriptionDTO> inscriptionDTOS = inscriptions.stream().map(InscriptionDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(inscriptionDTOS);
    }

    @DeleteMapping("/inscriptions/{id}")
    @PreAuthorize("hasAuthority('admin:delete')")
    public ResponseEntity<InscriptionDTO> deleteInscription(@PathVariable Long id) {
        if (adminService.getInscription(id) == null) {
            return ResponseEntity.notFound().build();
        }
        adminService.changeStateInscription(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/watchlists")
    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<List<WatchlistDTO>> getAllWatchlists_Admin() {
        List<Watchlist> watchlists = adminService.getAllWatchlists();
        if (watchlists == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        List<WatchlistDTO> watchlistDTOS = watchlists.stream().map(WatchlistDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(watchlistDTOS);
    }

    @DeleteMapping("/watchlists/{id}")
    @PreAuthorize("hasAuthority('admin:delete')")
    public ResponseEntity<WatchlistDTO> deleteWatchlist(@PathVariable Long id) {
        if (adminService.getWatchlist(id) == null) {
            return ResponseEntity.notFound().build();
        }
        adminService.changeStateWatchlist(id);
        return ResponseEntity.ok().build();
    }
}
