package ruben.SPM.controllers.EntitiesControllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import ruben.SPM.model.DTO.Entities.InscriptionDTO;
import ruben.SPM.model.DTO.Front.InscriptionFrontDTO;
import ruben.SPM.model.Entities.*;
import ruben.SPM.service.EntitiesServices.AthleteService;
import ruben.SPM.service.EntitiesServices.InscriptionService;
import ruben.SPM.service.EntitiesServices.TournamentService;
import ruben.SPM.service.EntitiesServices.UserService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class InscriptionControllerTest {

    @InjectMocks
    private InscriptionController inscriptionController;

    @Mock
    private InscriptionService inscriptionService;
    @Mock
    private TournamentService tournamentService;
    @Mock
    private AthleteService athleteService;
    @Mock
    private UserService userService;

    @Test
    public void testGetInscriptionById_WhenValidIdAndEnabled_ReturnsOk() {

        Long inscriptionId = 1L;
        Inscription inscription = new Inscription();
        inscription.setId(inscriptionId);
        inscription.setEnabled(true);

        when(inscriptionService.getInscription(inscriptionId)).thenReturn(inscription);


        ResponseEntity response = inscriptionController.getInscriptionById(inscriptionId);


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(inscription, response.getBody());
    }

    @Test
    public void testGetInscriptionById_Forbidden() {

        Long inscriptionId = 1L;
        Inscription inscription = new Inscription();
        inscription.setId(inscriptionId);
        inscription.setEnabled(false);

        when(inscriptionService.getInscription(inscriptionId)).thenReturn(inscription);


        ResponseEntity response = inscriptionController.getInscriptionById(inscriptionId);


        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals("The inscription that you asked is disabled.", response.getBody());
    }

    @Test
    public void testGetInscriptionById_NotFound() {

        Long inscriptionId = 1L;

        when(inscriptionService.getInscription(inscriptionId)).thenReturn(null);


        ResponseEntity response = inscriptionController.getInscriptionById(inscriptionId);


        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("The inscription that you asked doesnt exit.", response.getBody());
    }

    @Test
    public void testUpdateInscription() {

        Long inscriptionId = 1L;
        InscriptionDTO inscriptionDTO = new InscriptionDTO();
        inscriptionDTO.setId(inscriptionId);
        inscriptionDTO.setTournament(1L);
        inscriptionDTO.setAthlete(1L);
        inscriptionDTO.setEnabled(true);

        Tournament tournament = new Tournament();
        tournament.setId(1L);
        tournament.setEnabled(true);
        Athlete athlete = new Athlete();
        athlete.setId(1L);
        athlete.setEnabled(true);

        Inscription inscription = new Inscription();
        inscription.setId(inscriptionId);
        inscription.setTournament(tournament);
        inscription.setAthlete(athlete);
        when(inscriptionService.getInscription(inscriptionId)).thenReturn(inscription);
        when(tournamentService.getTournament(inscriptionDTO.getTournament())).thenReturn(tournament);
        when(athleteService.getAthlete(inscriptionDTO.getAthlete())).thenReturn(athlete);
        when(inscriptionService.validInscription(athlete, tournament)).thenReturn(false);
        when(inscriptionService.saveInscription(inscription)).thenReturn(inscription);


        ResponseEntity response = inscriptionController.updateInscription(inscriptionId, inscriptionDTO);


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(inscriptionDTO, response.getBody());
    }

    @Test
    public void testUpdateInscription_Inscription_NotFound() {

        Long inscriptionId = 1L;
        InscriptionDTO inscriptionDTO = new InscriptionDTO();
        inscriptionDTO.setId(1L);

        when(inscriptionService.getInscription(inscriptionId)).thenReturn(null);


        ResponseEntity response = inscriptionController.updateInscription(inscriptionId, inscriptionDTO);


        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Bad request ,there is no inscription for that id.", response.getBody());
    }
    @Test
    public void testUpdateInscription_NotFound() {

        Long inscriptionId = 1L;
        InscriptionDTO inscriptionDTO = new InscriptionDTO();
        inscriptionDTO.setId(2L);

        Inscription inscription = new Inscription();
        inscription.setId(2L);


        ResponseEntity response = inscriptionController.updateInscription(inscriptionId, inscriptionDTO);


        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("The id that you give doesnt match with the id of the inscription.", response.getBody());
    }

    @Test
    public void testUpdateInscription_Tournament_NotFound() {

        Long inscriptionId = 1L;
        InscriptionDTO inscriptionDTO = new InscriptionDTO();
        inscriptionDTO.setId(inscriptionId);
        inscriptionDTO.setTournament(1L);
        inscriptionDTO.setAthlete(1L);

        Tournament tournament = new Tournament();
        tournament.setId(1L);
        tournament.setEnabled(false);
        Athlete athlete = new Athlete();
        athlete.setId(1L);
        athlete.setEnabled(true);
        Inscription inscription = new Inscription();
        inscription.setId(inscriptionId);
        inscription.setTournament(tournament);
        inscription.setAthlete(athlete);

        when(inscriptionService.getInscription(inscriptionId)).thenReturn(inscription);
        when(tournamentService.getTournament(inscriptionDTO.getTournament())).thenReturn(null);


        ResponseEntity response = inscriptionController.updateInscription(inscriptionId, inscriptionDTO);


        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("The tournament of the inscription is disabled.", response.getBody());
    }

    @Test
    public void testUpdateInscription_Athlete_NotFound() {

        Long inscriptionId = 1L;
        InscriptionDTO inscriptionDTO = new InscriptionDTO();
        inscriptionDTO.setId(inscriptionId);
        inscriptionDTO.setTournament(1L);
        inscriptionDTO.setAthlete(1L);

        Tournament tournament = new Tournament();
        tournament.setId(1L);
        tournament.setEnabled(true);
        Athlete athlete = new Athlete();
        athlete.setId(1L);
        athlete.setEnabled(false);
        Inscription inscription = new Inscription();
        inscription.setId(inscriptionId);
        inscription.setTournament(tournament);
        inscription.setAthlete(athlete);

        when(inscriptionService.getInscription(inscriptionId)).thenReturn(inscription);
        when(tournamentService.getTournament(inscriptionDTO.getTournament())).thenReturn(tournament);
        when(athleteService.getAthlete(inscriptionDTO.getAthlete())).thenReturn(athlete);


        ResponseEntity response = inscriptionController.updateInscription(inscriptionId, inscriptionDTO);


        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("The user of the inscription is disabled.", response.getBody());
    }

    @Test
    public void testUpdateInscription_BadRequest() {

        Long inscriptionId = 1L;
        InscriptionDTO inscriptionDTO = new InscriptionDTO();
        inscriptionDTO.setId(inscriptionId);
        inscriptionDTO.setTournament(1L);
        inscriptionDTO.setAthlete(1L);

        Tournament tournament = new Tournament();
        tournament.setId(1L);
        tournament.setEnabled(true);
        Athlete athlete = new Athlete();
        athlete.setId(1L);
        athlete.setEnabled(true);
        Inscription inscription = new Inscription();
        inscription.setId(inscriptionId);
        inscription.setTournament(tournament);
        inscription.setAthlete(athlete);

        when(inscriptionService.getInscription(inscriptionId)).thenReturn(inscription);
        when(tournamentService.getTournament(inscriptionDTO.getTournament())).thenReturn(tournament);
        when(athleteService.getAthlete(inscriptionDTO.getAthlete())).thenReturn(athlete);
        when(inscriptionService.validInscription(athlete, tournament)).thenReturn(true);


        ResponseEntity response = inscriptionController.updateInscription(inscriptionId, inscriptionDTO);


        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("You already have this tournament in your watchlists.", response.getBody());
    }

    @Test
    public void createInscription() {

        Tournament tournament = new Tournament();
        tournament.setId(1L);
        tournament.setEnabled(true);
        Athlete athlete = new Athlete();
        athlete.setId(1L);
        athlete.setEnabled(true);
        Inscription inscription = new Inscription();
        inscription.setTournament(tournament);
        inscription.setAthlete(athlete);
        InscriptionFrontDTO inscriptionFrontDTO = new InscriptionFrontDTO();
        inscriptionFrontDTO.setAthlete_id(athlete.getId());
        inscriptionFrontDTO.setAthlete(athlete.getUsername());
        inscriptionFrontDTO.setTournament_id(tournament.getId());
        inscriptionFrontDTO.setTournament(tournament.getName());


        when(tournamentService.getTournament(anyLong())).thenReturn(tournament);
        when(athleteService.getAthlete(anyLong())).thenReturn(athlete);
        when(inscriptionService.validInscription(any(),any())).thenReturn(false);
        when(inscriptionService.saveInscription(any())).thenReturn(inscription);


        ResponseEntity response = inscriptionController.createInscription(inscriptionFrontDTO);


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

    }

    @Test
    public void createInscriptionBadRequest() {

        InscriptionFrontDTO inscriptionFrontDTO = new InscriptionFrontDTO(/* Provide invalid data */);
        Tournament tournament = new Tournament(/* Provide tournament data */);
        Athlete athlete = new Athlete(/* Provide athlete data */);



        when(inscriptionService.validInscription(Mockito.any(), Mockito.any())).thenReturn(true);


        ResponseEntity response = inscriptionController.createInscription(inscriptionFrontDTO);


        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("You already have this tournament in your inscriptions.", response.getBody());
        assertNotNull(response.getBody());

    }

    @Test
    public void createInscription_TournamentNotFound() {

        Tournament tournament = new Tournament();
        tournament.setId(1L);
        tournament.setEnabled(true);
        Athlete athlete = new Athlete();
        athlete.setId(1L);
        athlete.setEnabled(true);
        Inscription inscription = new Inscription();
        inscription.setTournament(tournament);
        inscription.setAthlete(athlete);
        InscriptionFrontDTO inscriptionFrontDTO = new InscriptionFrontDTO();
        inscriptionFrontDTO.setAthlete_id(athlete.getId());
        inscriptionFrontDTO.setAthlete(athlete.getUsername());
        inscriptionFrontDTO.setTournament_id(null);
        inscriptionFrontDTO.setTournament(null);



        when(athleteService.getAthlete(anyLong())).thenReturn(athlete);
        when(inscriptionService.validInscription(any(),any())).thenReturn(false);


        ResponseEntity response = inscriptionController.createInscription(inscriptionFrontDTO);


        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("The tournament of the inscription doesnt exist.",response.getBody());
        assertNotNull(response.getBody());

    }

    @Test
    public void createInscription_AthleteNotFound() {

        Tournament tournament = new Tournament();
        tournament.setId(1L);
        tournament.setEnabled(true);
        Athlete athlete = new Athlete();
        athlete.setId(1L);
        athlete.setEnabled(true);
        Inscription inscription = new Inscription();
        inscription.setTournament(tournament);
        inscription.setAthlete(athlete);
        InscriptionFrontDTO inscriptionFrontDTO = new InscriptionFrontDTO();
        inscriptionFrontDTO.setAthlete_id(null);
        inscriptionFrontDTO.setAthlete(null);
        inscriptionFrontDTO.setTournament_id(tournament.getId());
        inscriptionFrontDTO.setTournament(tournament.getName());


        when(tournamentService.getTournament(anyLong())).thenReturn(tournament);
        when(inscriptionService.validInscription(any(),any())).thenReturn(false);


        ResponseEntity response = inscriptionController.createInscription(inscriptionFrontDTO);


        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("The user of the inscription doesnt exist.",response.getBody());
        assertNotNull(response.getBody());

    }

    @Test
    public void createInscription_TournamentDisabled() {

        Tournament tournament = new Tournament();
        tournament.setId(1L);
        tournament.setEnabled(false);
        Athlete athlete = new Athlete();
        athlete.setId(1L);
        athlete.setEnabled(true);
        Inscription inscription = new Inscription();
        inscription.setTournament(tournament);
        inscription.setAthlete(athlete);
        InscriptionFrontDTO inscriptionFrontDTO = new InscriptionFrontDTO();
        inscriptionFrontDTO.setAthlete_id(athlete.getId());
        inscriptionFrontDTO.setAthlete(athlete.getUsername());
        inscriptionFrontDTO.setTournament_id(tournament.getId());
        inscriptionFrontDTO.setTournament(tournament.getName());


        when(tournamentService.getTournament(anyLong())).thenReturn(tournament);
        when(athleteService.getAthlete(anyLong())).thenReturn(athlete);
        when(inscriptionService.validInscription(any(),any())).thenReturn(false);

        ResponseEntity response = inscriptionController.createInscription(inscriptionFrontDTO);


        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("The tournament of the inscription is disabled.",response.getBody());
        assertNotNull(response.getBody());

    }

    @Test
    public void createInscription_AthleteDisabled() {

        Tournament tournament = new Tournament();
        tournament.setId(1L);
        tournament.setEnabled(true);
        Athlete athlete = new Athlete();
        athlete.setId(1L);
        athlete.setEnabled(false);
        Inscription inscription = new Inscription();
        inscription.setTournament(tournament);
        inscription.setAthlete(athlete);
        InscriptionFrontDTO inscriptionFrontDTO = new InscriptionFrontDTO();
        inscriptionFrontDTO.setAthlete_id(athlete.getId());
        inscriptionFrontDTO.setAthlete(athlete.getUsername());
        inscriptionFrontDTO.setTournament_id(tournament.getId());
        inscriptionFrontDTO.setTournament(tournament.getName());


        when(tournamentService.getTournament(anyLong())).thenReturn(tournament);
        when(athleteService.getAthlete(anyLong())).thenReturn(athlete);
        when(inscriptionService.validInscription(any(),any())).thenReturn(false);


        ResponseEntity response = inscriptionController.createInscription(inscriptionFrontDTO);


        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("The user of the inscription is disabled.",response.getBody());
        assertNotNull(response.getBody());
    }

    @Test
    public void deleteInscription() {
        Tournament tournament = new Tournament();
        tournament.setId(1L);
        tournament.setEnabled(true);
        Athlete athlete = new Athlete();
        athlete.setId(1L);
        athlete.setEnabled(true);
        Inscription inscription = new Inscription();
        inscription.setTournament(tournament);
        inscription.setAthlete(athlete);
        Long inscriptionId = 1L;
        String username = "username";
        User user = new User();
        user.setId(1L);
        user.setUsername(username);
        InscriptionFrontDTO expectedDTO = new InscriptionFrontDTO();

        when(inscriptionService.getInscription(anyLong())).thenReturn(inscription);
        when(userService.isAuthorized()).thenReturn(user);


        ResponseEntity response = inscriptionController.deleteInscription(inscriptionId);


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

    }

    @Test
    public void deleteInscription_NotFound() {
        Tournament tournament = new Tournament();
        tournament.setId(1L);
        tournament.setEnabled(true);
        Athlete athlete = new Athlete();
        athlete.setId(1L);
        athlete.setEnabled(true);
        Inscription inscription = new Inscription();
        inscription.setTournament(tournament);
        inscription.setAthlete(athlete);
        Long inscriptionId = 1L;
        String username = "username";
        User user = new User();
        user.setId(1L);
        user.setUsername(username);
        InscriptionFrontDTO expectedDTO = new InscriptionFrontDTO();

        when(inscriptionService.getInscription(anyLong())).thenReturn(null);
        when(userService.isAuthorized()).thenReturn(user);


        ResponseEntity response = inscriptionController.deleteInscription(inscriptionId);


        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void deleteInscription_BadRequest() {
        Organizer organizer = new Organizer();
        organizer.setId(1L);
        Tournament tournament = new Tournament();
        tournament.setId(1L);
        tournament.setEnabled(true);
        tournament.setOrganizer(organizer);
        Athlete athlete = new Athlete();
        athlete.setId(1L);
        athlete.setEnabled(true);
        Inscription inscription = new Inscription();
        inscription.setTournament(tournament);
        inscription.setAthlete(athlete);
        Long inscriptionId = 1L;
        String username = "username";
        User user = new User();
        user.setId(2L);
        user.setUsername(username);
        InscriptionFrontDTO expectedDTO = new InscriptionFrontDTO();

        when(inscriptionService.getInscription(anyLong())).thenReturn(inscription);
        when(userService.isAuthorized()).thenReturn(user);


        ResponseEntity response = inscriptionController.deleteInscription(inscriptionId);


        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());

    }
}
