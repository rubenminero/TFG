package ruben.SPM.controllers.EntitiesControllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ruben.SPM.model.DTO.Entities.WatchlistDTO;
import ruben.SPM.model.DTO.Front.EventFrontDTO;
import ruben.SPM.model.DTO.Front.TournamentFrontDTO;
import ruben.SPM.model.Entities.*;
import ruben.SPM.service.EntitiesServices.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class WatchlistControllerTest {
    @InjectMocks
    private WatchlistController watchlistController;
    @Mock
    private WatchlistService watchlistService;
    @Mock
    private UserService userService;
    @Mock
    private TournamentService tournamentService;
    @Mock
    private AthleteService athleteService;

    @Test
    public void testGetWatchlistById() {
        Long id = 1L;
        User user = new User();
        user.setId(1L);

        Athlete athlete = new Athlete();
        athlete.setId(1L);

        Organizer organizer = new Organizer();
        organizer.setId(1L);

        Sports_type sportsType = new Sports_type();
        sportsType.setName("sport type");

        Tournament tournament = new Tournament();
        tournament.setId(1L);
        tournament.setInscription(true);
        tournament.setCapacity(1000);
        tournament.setOrganizer(organizer);
        tournament.setSport_type(sportsType);

        Watchlist watchlist = new Watchlist();
        watchlist.setTournament(tournament);
        watchlist.setAthlete(athlete);
        watchlist.setEnabled(true);

        when(userService.isAuthorized()).thenReturn(user);
        when(watchlistService.getWatchList(id)).thenReturn(watchlist);

        ResponseEntity response = watchlistController.getWatchlistById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(userService, times(1)).isAuthorized();
        verify(watchlistService, times(1)).getWatchList(id);
    }

    @Test
    public void testGetWatchlistById_InvalidUser() {
        Long id = 1L;

        when(userService.isAuthorized()).thenReturn(null);

        ResponseEntity response = watchlistController.getWatchlistById(id);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals("This user can't do that operation.", response.getBody());
        verify(userService, times(1)).isAuthorized();
        verify(watchlistService, never()).getWatchList(id);
    }

    @Test
    public void testGetWatchlistById_InvalidWatchlistId() {
        Long id = 1L;
        Athlete athlete = new Athlete();
        athlete.setId(1L);

        Organizer organizer = new Organizer();
        organizer.setId(1L);

        Sports_type sportsType = new Sports_type();
        sportsType.setName("sport type");

        Tournament tournament = new Tournament();
        tournament.setId(1L);
        tournament.setInscription(true);
        tournament.setCapacity(1000);
        tournament.setOrganizer(organizer);
        tournament.setSport_type(sportsType);

        Watchlist watchlist = new Watchlist();
        watchlist.setTournament(tournament);
        watchlist.setAthlete(athlete);
        watchlist.setEnabled(true);

        when(userService.isAuthorized()).thenReturn(new User());
        when(watchlistService.getWatchList(id)).thenReturn(null);

        ResponseEntity response = watchlistController.getWatchlistById(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("The watchlist that you asked doesnt exist.", response.getBody());
        verify(userService, times(1)).isAuthorized();
        verify(watchlistService, times(1)).getWatchList(id);
    }

    @Test
    public void testGetWatchlistById_DisabledWatchlist() {
        Long id = 1L;
        Athlete athlete = new Athlete();
        athlete.setId(1L);

        Organizer organizer = new Organizer();
        organizer.setId(1L);

        Sports_type sportsType = new Sports_type();
        sportsType.setName("sport type");

        Tournament tournament = new Tournament();
        tournament.setId(1L);
        tournament.setInscription(true);
        tournament.setCapacity(1000);
        tournament.setOrganizer(organizer);
        tournament.setSport_type(sportsType);

        Watchlist watchlist = new Watchlist();
        watchlist.setTournament(tournament);
        watchlist.setAthlete(athlete);
        watchlist.setEnabled(false);

        when(userService.isAuthorized()).thenReturn(new User());
        when(watchlistService.getWatchList(id)).thenReturn(watchlist);

        ResponseEntity response = watchlistController.getWatchlistById(id);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals("The watchlist that you asked is disabled.", response.getBody());
        verify(userService, times(1)).isAuthorized();
        verify(watchlistService, times(1)).getWatchList(id);
    }

    @Test
    public void testCreateWatchlist() {
        Long id = 1L;

        User user = new User();
        user.setId(1L);


        Athlete athlete = new Athlete();
        athlete.setId(1L);

        Organizer organizer = new Organizer();
        organizer.setId(1L);

        Sports_type sportsType = new Sports_type();
        sportsType.setName("sport type");

        Tournament tournament = new Tournament();
        tournament.setId(1L);
        tournament.setInscription(true);
        tournament.setCapacity(1000);
        tournament.setOrganizer(organizer);
        tournament.setSport_type(sportsType);



        WatchlistDTO watchlistDTO = new WatchlistDTO();
        watchlistDTO.setId(1L);
        watchlistDTO.setTournament(tournament.getId());
        watchlistDTO.setAthlete(athlete.getId());
        watchlistDTO.setEnabled(true);

        when(userService.isAuthorized()).thenReturn(user);
        when(tournamentService.getTournament(watchlistDTO.getTournament())).thenReturn(tournament);
        when(athleteService.getAthlete(watchlistDTO.getAthlete())).thenReturn(athlete);
        when(watchlistService.isValid(any())).thenReturn(true);

        ResponseEntity response = watchlistController.createWatchlist(watchlistDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(userService, times(1)).isAuthorized();
        verify(tournamentService, times(1)).getTournament(watchlistDTO.getTournament());
        verify(athleteService, times(1)).getAthlete(watchlistDTO.getAthlete());
    }

    @Test
    public void testCreateWatchlist_DuplicateWatchlist() {
        Long id = 1L;

        User user = new User();
        user.setId(1L);


        Athlete athlete = new Athlete();
        athlete.setId(1L);

        Organizer organizer = new Organizer();
        organizer.setId(1L);

        Sports_type sportsType = new Sports_type();
        sportsType.setName("sport type");

        Tournament tournament = new Tournament();
        tournament.setId(1L);
        tournament.setInscription(true);
        tournament.setCapacity(1000);
        tournament.setOrganizer(organizer);
        tournament.setSport_type(sportsType);



        WatchlistDTO watchlistDTO = new WatchlistDTO();
        watchlistDTO.setId(1L);
        watchlistDTO.setTournament(tournament.getId());
        watchlistDTO.setAthlete(athlete.getId());
        watchlistDTO.setEnabled(true);

        when(userService.isAuthorized()).thenReturn(user);
        when(tournamentService.getTournament(watchlistDTO.getTournament())).thenReturn(tournament);
        when(athleteService.getAthlete(watchlistDTO.getAthlete())).thenReturn(athlete);
        when(watchlistService.isValid(any())).thenReturn(false);

        ResponseEntity response = watchlistController.createWatchlist(watchlistDTO);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("You already have this tournament/event in your watchlists.", response.getBody());
        verify(userService, times(1)).isAuthorized();
        verify(tournamentService, times(1)).getTournament(watchlistDTO.getTournament());
        verify(athleteService, times(1)).getAthlete(watchlistDTO.getAthlete());
        verify(watchlistService, never()).saveWatchlist(any());
    }

    @Test
    public void testCreateWatchlist_InvalidTournamentDisabled() {
        Long id = 1L;

        User user = new User();
        user.setId(1L);


        Athlete athlete = new Athlete();
        athlete.setId(1L);

        Organizer organizer = new Organizer();
        organizer.setId(1L);

        Sports_type sportsType = new Sports_type();
        sportsType.setName("sport type");

        Tournament tournament = new Tournament();
        tournament.setId(1L);
        tournament.setEnabled(false);
        tournament.setInscription(true);
        tournament.setCapacity(1000);
        tournament.setOrganizer(organizer);
        tournament.setSport_type(sportsType);



        WatchlistDTO watchlistDTO = new WatchlistDTO();
        watchlistDTO.setId(1L);
        watchlistDTO.setTournament(tournament.getId());
        watchlistDTO.setAthlete(athlete.getId());
        watchlistDTO.setEnabled(true);

        when(userService.isAuthorized()).thenReturn(user);
        when(tournamentService.getTournament(watchlistDTO.getTournament())).thenReturn(tournament);
        when(athleteService.getAthlete(watchlistDTO.getAthlete())).thenReturn(athlete);

        ResponseEntity response = watchlistController.createWatchlist(watchlistDTO);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("The tournament of the watchlist is disabled.", response.getBody());
        verify(userService, times(1)).isAuthorized();
        verify(tournamentService, times(1)).getTournament(watchlistDTO.getTournament());
        verify(athleteService, times(1)).getAthlete(watchlistDTO.getAthlete());
        verify(watchlistService, never()).isValid(any());
        verify(watchlistService, never()).saveWatchlist(any());
    }

    @Test
    public void testCreateWatchlist_InvalidAthleteDisabled() {
        Long id = 1L;

        User user = new User();
        user.setId(1L);


        Athlete athlete = new Athlete();
        athlete.setEnabled(false);
        athlete.setId(1L);

        Organizer organizer = new Organizer();
        organizer.setId(1L);

        Sports_type sportsType = new Sports_type();
        sportsType.setName("sport type");

        Tournament tournament = new Tournament();
        tournament.setId(1L);
        tournament.setEnabled(true);
        tournament.setInscription(true);
        tournament.setCapacity(1000);
        tournament.setOrganizer(organizer);
        tournament.setSport_type(sportsType);



        WatchlistDTO watchlistDTO = new WatchlistDTO();
        watchlistDTO.setId(1L);
        watchlistDTO.setTournament(tournament.getId());
        watchlistDTO.setAthlete(athlete.getId());
        watchlistDTO.setEnabled(true);

        when(userService.isAuthorized()).thenReturn(user);
        when(tournamentService.getTournament(watchlistDTO.getTournament())).thenReturn(tournament);
        when(athleteService.getAthlete(watchlistDTO.getAthlete())).thenReturn(athlete);

        ResponseEntity response = watchlistController.createWatchlist(watchlistDTO);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("The user of the watchlist is disabled.", response.getBody());
        verify(userService, times(1)).isAuthorized();
        verify(tournamentService, times(1)).getTournament(watchlistDTO.getTournament());
        verify(athleteService, times(1)).getAthlete(watchlistDTO.getAthlete());
        verify(watchlistService, never()).isValid(any());
        verify(watchlistService, never()).saveWatchlist(any());
    }

}
