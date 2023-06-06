package ruben.SPM.controllers.EntitiesControllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ruben.SPM.model.DTO.Auth.PasswordChangeDTO;
import ruben.SPM.model.DTO.Entities.AthleteDTO;
import ruben.SPM.model.DTO.Front.InscriptionFrontDTO;
import ruben.SPM.model.DTO.Front.WatchlistFrontDTO;
import ruben.SPM.model.Entities.*;
import ruben.SPM.service.EntitiesServices.AthleteService;
import ruben.SPM.service.EntitiesServices.InscriptionService;
import ruben.SPM.service.EntitiesServices.UserService;
import ruben.SPM.service.EntitiesServices.WatchlistService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AthleteControllerTest {

    @InjectMocks
    private AthleteController athleteController;

    @Mock
    private AthleteService athleteService;

    @Mock
    private InscriptionService inscriptionService;

    @Mock
    private WatchlistService watchlistService;

    @Mock
    private UserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    public void testGetAthleteById() {
        // Arrange
        Long athleteId = 1L;
        Athlete athlete = new Athlete();
        athlete.setEnabled(true);
        when(athleteService.getAthlete(athleteId)).thenReturn(athlete);

        // Act
        ResponseEntity<?> response = athleteController.getAthleteById(athleteId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        AthleteDTO responseBody = (AthleteDTO) response.getBody();
        assertNotNull(responseBody);
        verify(athleteService, times(1)).getAthlete(athleteId);
    }

    @Test
    public void testGetAthleteById_AthleteNotEnabledorDoesntExist() {
        // Arrange
        Long athleteId = 1L;
        Athlete athlete = new Athlete();
        athlete.setEnabled(false);
        when(athleteService.getAthlete(athleteId)).thenReturn(athlete);

        // Act
        ResponseEntity<?> response = athleteController.getAthleteById(athleteId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        String responseBody = (String) response.getBody();
        assertNotNull(responseBody);
        assertEquals("The athlete that you asked doesnt exist.", responseBody);
        verify(athleteService, times(1)).getAthlete(athleteId);
    }

    @Test
    public void testUpdateAthlete() {
        // Arrange
        Long athleteId = 1L;
        Athlete savedAthlete = new Athlete();
        savedAthlete.setId(athleteId);
        savedAthlete.setUsername("username1");

        Athlete updatedAthlete = new Athlete();
        updatedAthlete.setId(athleteId);
        updatedAthlete.setUsername("username2");

        when(athleteService.getAthlete(athleteId)).thenReturn(savedAthlete);
        when(userService.validUsername(updatedAthlete.getUsername())).thenReturn(false);
        when(athleteService.updateAthlete(updatedAthlete, savedAthlete)).thenReturn(updatedAthlete);

        // Act
        ResponseEntity<?> response = athleteController.updateAthlete(athleteId, updatedAthlete);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        AthleteDTO responseBody = (AthleteDTO) response.getBody();
        assertNotNull(responseBody);
        assertEquals(updatedAthlete.getId(), responseBody.getId());
        assertEquals(updatedAthlete.getUsername(), responseBody.getUsername());
        verify(athleteService, times(1)).getAthlete(athleteId);
        verify(userService, times(1)).validUsername(updatedAthlete.getUsername());
        verify(athleteService, times(1)).updateAthlete(updatedAthlete, savedAthlete);
    }

    @Test
    public void testUpdateAthlete_AthleteDoesntExist() {
        // Arrange
        Long athleteId = 1L;
        when(athleteService.getAthlete(athleteId)).thenReturn(null);

        // Act
        ResponseEntity<?> response = athleteController.updateAthlete(athleteId, new Athlete());

        // Assert
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        String responseBody = (String) response.getBody();
        assertNotNull(responseBody);
        assertEquals("There is no user with that data.", responseBody);
        verify(athleteService, times(1)).getAthlete(athleteId);
        verify(userService, never()).validUsername(anyString());
        verify(athleteService, never()).updateAthlete(any(Athlete.class), any(Athlete.class));
    }

    @Test
    public void testUpdateAthlete_UsernameTaked() {
        // Arrange
        Long athleteId = 1L;
        Athlete savedAthlete = new Athlete();
        savedAthlete.setId(athleteId);
        savedAthlete.setUsername("username1");

        Athlete updatedAthlete = new Athlete();
        updatedAthlete.setId(athleteId);
        updatedAthlete.setUsername("username2");

        when(athleteService.getAthlete(athleteId)).thenReturn(savedAthlete);
        when(userService.validUsername(updatedAthlete.getUsername())).thenReturn(true);

        // Act
        ResponseEntity<?> response = athleteController.updateAthlete(athleteId, updatedAthlete);

        // Assert
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        String responseBody = (String) response.getBody();
        assertNotNull(responseBody);
        assertEquals("This username is already taken.", responseBody);
        verify(athleteService, times(1)).getAthlete(athleteId);
        verify(userService, times(1)).validUsername(updatedAthlete.getUsername());
        verify(athleteService, never()).updateAthlete(any(Athlete.class), any(Athlete.class));
    }



    @Test
    public void testGetInscriptionById() {
        // Arrange
        Long athleteId = 1L;
        Athlete athlete = new Athlete();
        athlete.setId(athleteId);
        athlete.setUsername("username1");

        List<Inscription> inscriptions = new ArrayList<>();
        Inscription inscription= new Inscription();
        Inscription inscription1= new Inscription();
        inscription.setTournament(new Tournament());
        inscription.setAthlete(new Athlete());
        inscription1.setTournament(new Tournament());
        inscription1.setAthlete(new Athlete());

        inscriptions.add(inscription);
        inscriptions.add(inscription1);

        when(inscriptionService.getEnabledInscriptions(athlete.getId())).thenReturn(inscriptions);

        // Act
        ResponseEntity response = athleteController.getInscriptionById(athlete.getId());

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<InscriptionFrontDTO> responseBody = (List<InscriptionFrontDTO>) response.getBody();
        assertNotNull(responseBody);
        assertEquals(inscriptions.size(), responseBody.size());
        verify(inscriptionService, times(1)).getEnabledInscriptions(athleteId);
    }

    @Test
    public void testGetInscriptionById_NotFound() {
        // Arrange
        Long athleteId = 1L;
        List<Inscription> inscriptions = new ArrayList<>();

        when(inscriptionService.getEnabledInscriptions(athleteId)).thenReturn(inscriptions);

        // Act
        ResponseEntity response = athleteController.getInscriptionById(athleteId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        String responseBody = (String) response.getBody();
        assertNotNull(responseBody);
        assertEquals("The athlete that you asked doesnt have inscriptions.", responseBody);
        verify(inscriptionService, times(1)).getEnabledInscriptions(athleteId);
    }


    @Test
    public void testGetWatchlistById() {
        // Arrange
        Long athleteId = 1L;
        Athlete athlete = new Athlete();
        athlete.setId(athleteId);
        athlete.setUsername("username1");

        List<Watchlist> watchlists = new ArrayList<>();
        Watchlist watchlist= new Watchlist();
        Watchlist watchlist1= new Watchlist();
        watchlist.setTournament(new Tournament());
        watchlist.setAthlete(new Athlete());
        watchlist1.setTournament(new Tournament());
        watchlist1.setAthlete(new Athlete());

        watchlists.add(watchlist);
        watchlists.add(watchlist1);

        when(watchlistService.getEnabledWatchlists(athleteId)).thenReturn(watchlists);

        // Act
        ResponseEntity response = athleteController.getWatchlistById(athleteId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<WatchlistFrontDTO> responseBody = (List<WatchlistFrontDTO>) response.getBody();
        assertNotNull(responseBody);
        assertEquals(watchlists.size(), responseBody.size());
        verify(watchlistService, times(1)).getEnabledWatchlists(athleteId);
    }

    @Test
    public void testGetWatchlistById_NotFound() {
        // Arrange
        Long athleteId = 1L;
        List<Watchlist> watchlists = new ArrayList<>();

        when(watchlistService.getEnabledWatchlists(athleteId)).thenReturn(watchlists);

        // Act
        ResponseEntity response = athleteController.getWatchlistById(athleteId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        String responseBody = (String) response.getBody();
        assertNotNull(responseBody);
        assertEquals("The athlete that you asked doesnt have watchlists.", responseBody);
        verify(watchlistService, times(1)).getEnabledWatchlists(athleteId);
    }

    @Test
    public void testDeleteAthlete() {
        // Arrange
        Long athleteId = 1L;
        String username = "testuser";

        Athlete athlete = new Athlete();
        athlete.setId(athleteId);
        athlete.setUsername(username);


        User user = new User();
        user.setId(athleteId);
        user.setUsername(username);

        when(athleteService.getAthlete(athleteId)).thenReturn(athlete);
        when(userService.isAuthorized()).thenReturn(user);
        // Act
        ResponseEntity response = athleteController.deleteAthlete(athleteId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        AthleteDTO athleteDTO = (AthleteDTO) response.getBody();
        assertNotNull(athleteDTO);
        assertEquals(athleteId, athleteDTO.getId());
    }

    @Test
    public void testDeleteAthlete_Forbidden() {
        // Arrange
        Long athleteId = 1L;
        String username = "testuser";

        Athlete athlete = new Athlete();
        athlete.setId(athleteId);
        athlete.setUsername(username);

        Long athleteId2 = 0L;
        String username2 = "testuser2";

        Athlete athlete2 = new Athlete();
        athlete2.setId(athleteId2);
        athlete2.setUsername(username2);


        User user = new User();
        user.setId(athleteId);
        user.setUsername(username);

        when(athleteService.getAthlete(athleteId)).thenReturn(null);
        when(userService.isAuthorized()).thenReturn(user);

        // Act
        ResponseEntity response = athleteController.deleteAthlete(athleteId);

        // Assert
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        String responseBody = (String) response.getBody();
        assertNotNull(responseBody);
        assertEquals("This user cant do that operation.", responseBody);
        verify(athleteService, times(1)).getAthlete(athleteId);
        verify(userService, never()).getUserByUsername(anyString());
        verify(athleteService, never()).deleteAthlete(any());
    }

    @Test
    public void testDeleteAthlete_BadRequest() {
        // Arrange
        Long athleteId = 1L;

        Athlete athlete = new Athlete();
        athlete.setId(athleteId);

        String username = "testuser";
        User user = new User();
        user.setId(2L);
        user.setUsername(username);

        when(athleteService.getAthlete(athleteId)).thenReturn(athlete);
        when(userService.isAuthorized()).thenReturn(user);
        // Act
        ResponseEntity response = athleteController.deleteAthlete(athleteId);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        String responseBody = (String) response.getBody();
        assertNotNull(responseBody);
        assertEquals("The id provided doesnt doesnt belong to your user.", responseBody);
    }


}
