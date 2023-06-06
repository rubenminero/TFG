package ruben.SPM.controllers.EntitiesControllers;

import org.antlr.v4.runtime.atn.SemanticContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ruben.SPM.model.DTO.Entities.OrganizerDTO;
import ruben.SPM.model.Entities.*;
import ruben.SPM.service.EntitiesServices.OrganizerService;
import ruben.SPM.service.EntitiesServices.UserService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class OrganizerControllerTest {
    @InjectMocks
    private OrganizerController organizerController;
    @Mock
    private OrganizerService organizerService;
    @Mock
    private UserService userService;

    @Test
    public void testGetTournamentsOrganizer_NoFound() {
        User user = new User();
        user.setId(1L);
        when(organizerService.getTournamentsOrganizer(anyLong())).thenReturn(Collections.emptyList());

        ResponseEntity response = organizerController.getTournamentsOrganizer(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("There is no tournaments.", response.getBody());
    }

    @Test
    public void testGetTournamentsOrganizer() {
        User user = new User();
        user.setId(1L);
        Organizer organizer = new Organizer();
        organizer.setId(1L);
        Sports_type sportsType = new Sports_type();
        sportsType.setName("sport type");
        organizer.setId(1L);
        List<Tournament> tournaments = new ArrayList<>();
        Tournament tournament = new Tournament();
        tournament.setId(2L);
        tournament.setInscription(true);
        tournament.setEnabled(true);
        tournament.setCapacity(1000);
        tournament.setOrganizer(organizer);
        tournament.setSport_type(sportsType);
        tournaments.add(tournament);
        when(organizerService.getTournamentsOrganizer(anyLong())).thenReturn(tournaments);

        ResponseEntity response = organizerController.getTournamentsOrganizer(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testGetEventsOrganizer_NoFound() {
        User user = new User();
        user.setId(1L);
        when(organizerService.getEventsOrganizer(anyLong())).thenReturn(Collections.emptyList());

        ResponseEntity response = organizerController.getEventsOrganizer(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("There is no events.", response.getBody());
    }

    @Test
    public void testGetEventsOrganizer() {
        User user = new User();
        user.setId(1L);
        Organizer organizer = new Organizer();
        organizer.setId(1L);
        Sports_type sportsType = new Sports_type();
        sportsType.setName("sport type");
        organizer.setId(1L);
        List<Tournament> events = new ArrayList<>();
        Tournament event = new Tournament();
        event.setId(2L);
        event.setInscription(false);
        event.setEnabled(true);
        event.setCapacity(-1);
        event.setOrganizer(organizer);
        event.setSport_type(sportsType);
        events.add(event);
        when(organizerService.getEventsOrganizer(anyLong())).thenReturn(events);

        ResponseEntity response = organizerController.getEventsOrganizer(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testGetOrganizerById() {
        Organizer organizer = new Organizer();
        organizer.setEnabled(true);
        when(organizerService.getOrganizer(anyLong())).thenReturn(organizer);

        ResponseEntity response = organizerController.getOrganizerById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(new OrganizerDTO(organizer), response.getBody());
        verify(organizerService, times(1)).getOrganizer(anyLong());
    }

    @Test
    public void testGetOrganizerById_OrganizerDisabled() {
        Organizer organizer = new Organizer();
        organizer.setEnabled(false);
        when(organizerService.getOrganizer(anyLong())).thenReturn(organizer);

        ResponseEntity response = organizerController.getOrganizerById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("The organizer that you asked is disabled.", response.getBody());
        verify(organizerService, times(1)).getOrganizer(anyLong());
    }

    @Test
    public void testGetOrganizerById_OrganizerNotFound() {
        when(organizerService.getOrganizer(anyLong())).thenReturn(null);

        ResponseEntity response = organizerController.getOrganizerById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("The organizer that you asked doesnt exist.", response.getBody());
        verify(organizerService, times(1)).getOrganizer(anyLong());
    }

    @Test
    public void testUpdateOrganizer() {
        Organizer organizerSaved = new Organizer();
        organizerSaved.setId(1L);
        organizerSaved.setUsername("username1");
        organizerSaved.setEmail("email1@example.com");
        organizerSaved.setCompany("company1");

        Organizer organizerUpdate = new Organizer();
        organizerUpdate.setId(1L);
        organizerUpdate.setUsername("username2");
        organizerUpdate.setEmail("email2@example.com");
        organizerUpdate.setCompany("company2");

        when(organizerService.getOrganizer(1L)).thenReturn(organizerSaved);
        when(userService.validUsername("username2")).thenReturn(false);
        when(userService.validEmail("email2@example.com")).thenReturn(false);
        when(organizerService.validCompany_name("company2")).thenReturn(false);
        when(organizerService.updateOrganizer(organizerUpdate, organizerSaved)).thenReturn(organizerSaved);

        ResponseEntity response = organizerController.updateOrganizer(1L, organizerUpdate);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(new OrganizerDTO(organizerSaved), response.getBody());
        verify(organizerService, times(1)).getOrganizer(1L);
        verify(userService, times(1)).validUsername("username2");
        verify(userService, times(1)).validEmail("email2@example.com");
        verify(organizerService, times(1)).validCompany_name("company2");
        verify(organizerService, times(1)).updateOrganizer(organizerUpdate, organizerSaved);
    }

    @Test
    public void testUpdateOrganizer_OrganizerNotFound() {
        Organizer organizerSaved = new Organizer();
        organizerSaved.setId(1L);
        organizerSaved.setUsername("username1");
        organizerSaved.setEmail("email1@example.com");
        organizerSaved.setCompany("company1");

        Organizer organizerUpdate = new Organizer();
        organizerUpdate.setId(1L);
        organizerUpdate.setUsername("username2");
        organizerUpdate.setEmail("email2@example.com");
        organizerUpdate.setCompany("company2");




        ResponseEntity response = organizerController.updateOrganizer(1L, organizerUpdate);
        assertEquals("There is no user with that data.", response.getBody());
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    public void testUpdateOrganizer_UsernameInvalid() {
        Organizer organizerSaved = new Organizer();
        organizerSaved.setId(1L);
        organizerSaved.setUsername("username1");
        organizerSaved.setEmail("email1@example.com");
        organizerSaved.setCompany("company1");

        Organizer organizerUpdate = new Organizer();
        organizerUpdate.setId(1L);
        organizerUpdate.setUsername("username2");
        organizerUpdate.setEmail("email2@example.com");
        organizerUpdate.setCompany("company2");

        when(organizerService.getOrganizer(1L)).thenReturn(organizerSaved);
        when(userService.validUsername("username2")).thenReturn(true);

        ResponseEntity response = organizerController.updateOrganizer(1L, organizerUpdate);
        assertEquals("This username is already taken.", response.getBody());
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    public void testUpdateOrganizer_EmailInvalid() {
        Organizer organizerSaved = new Organizer();
        organizerSaved.setId(1L);
        organizerSaved.setUsername("username1");
        organizerSaved.setEmail("email1@example.com");
        organizerSaved.setCompany("company1");

        Organizer organizerUpdate = new Organizer();
        organizerUpdate.setId(1L);
        organizerUpdate.setUsername("username2");
        organizerUpdate.setEmail("email2@example.com");
        organizerUpdate.setCompany("company2");

        when(organizerService.getOrganizer(1L)).thenReturn(organizerSaved);
        when(userService.validUsername("username2")).thenReturn(false);
        when(userService.validEmail("email2@example.com")).thenReturn(true);

        ResponseEntity response = organizerController.updateOrganizer(1L, organizerUpdate);
        assertEquals("This email is already taken.", response.getBody());
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    public void testUpdateOrganizer_CompanyNameInvalid() {
        Organizer organizerSaved = new Organizer();
        organizerSaved.setId(1L);
        organizerSaved.setUsername("username1");
        organizerSaved.setEmail("email1@example.com");
        organizerSaved.setCompany("company1");

        Organizer organizerUpdate = new Organizer();
        organizerUpdate.setId(1L);
        organizerUpdate.setUsername("username2");
        organizerUpdate.setEmail("email2@example.com");
        organizerUpdate.setCompany("company2");

        when(organizerService.getOrganizer(1L)).thenReturn(organizerSaved);
        when(userService.validUsername("username2")).thenReturn(false);
        when(userService.validEmail("email2@example.com")).thenReturn(false);
        when(organizerService.validCompany_name("company2")).thenReturn(true);

        ResponseEntity response = organizerController.updateOrganizer(1L, organizerUpdate);
        assertEquals("This company name is already taken.", response.getBody());
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    public void testDeleteOrganizer() {
        Organizer organizer = new Organizer();
        organizer.setId(1L);
        organizer.setUsername("user1");
        User user = new User();
        user.setId(1L);
        user.setUsername("user1");

        when(organizerService.getOrganizer(1L)).thenReturn(organizer);
        when(userService.isAuthorized()).thenReturn(user);


        ResponseEntity response = organizerController.deleteOrganizer(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testDeleteOrganizer_OrganizerNotFound() {
        Organizer organizer = new Organizer();
        organizer.setId(1L);
        organizer.setUsername("user1");
        User user = new User();
        user.setId(1L);
        user.setUsername("user1");

        when(organizerService.getOrganizer(1L)).thenReturn(null);
        when(userService.isAuthorized()).thenReturn(user);

        ResponseEntity response = organizerController.deleteOrganizer(1L);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    public void testDeleteOrganizer_OrganizerNotOwnedByUser() {
        Organizer organizer = new Organizer();
        organizer.setId(1L);
        organizer.setUsername("user1");
        User user = new User();
        user.setId(2L);
        user.setUsername("user1");

        when(organizerService.getOrganizer(1L)).thenReturn(organizer);
        when(userService.isAuthorized()).thenReturn(user);

        ResponseEntity response = organizerController.deleteOrganizer(1L);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("The id provided doesnt belong to your user.", response.getBody());
    }


}
