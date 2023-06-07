package ruben.SPM.controllers.EntitiesControllers.AdminControllersTests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ruben.SPM.controllers.EntitiesControllers.AdminController;
import ruben.SPM.model.DTO.Front.EventFrontDTO;
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
public class EventsAdminControllerTest {

    @InjectMocks
    private AdminController adminController;
    @Mock
    private AdminService adminService;
    @Mock
    private DeleteService deleteService;

    @Test
    public void testGetAllEvents() {
        Long tournamentId = 1L;
        Organizer organizer = new Organizer();
        Sports_type sportsType = new Sports_type();

        Tournament event = new Tournament();
        event.setId(1L);
        event.setInscription(false);
        event.setCapacity(1000);
        event.setOrganizer(organizer);
        event.setSport_type(sportsType);

        Tournament event1 = new Tournament();
        event1.setId(1L);
        event1.setInscription(true);
        event1.setCapacity(1000);
        event1.setOrganizer(organizer);
        event1.setSport_type(sportsType);

        List<Tournament> events = new ArrayList<>();
        events.add(event);
        events.add(event1);

        when(adminService.getAllEvents()).thenReturn(events);

        ResponseEntity response = adminController.getAllEvents_Admin();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(events.size(), ((List<EventFrontDTO>) response.getBody()).size());
        verify(adminService, times(1)).getAllEvents();
    }

    @Test
    public void testGetAllEvents_NotFound() {
        when(adminService.getAllEvents()).thenReturn(Collections.emptyList());

        ResponseEntity response = adminController.getAllEvents_Admin();

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("There are no events.", response.getBody());
        verify(adminService, times(1)).getAllEvents();
    }

    @Test
    public void testUpdateEvent() {
        Long eventId = 1L;
        Organizer organizer = new Organizer();
        Sports_type sportsType = new Sports_type();

        Tournament event = new Tournament();
        event.setId(1L);
        event.setInscription(false);
        event.setCapacity(1000);
        event.setOrganizer(organizer);
        event.setSport_type(sportsType);

        when(adminService.getTournament(eventId)).thenReturn(event);

        ResponseEntity response = adminController.updateEvent(eventId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(adminService, times(1)).getTournament(eventId);
        verify(adminService, times(1)).changeStateTournament(eventId);
    }

    @Test
    public void testUpdateEvent_EventNotFound() {
        Long eventId = 1L;

        when(adminService.getTournament(eventId)).thenReturn(null);

        ResponseEntity response = adminController.updateEvent(eventId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("There is no event with this id.", response.getBody());
        verify(adminService, times(1)).getTournament(eventId);
        verify(adminService, times(0)).changeStateTournament(anyLong());
    }

    @Test
    public void testDeleteEvent() {
        Long eventId = 1L;
        Organizer organizer = new Organizer();
        Sports_type sportsType = new Sports_type();

        Tournament event = new Tournament();
        event.setId(1L);
        event.setInscription(false);
        event.setCapacity(1000);
        event.setOrganizer(organizer);
        event.setSport_type(sportsType);

        when(adminService.getTournament(eventId)).thenReturn(event);

        ResponseEntity response = adminController.deleteEvent(eventId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(adminService, times(1)).getTournament(eventId);
        verify(deleteService, times(1)).deleteTournament(eventId);
    }

    @Test
    public void testDeleteEvent_EventNotFound() {
        Long eventId = 1L;

        when(adminService.getTournament(eventId)).thenReturn(null);

        ResponseEntity response = adminController.deleteEvent(eventId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("There is no event with this id.", response.getBody());
        verify(adminService, times(1)).getTournament(eventId);
        verify(deleteService, times(0)).deleteTournament(anyLong());
    }
}
