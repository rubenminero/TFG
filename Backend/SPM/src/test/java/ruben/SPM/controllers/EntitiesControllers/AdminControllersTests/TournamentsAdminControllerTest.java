package ruben.SPM.controllers.EntitiesControllers.AdminControllersTests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ruben.SPM.controllers.EntitiesControllers.AdminController;
import ruben.SPM.model.DTO.Entities.OrganizerDTO;
import ruben.SPM.model.DTO.Front.TournamentFrontDTO;
import ruben.SPM.model.Entities.Organizer;
import ruben.SPM.model.Entities.Sports_type;
import ruben.SPM.model.Entities.Tournament;
import ruben.SPM.service.EntitiesServices.AdminService;
import ruben.SPM.service.EntitiesServices.DeleteService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class TournamentsAdminControllerTest {

    @InjectMocks
    private AdminController adminController;
    @Mock
    private AdminService adminService;
    @Mock
    private DeleteService deleteService;

    @Test
    public void testGetAllTournaments() {
        Long tournamentId = 1L;
        Organizer organizer = new Organizer();
        Sports_type sportsType = new Sports_type();

        Tournament tournament = new Tournament();
        tournament.setId(1L);
        tournament.setInscription(true);
        tournament.setCapacity(1000);
        tournament.setOrganizer(organizer);
        tournament.setSport_type(sportsType);

        Tournament tournament1 = new Tournament();
        tournament1.setId(1L);
        tournament1.setInscription(true);
        tournament1.setCapacity(1000);
        tournament1.setOrganizer(organizer);
        tournament1.setSport_type(sportsType);

        List<Tournament> tournaments = new ArrayList<>();
        tournaments.add(tournament);
        tournaments.add(tournament1);

        when(adminService.getAllTournaments()).thenReturn(tournaments);

        ResponseEntity response = adminController.getAllTournaments_Admin();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(tournaments.size(), ((List<TournamentFrontDTO>) response.getBody()).size());
        verify(adminService, times(1)).getAllTournaments();
    }

    @Test
    public void testGetAllTournaments_NotFound() {
        when(adminService.getAllTournaments()).thenReturn(Collections.emptyList());

        ResponseEntity response = adminController.getAllTournaments_Admin();

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("There are no tournaments.", response.getBody());
        verify(adminService, times(1)).getAllTournaments();
    }

    @Test
    public void testUpdateTournament() {
        Long tournamentId = 1L;
        Organizer organizer = new Organizer();
        Sports_type sportsType = new Sports_type();

        Tournament tournament = new Tournament();
        tournament.setId(1L);
        tournament.setInscription(true);
        tournament.setCapacity(1000);
        tournament.setOrganizer(organizer);
        tournament.setSport_type(sportsType);

        when(adminService.getTournament(tournamentId)).thenReturn(tournament);

        ResponseEntity response = adminController.updateTournament(tournamentId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(adminService, times(1)).getTournament(tournamentId);
        verify(adminService, times(1)).changeStateTournament(tournamentId);
    }

    @Test
    public void testUpdateTournament_NotFound() {
        Long tournamentId = 1L;

        when(adminService.getTournament(tournamentId)).thenReturn(null);

        ResponseEntity response = adminController.updateTournament(tournamentId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("There is no tournament with this id.", response.getBody());
        verify(adminService, times(1)).getTournament(tournamentId);
        verify(adminService, times(0)).changeStateTournament(anyLong());
    }

    @Test
    public void testDeleteTournament_Success() {
        Long tournamentId = 1L;
        Organizer organizer = new Organizer();
        Sports_type sportsType = new Sports_type();

        Tournament tournament = new Tournament();
        tournament.setId(1L);
        tournament.setInscription(true);
        tournament.setCapacity(1000);
        tournament.setOrganizer(organizer);
        tournament.setSport_type(sportsType);

        when(adminService.getTournament(tournamentId)).thenReturn(tournament);

        ResponseEntity response = adminController.deleteTournament(tournamentId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(adminService, times(1)).getTournament(tournamentId);
        verify(deleteService, times(1)).deleteTournament(tournamentId);
    }

    @Test
    public void testDeleteTournament_TournamentNotFound() {
        Long tournamentId = 1L;

        when(adminService.getTournament(tournamentId)).thenReturn(null);

        ResponseEntity response = adminController.deleteTournament(tournamentId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("There is no tournament with this id.", response.getBody());
        verify(adminService, times(1)).getTournament(tournamentId);
        verify(deleteService, times(0)).deleteTournament(anyLong());
    }
}
