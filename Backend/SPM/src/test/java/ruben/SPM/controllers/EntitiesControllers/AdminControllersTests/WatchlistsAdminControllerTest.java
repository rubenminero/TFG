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
import ruben.SPM.model.DTO.Front.InscriptionFrontDTO;
import ruben.SPM.model.DTO.Front.WatchlistFrontDTO;
import ruben.SPM.model.Entities.*;
import ruben.SPM.service.EntitiesServices.AdminService;
import ruben.SPM.service.EntitiesServices.DeleteService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class WatchlistsAdminControllerTest {

    @InjectMocks
    private AdminController adminController;
    @Mock
    private AdminService adminService;
    @Mock
    private DeleteService deleteService;

    @Test
    public void testGetAllWatchlistsAdmin() {
        Athlete athlete = new Athlete();
        Organizer organizer = new Organizer();
        Sports_type sportsType = new Sports_type();

        Tournament event = new Tournament();
        event.setId(1L);
        event.setInscription(false);
        event.setCapacity(-1);
        event.setOrganizer(organizer);
        event.setSport_type(sportsType);

        Watchlist watchlist = new Watchlist();
        watchlist.setAthlete(athlete);
        watchlist.setTournament(event);
        watchlist.setEnabled(true);

        Watchlist watchlist1 = new Watchlist();
        watchlist1.setAthlete(athlete);
        watchlist1.setTournament(event);
        watchlist1.setEnabled(true);

        List<Watchlist> watchlists = new ArrayList<>();
        watchlists.add(watchlist);
        watchlists.add(watchlist1);

        when(adminService.getAllWatchlists()).thenReturn(watchlists);

        ResponseEntity response = adminController.getAllWatchlists_Admin();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(watchlists.size(), ((List<WatchlistFrontDTO>) response.getBody()).size());
        verify(adminService, times(1)).getAllWatchlists();
    }

    @Test
    public void testGetAllWatchlistsAdmin_NotFound() {
        when(adminService.getAllWatchlists()).thenReturn(Collections.emptyList());

        ResponseEntity response = adminController.getAllWatchlists_Admin();

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("There are no watchlists.", response.getBody());
        verify(adminService, times(1)).getAllWatchlists();
    }

    @Test
    public void testUpdateWatchlist() {
        Long watchlistId = 1L;
        Athlete athlete = new Athlete();
        Organizer organizer = new Organizer();
        Sports_type sportsType = new Sports_type();

        Tournament event = new Tournament();
        event.setId(1L);
        event.setInscription(false);
        event.setCapacity(-1);
        event.setOrganizer(organizer);
        event.setSport_type(sportsType);

        Watchlist watchlist = new Watchlist();
        watchlist.setAthlete(athlete);
        watchlist.setTournament(event);
        watchlist.setEnabled(true);


        when(adminService.getWatchlist(watchlistId)).thenReturn(watchlist);

        ResponseEntity response = adminController.updateWatchlist(watchlistId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(adminService, times(1)).getWatchlist(watchlistId);
        verify(adminService, times(1)).changeStateWatchlist(watchlistId);
    }

    @Test
    public void testUpdateWatchlist_NotFound() {
        Long watchlistId = 1L;

        when(adminService.getWatchlist(watchlistId)).thenReturn(null);

        ResponseEntity response = adminController.updateWatchlist(watchlistId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("There is no watchlist with this id.", response.getBody());
        verify(adminService, times(1)).getWatchlist(watchlistId);
        verify(adminService, times(0)).changeStateWatchlist(anyLong());
    }

    @Test
    public void testDeleteWatchlist() {
        Long watchlistId = 1L;
        Athlete athlete = new Athlete();
        Organizer organizer = new Organizer();
        Sports_type sportsType = new Sports_type();

        Tournament event = new Tournament();
        event.setId(1L);
        event.setInscription(false);
        event.setCapacity(-1);
        event.setOrganizer(organizer);
        event.setSport_type(sportsType);

        Watchlist watchlist = new Watchlist();
        watchlist.setAthlete(athlete);
        watchlist.setTournament(event);
        watchlist.setEnabled(true);

        when(adminService.getWatchlist(watchlistId)).thenReturn(watchlist);

        ResponseEntity response = adminController.deleteWatchlist(watchlistId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(adminService, times(1)).getWatchlist(watchlistId);
    }

    @Test
    public void testDeleteWatchlist_WatchlistNotFound() {
        Long watchlistId = 1L;

        when(adminService.getWatchlist(watchlistId)).thenReturn(null);

        ResponseEntity response = adminController.deleteWatchlist(watchlistId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("There is no watchlist with this id.", response.getBody());
        verify(adminService, times(1)).getWatchlist(watchlistId);
        verify(deleteService, times(0)).deleteWatchlist(anyLong());
    }
}
