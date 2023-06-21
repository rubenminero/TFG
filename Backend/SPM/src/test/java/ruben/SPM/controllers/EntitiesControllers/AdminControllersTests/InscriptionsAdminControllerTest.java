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
import ruben.SPM.model.DTO.Entities.Sports_typeDTO;
import ruben.SPM.model.DTO.Front.InscriptionFrontDTO;
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
public class InscriptionsAdminControllerTest {

    @InjectMocks
    private AdminController adminController;
    @Mock
    private AdminService adminService;
    @Mock
    private DeleteService deleteService;
    @Test
    public void testGetAllInscriptionsAdmin_Success() {
        Athlete athlete = new Athlete();
        Organizer organizer = new Organizer();
        Sports_type sportsType = new Sports_type();

        Tournament tournament = new Tournament();
        tournament.setId(1L);
        tournament.setInscription(true);
        tournament.setCapacity(1000);
        tournament.setOrganizer(organizer);
        tournament.setSport_type(sportsType);

        Inscription inscription = new Inscription();
        inscription.setAthlete(athlete);
        inscription.setTournament(tournament);
        inscription.setEnabled(true);

        Inscription inscription1 = new Inscription();
        inscription1.setAthlete(athlete);
        inscription1.setTournament(tournament);
        inscription1.setEnabled(true);

        List<Inscription> inscriptions = new ArrayList<>();
        inscriptions.add(inscription);
        inscriptions.add(inscription1);

        when(adminService.getAllInscriptions()).thenReturn(inscriptions);

        ResponseEntity response = adminController.getAllInscriptionsAdmin();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(inscriptions.size(), ((List<InscriptionFrontDTO>) response.getBody()).size());
        verify(adminService, times(1)).getAllInscriptions();
    }

    @Test
    public void testGetAllInscriptions_NotFound() {
        when(adminService.getAllInscriptions()).thenReturn(Collections.emptyList());

        ResponseEntity response = adminController.getAllInscriptionsAdmin();

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("There are no inscriptions.", response.getBody());
        verify(adminService, times(1)).getAllInscriptions();
    }

    @Test
    public void testUpdateInscription() {
        Long inscriptionId = 1L;
        Athlete athlete = new Athlete();
        Organizer organizer = new Organizer();
        Sports_type sportsType = new Sports_type();

        Tournament tournament = new Tournament();
        tournament.setId(1L);
        tournament.setInscription(true);
        tournament.setCapacity(1000);
        tournament.setOrganizer(organizer);
        tournament.setSport_type(sportsType);

        Inscription inscription = new Inscription();
        inscription.setAthlete(athlete);
        inscription.setTournament(tournament);
        inscription.setEnabled(true);

        when(adminService.getInscription(inscriptionId)).thenReturn(inscription);

        ResponseEntity response = adminController.updateInscription(inscriptionId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(adminService, times(1)).getInscription(inscriptionId);
        verify(adminService, times(1)).changeStateInscription(inscriptionId);
    }

    @Test
    public void testUpdateInscription_NotFound() {
        Long inscriptionId = 1L;

        when(adminService.getInscription(inscriptionId)).thenReturn(null);

        ResponseEntity response = adminController.updateInscription(inscriptionId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("There is no inscription with this id.", response.getBody());
        verify(adminService, times(1)).getInscription(inscriptionId);
        verify(adminService, times(0)).changeStateInscription(anyLong());
    }

    @Test
    public void testDeleteInscription() {
        Long inscriptionId = 1L;
        Athlete athlete = new Athlete();
        Organizer organizer = new Organizer();
        Sports_type sportsType = new Sports_type();

        Tournament tournament = new Tournament();
        tournament.setId(1L);
        tournament.setInscription(true);
        tournament.setCapacity(1000);
        tournament.setOrganizer(organizer);
        tournament.setSport_type(sportsType);

        Inscription inscription = new Inscription();
        inscription.setAthlete(athlete);
        inscription.setTournament(tournament);
        inscription.setEnabled(true);

        when(adminService.getInscription(inscriptionId)).thenReturn(inscription);

        ResponseEntity response = adminController.deleteInscription(inscriptionId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(adminService, times(1)).getInscription(inscriptionId);
    }

    @Test
    public void testDeleteInscription_InscriptionNotFound() {
        Long inscriptionId = 1L;

        when(adminService.getInscription(inscriptionId)).thenReturn(null);

        ResponseEntity response = adminController.deleteInscription(inscriptionId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("There is no inscription with this id.", response.getBody());
        verify(adminService, times(1)).getInscription(inscriptionId);
        verify(deleteService, times(0)).deleteInscription(anyLong());
    }


}
