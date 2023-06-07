package ruben.SPM.controllers.EntitiesControllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ruben.SPM.model.DTO.Front.EventFrontDTO;
import ruben.SPM.model.DTO.Front.TournamentFrontDTO;
import ruben.SPM.model.Entities.*;
import ruben.SPM.service.EntitiesServices.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TournamentControllerTest {
    @InjectMocks
    private TournamentController tournamentController;
    @Mock
    private TournamentService tournamentService;
    @Mock
    private OrganizerService organizerService;
    @Mock
    private Sports_typeService sportsTypeService;
    @Mock
    private UserService userService;
    @Mock
    private InscriptionService inscriptionService;

    @Test
    public void testGetAllTournaments() {
        Organizer organizer = new Organizer();
        Sports_type sportsType = new Sports_type();
        List<Tournament> tournaments = new ArrayList<>();
        Tournament tournament = new Tournament();
        tournament.setInscription(true);
        tournament.setCapacity(1000);
        tournament.setOrganizer(organizer);
        tournament.setSport_type(sportsType);
        Tournament tournament1 = new Tournament();
        tournament1.setInscription(true);
        tournament1.setCapacity(1000);
        tournament1.setOrganizer(organizer);
        tournament1.setSport_type(sportsType);

        tournaments.add(tournament);
        tournaments.add(tournament1);

        when(tournamentService.getEnabledTournaments()).thenReturn(tournaments);

        ResponseEntity response = tournamentController.getAllTournaments();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(tournamentService, times(1)).getEnabledTournaments();
    }

    @Test
    public void testGetAllTournaments_NotFound() {
        List<Tournament> tournaments = new ArrayList<>();

        when(tournamentService.getEnabledTournaments()).thenReturn(tournaments);

        ResponseEntity response = tournamentController.getAllTournaments();

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("There is no tournaments.", response.getBody());
        verify(tournamentService, times(1)).getEnabledTournaments();
    }

    @Test
    public void testGetAllEvents() {
        Organizer organizer = new Organizer();
        Sports_type sportsType = new Sports_type();
        List<Tournament> events = new ArrayList<>();
        Tournament event = new Tournament();
        event.setInscription(false);
        event.setCapacity(-1);
        event.setOrganizer(organizer);
        event.setSport_type(sportsType);
        Tournament event1 = new Tournament();
        event1.setInscription(false);
        event1.setCapacity(-1);
        event1.setOrganizer(organizer);
        event1.setSport_type(sportsType);

        events.add(event);
        events.add(event1);

        when(tournamentService.getEnabledEvents()).thenReturn(events);

        ResponseEntity response = tournamentController.getAllEvents();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(tournamentService, times(1)).getEnabledEvents();
    }

    @Test
    public void testGetAllEventsNotFound() {
        List<Tournament> events = new ArrayList<>();

        when(tournamentService.getEnabledEvents()).thenReturn(events);

        ResponseEntity response = tournamentController.getAllEvents();

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("There is no events.", response.getBody());
        verify(tournamentService, times(1)).getEnabledEvents();
    }


    @Test
    public void testGetTournamentById() {
        Organizer organizer = new Organizer();
        Sports_type sportsType = new Sports_type();
        Tournament tournament = new Tournament();
        tournament.setId(1L);
        tournament.setInscription(true);
        tournament.setCapacity(1000);
        tournament.setOrganizer(organizer);
        tournament.setSport_type(sportsType);

        when(tournamentService.getTournament(tournament.getId())).thenReturn(tournament);

        ResponseEntity response = tournamentController.getTournamentById(tournament.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(tournamentService, times(1)).getTournament(tournament.getId());
    }

    @Test
    public void testGetTournamentById_Disabled() {
        Long tournamentId = 1L;
        Tournament tournament = new Tournament();
        tournament.setId(tournamentId);
        tournament.setEnabled(false);

        when(tournamentService.getTournament(tournamentId)).thenReturn(tournament);

        ResponseEntity response = tournamentController.getTournamentById(tournamentId);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals("The tournament that you asked is disabled.", response.getBody());
        verify(tournamentService, times(1)).getTournament(tournamentId);
    }

    @Test
    public void testGetTournamentById_DoesntExist() {
        Long tournamentId = 1L;

        when(tournamentService.getTournament(tournamentId)).thenReturn(null);

        ResponseEntity response = tournamentController.getTournamentById(tournamentId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("The tournament by id has not been found", response.getBody());
        verify(tournamentService, times(1)).getTournament(tournamentId);
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

        TournamentFrontDTO tournamentDTO = new TournamentFrontDTO(tournament);
        tournamentDTO.setId(tournamentId);
        tournamentDTO.setOrganizer("Organizer");
        tournamentDTO.setSport_type("Sport Type");

        when(tournamentService.getTournament(tournamentId)).thenReturn(tournament);
        when(organizerService.getOrganizerByCompany_name(tournamentDTO.getOrganizer())).thenReturn(organizer);
        when(sportsTypeService.getSport_typeByName(tournamentDTO.getSport_type())).thenReturn(sportsType);


        ResponseEntity response = tournamentController.updateTournament(tournamentId, tournamentDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(organizerService, times(1)).getOrganizerByCompany_name(tournamentDTO.getOrganizer());
        verify(sportsTypeService, times(1)).getSport_typeByName(tournamentDTO.getSport_type());
    }

    @Test
    public void testUpdateTournament_InvalidId() {
        Long tournamentId = 1L;
        TournamentFrontDTO tournamentDTO = new TournamentFrontDTO();
        tournamentDTO.setId(tournamentId);
        tournamentDTO.setOrganizer("Organizer");
        tournamentDTO.setSport_type("Sport Type");
        tournamentDTO.setCapacity(100);
        tournamentDTO.setInscription(true);

        Organizer organizer = new Organizer();
        Sports_type sportsType = new Sports_type();

        Tournament tournament = new Tournament();
        tournament.setId(1L);
        tournament.setInscription(true);
        tournament.setCapacity(1000);
        tournament.setOrganizer(organizer);
        tournament.setSport_type(sportsType);


        ResponseEntity response = tournamentController.updateTournament(2L, tournamentDTO);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals("Bad request, the id given in the path doesn't match with the id on the tournament.", response.getBody());
        verify(tournamentService, never()).getTournament(anyLong());
        verify(organizerService, never()).getOrganizerByCompany_name(anyString());
        verify(sportsTypeService, never()).getSport_typeByName(anyString());
        verify(tournamentService, never()).saveTournament(any());
    }

    @Test
    public void testUpdateTournament_TournamentNotFound() {
        Long tournamentId = 1L;
        TournamentFrontDTO tournamentDTO = new TournamentFrontDTO();
        tournamentDTO.setId(tournamentId);
        tournamentDTO.setOrganizer("Organizer");
        tournamentDTO.setSport_type("Sport Type");
        tournamentDTO.setCapacity(100);
        tournamentDTO.setInscription(true);

        Organizer organizer = new Organizer();
        Sports_type sportsType = new Sports_type();

        Tournament tournament = new Tournament();
        tournament.setId(1L);
        tournament.setInscription(true);
        tournament.setCapacity(1000);
        tournament.setOrganizer(organizer);
        tournament.setSport_type(sportsType);

        when(tournamentService.getTournament(tournamentId)).thenReturn(null);




        ResponseEntity response = tournamentController.updateTournament(tournamentId, tournamentDTO);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Bad request, there is no tournament for that id.", response.getBody());
        verify(tournamentService, times(1)).getTournament(tournamentId);
        verify(organizerService, never()).getOrganizerByCompany_name(anyString());
        verify(sportsTypeService, never()).getSport_typeByName(anyString());
        verify(tournamentService, never()).saveTournament(any());
    }

    @Test
    public void testUpdateTournament_InvalidOrganizer() {
        Long tournamentId = 1L;
        TournamentFrontDTO tournamentDTO = new TournamentFrontDTO();
        tournamentDTO.setId(tournamentId);
        tournamentDTO.setOrganizer("Organizer");
        tournamentDTO.setSport_type("Sport Type");
        tournamentDTO.setCapacity(100);
        tournamentDTO.setInscription(true);

        Organizer organizer = new Organizer();
        Sports_type sportsType = new Sports_type();

        Tournament tournament = new Tournament();
        tournament.setId(1L);
        tournament.setInscription(true);
        tournament.setCapacity(1000);
        tournament.setOrganizer(organizer);
        tournament.setSport_type(sportsType);

        when(tournamentService.getTournament(tournamentId)).thenReturn(tournament);
        when(organizerService.getOrganizerByCompany_name(tournamentDTO.getOrganizer())).thenReturn(null);
        when(sportsTypeService.getSport_typeByName(tournamentDTO.getSport_type())).thenReturn(sportsType);


        ResponseEntity response = tournamentController.updateTournament(tournamentId, tournamentDTO);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("The organizer of the tournament doesn't exist.", response.getBody());
        verify(tournamentService, times(1)).getTournament(tournamentId);
        verify(organizerService, times(1)).getOrganizerByCompany_name(tournamentDTO.getOrganizer());
        verify(tournamentService, never()).saveTournament(any());
    }

    @Test
    public void testUpdateTournament_InvalidSportType() {
        Long tournamentId = 1L;
        TournamentFrontDTO tournamentDTO = new TournamentFrontDTO();
        tournamentDTO.setId(tournamentId);
        tournamentDTO.setOrganizer("Organizer");
        tournamentDTO.setSport_type("Sport Type");
        tournamentDTO.setCapacity(100);
        tournamentDTO.setInscription(true);

        Organizer organizer = new Organizer();
        Sports_type sportsType = new Sports_type();

        Tournament tournament = new Tournament();
        tournament.setId(1L);
        tournament.setInscription(true);
        tournament.setCapacity(1000);
        tournament.setOrganizer(organizer);
        tournament.setSport_type(sportsType);

        when(tournamentService.getTournament(tournamentId)).thenReturn(tournament);
        when(organizerService.getOrganizerByCompany_name(tournamentDTO.getOrganizer())).thenReturn(organizer);
        when(sportsTypeService.getSport_typeByName(tournamentDTO.getSport_type())).thenReturn(null);


        ResponseEntity response = tournamentController.updateTournament(tournamentId, tournamentDTO);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("The sport type for the tournament doesn't exist.", response.getBody());
        verify(tournamentService, times(1)).getTournament(tournamentId);
        verify(organizerService, times(1)).getOrganizerByCompany_name(tournamentDTO.getOrganizer());
        verify(sportsTypeService, times(1)).getSport_typeByName(tournamentDTO.getSport_type());
        verify(tournamentService, never()).saveTournament(any());
    }


    @Test
    public void testUpdateEvent() {
        Long tournamentId = 1L;
        Organizer organizer = new Organizer();
        Sports_type sportsType = new Sports_type();

        Tournament tournament = new Tournament();
        tournament.setId(1L);
        tournament.setInscription(true);
        tournament.setCapacity(1000);
        tournament.setOrganizer(organizer);
        tournament.setSport_type(sportsType);

        EventFrontDTO tournamentDTO = new EventFrontDTO(tournament);
        tournamentDTO.setId(tournamentId);
        tournamentDTO.setOrganizer("Organizer");
        tournamentDTO.setSport_type("Sport Type");

        when(tournamentService.getTournament(tournamentId)).thenReturn(tournament);
        when(organizerService.getOrganizerByCompany_name(tournamentDTO.getOrganizer())).thenReturn(organizer);
        when(sportsTypeService.getSport_typeByName(tournamentDTO.getSport_type())).thenReturn(sportsType);


        ResponseEntity response = tournamentController.updateEvent(tournamentId, tournamentDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(organizerService, times(1)).getOrganizerByCompany_name(tournamentDTO.getOrganizer());
        verify(sportsTypeService, times(1)).getSport_typeByName(tournamentDTO.getSport_type());
    }

    @Test
    public void testUpdateEvent_InvalidId() {
        Long tournamentId = 1L;
        Organizer organizer = new Organizer();
        Sports_type sportsType = new Sports_type();

        Tournament tournament = new Tournament();
        tournament.setId(1L);
        tournament.setInscription(true);
        tournament.setCapacity(1000);
        tournament.setOrganizer(organizer);
        tournament.setSport_type(sportsType);

        EventFrontDTO tournamentDTO = new EventFrontDTO(tournament);
        tournamentDTO.setId(tournamentId);
        tournamentDTO.setOrganizer("Organizer");
        tournamentDTO.setSport_type("Sport Type");


        ResponseEntity response = tournamentController.updateEvent(2L, tournamentDTO);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals("Bad request, the id given in the path doesn't match with the id on the event.", response.getBody());
        verify(tournamentService, never()).getTournament(anyLong());
        verify(organizerService, never()).getOrganizerByCompany_name(anyString());
        verify(sportsTypeService, never()).getSport_typeByName(anyString());
        verify(tournamentService, never()).saveTournament(any());
    }

    @Test
    public void testUpdateEvent_TournamentNotFound() {
        Long tournamentId = 1L;
        Organizer organizer = new Organizer();
        Sports_type sportsType = new Sports_type();

        Tournament tournament = new Tournament();
        tournament.setId(1L);
        tournament.setInscription(true);
        tournament.setCapacity(1000);
        tournament.setOrganizer(organizer);
        tournament.setSport_type(sportsType);

        EventFrontDTO tournamentDTO = new EventFrontDTO(tournament);
        tournamentDTO.setId(tournamentId);
        tournamentDTO.setOrganizer("Organizer");
        tournamentDTO.setSport_type("Sport Type");

        when(tournamentService.getTournament(tournamentId)).thenReturn(null);



        ResponseEntity response = tournamentController.updateEvent(tournamentId, tournamentDTO);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Bad request, there is no event for that id.", response.getBody());
        verify(tournamentService, times(1)).getTournament(tournamentId);
        verify(organizerService, never()).getOrganizerByCompany_name(anyString());
        verify(sportsTypeService, never()).getSport_typeByName(anyString());
        verify(tournamentService, never()).saveTournament(any());
    }

    @Test
    public void testUpdateEvent_InvalidOrganizer() {
        Long tournamentId = 1L;
        Organizer organizer = new Organizer();
        Sports_type sportsType = new Sports_type();

        Tournament tournament = new Tournament();
        tournament.setId(1L);
        tournament.setInscription(true);
        tournament.setCapacity(1000);
        tournament.setOrganizer(organizer);
        tournament.setSport_type(sportsType);

        EventFrontDTO tournamentDTO = new EventFrontDTO(tournament);
        tournamentDTO.setId(tournamentId);
        tournamentDTO.setOrganizer("Organizer");
        tournamentDTO.setSport_type("Sport Type");

        when(tournamentService.getTournament(tournamentId)).thenReturn(tournament);
        when(organizerService.getOrganizerByCompany_name(tournamentDTO.getOrganizer())).thenReturn(null);
        when(sportsTypeService.getSport_typeByName(tournamentDTO.getSport_type())).thenReturn(sportsType);


        ResponseEntity response = tournamentController.updateEvent(tournamentId, tournamentDTO);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("The organizer of the event doesn't exist.", response.getBody());
        verify(tournamentService, times(1)).getTournament(tournamentId);
        verify(organizerService, times(1)).getOrganizerByCompany_name(tournamentDTO.getOrganizer());
        verify(tournamentService, never()).saveTournament(any());
    }

    @Test
    public void testUpdateEvent_InvalidSportType() {
        Long tournamentId = 1L;
        Organizer organizer = new Organizer();
        Sports_type sportsType = new Sports_type();

        Tournament tournament = new Tournament();
        tournament.setId(1L);
        tournament.setInscription(true);
        tournament.setCapacity(1000);
        tournament.setOrganizer(organizer);
        tournament.setSport_type(sportsType);

        EventFrontDTO tournamentDTO = new EventFrontDTO(tournament);
        tournamentDTO.setId(tournamentId);
        tournamentDTO.setOrganizer("Organizer");
        tournamentDTO.setSport_type("Sport Type");

        when(tournamentService.getTournament(tournamentId)).thenReturn(tournament);
        when(organizerService.getOrganizerByCompany_name(tournamentDTO.getOrganizer())).thenReturn(organizer);
        when(sportsTypeService.getSport_typeByName(tournamentDTO.getSport_type())).thenReturn(null);


        ResponseEntity response = tournamentController.updateEvent(tournamentId, tournamentDTO);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("The sport type for the event doesn't exist.", response.getBody());
        verify(tournamentService, times(1)).getTournament(tournamentId);
        verify(organizerService, times(1)).getOrganizerByCompany_name(tournamentDTO.getOrganizer());
        verify(sportsTypeService, times(1)).getSport_typeByName(tournamentDTO.getSport_type());
        verify(tournamentService, never()).saveTournament(any());
    }

    @Test
    public void testCreateTournament() {
        User user = User.builder()
                .id(1L)
                .username("Organizer")
                .email("user@example.com")
                .build();
        Long tournamentId = 1L;
        Organizer organizer = new Organizer();
        organizer.setUsername("Organizer");
        Sports_type sportsType = new Sports_type();
        sportsType.setName("Sport Type");

        Tournament tournament = new Tournament();
        tournament.setId(1L);
        tournament.setInscription(true);
        tournament.setCapacity(1000);
        tournament.setOrganizer(organizer);
        tournament.setSport_type(sportsType);

        TournamentFrontDTO tournamentDTO = new TournamentFrontDTO(tournament);
        tournamentDTO.setId(tournamentId);
        tournamentDTO.setOrganizer("Organizer");
        tournamentDTO.setSport_type("Sport Type");
        tournamentDTO.setInscription(true);
        tournamentDTO.setCapacity(1000);

        when(userService.isAuthorized()).thenReturn(user);
        when(organizerService.getOrganizerByUsername(organizer.getUsername())).thenReturn(organizer);
        when(sportsTypeService.getSport_typeByName(tournamentDTO.getSport_type())).thenReturn(sportsType);


        ResponseEntity response = tournamentController.createTournament(tournamentDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(organizerService, times(1)).getOrganizerByUsername(organizer.getUsername());
        verify(sportsTypeService, times(1)).getSport_typeByName(tournamentDTO.getSport_type());
    }

    @Test
    public void testCreateTournament_InvalidOrganizer() {
        User user = User.builder()
                .id(1L)
                .username("Organizer")
                .email("user@example.com")
                .build();
        Long tournamentId = 1L;
        Organizer organizer = new Organizer();
        organizer.setUsername("Organizer");
        Sports_type sportsType = new Sports_type();
        sportsType.setName("Sport Type");

        Tournament tournament = new Tournament();
        tournament.setId(1L);
        tournament.setInscription(true);
        tournament.setCapacity(1000);
        tournament.setOrganizer(organizer);
        tournament.setSport_type(sportsType);

        TournamentFrontDTO tournamentDTO = new TournamentFrontDTO(tournament);
        tournamentDTO.setId(tournamentId);
        tournamentDTO.setOrganizer("Organizer");
        tournamentDTO.setSport_type("Sport Type");
        tournamentDTO.setInscription(true);
        tournamentDTO.setCapacity(1000);

        when(userService.isAuthorized()).thenReturn(user);
        when(organizerService.getOrganizerByUsername(organizer.getUsername())).thenReturn(null);
        when(sportsTypeService.getSport_typeByName(tournamentDTO.getSport_type())).thenReturn(sportsType);


        ResponseEntity response = tournamentController.createTournament(tournamentDTO);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("The organizer of the tournament doesn't exist.", response.getBody());
        verify(organizerService, times(1)).getOrganizerByUsername(user.getUsername());
        verify(tournamentService, never()).saveTournament(any());
    }

    @Test
    public void testCreateTournament_InvalidSportType() {
        User user = User.builder()
                .id(1L)
                .username("Organizer")
                .email("user@example.com")
                .build();
        Long tournamentId = 1L;
        Organizer organizer = new Organizer();
        organizer.setUsername("Organizer");
        Sports_type sportsType = new Sports_type();
        sportsType.setName("Sport Type");

        Tournament tournament = new Tournament();
        tournament.setId(1L);
        tournament.setInscription(true);
        tournament.setCapacity(1000);
        tournament.setOrganizer(organizer);
        tournament.setSport_type(sportsType);

        TournamentFrontDTO tournamentDTO = new TournamentFrontDTO(tournament);
        tournamentDTO.setId(tournamentId);
        tournamentDTO.setOrganizer("Organizer");
        tournamentDTO.setSport_type("Sport Type");
        tournamentDTO.setInscription(true);
        tournamentDTO.setCapacity(1000);

        when(userService.isAuthorized()).thenReturn(user);
        when(organizerService.getOrganizerByUsername(organizer.getUsername())).thenReturn(organizer);
        when(sportsTypeService.getSport_typeByName(tournamentDTO.getSport_type())).thenReturn(null);


        ResponseEntity response = tournamentController.createTournament(tournamentDTO);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("The sport type for the tournament doesn't exist.", response.getBody());
        verify(organizerService, times(1)).getOrganizerByUsername(user.getUsername());
        verify(sportsTypeService, times(1)).getSport_typeByName(tournamentDTO.getSport_type());
        verify(tournamentService, never()).saveTournament(any());
    }


    @Test
    public void testCreateEvent() {
        User user = User.builder()
                .id(1L)
                .username("Organizer")
                .email("user@example.com")
                .build();
        Long tournamentId = 1L;
        Organizer organizer = new Organizer();
        organizer.setUsername("Organizer");
        Sports_type sportsType = new Sports_type();
        sportsType.setName("Sport Type");

        Tournament tournament = new Tournament();
        tournament.setId(1L);
        tournament.setInscription(false);
        tournament.setCapacity(-1);
        tournament.setOrganizer(organizer);
        tournament.setSport_type(sportsType);

        EventFrontDTO eventFrontDTO = new EventFrontDTO(tournament);
        eventFrontDTO.setId(tournamentId);
        eventFrontDTO.setOrganizer("Organizer");
        eventFrontDTO.setSport_type("Sport Type");

        when(userService.isAuthorized()).thenReturn(user);
        when(organizerService.getOrganizerByUsername(organizer.getUsername())).thenReturn(organizer);
        when(sportsTypeService.getSport_typeByName(eventFrontDTO.getSport_type())).thenReturn(sportsType);


        ResponseEntity response = tournamentController.createEvent(eventFrontDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(organizerService, times(1)).getOrganizerByUsername(organizer.getUsername());
        verify(sportsTypeService, times(1)).getSport_typeByName(eventFrontDTO.getSport_type());
    }

    @Test
    public void testCreateEvent_InvalidOrganizer() {
        User user = User.builder()
                .id(1L)
                .username("Organizer")
                .email("user@example.com")
                .build();
        Long tournamentId = 1L;
        Organizer organizer = new Organizer();
        organizer.setUsername("Organizer");
        Sports_type sportsType = new Sports_type();
        sportsType.setName("Sport Type");

        Tournament tournament = new Tournament();
        tournament.setId(1L);
        tournament.setInscription(false);
        tournament.setCapacity(-1);
        tournament.setOrganizer(organizer);
        tournament.setSport_type(sportsType);

        EventFrontDTO eventFrontDTO = new EventFrontDTO(tournament);
        eventFrontDTO.setId(tournamentId);
        eventFrontDTO.setOrganizer("Organizer");
        eventFrontDTO.setSport_type("Sport Type");

        when(userService.isAuthorized()).thenReturn(user);
        when(organizerService.getOrganizerByUsername(organizer.getUsername())).thenReturn(null);
        when(sportsTypeService.getSport_typeByName(eventFrontDTO.getSport_type())).thenReturn(sportsType);


        ResponseEntity response = tournamentController.createEvent(eventFrontDTO);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("The organizer of the event doesn't exist.", response.getBody());
        verify(organizerService, times(1)).getOrganizerByUsername(user.getUsername());
        verify(tournamentService, never()).saveTournament(any());
    }

    @Test
    public void testCreateEvent_InvalidSportType() {
        User user = User.builder()
                .id(1L)
                .username("Organizer")
                .email("user@example.com")
                .build();
        Long tournamentId = 1L;
        Organizer organizer = new Organizer();
        organizer.setUsername("Organizer");
        Sports_type sportsType = new Sports_type();
        sportsType.setName("Sport Type");

        Tournament tournament = new Tournament();
        tournament.setId(1L);
        tournament.setInscription(false);
        tournament.setCapacity(-1);
        tournament.setOrganizer(organizer);
        tournament.setSport_type(sportsType);

        EventFrontDTO eventFrontDTO = new EventFrontDTO(tournament);
        eventFrontDTO.setId(tournamentId);
        eventFrontDTO.setOrganizer("Organizer");
        eventFrontDTO.setSport_type("Sport Type");

        when(userService.isAuthorized()).thenReturn(user);
        when(organizerService.getOrganizerByUsername(organizer.getUsername())).thenReturn(organizer);
        when(sportsTypeService.getSport_typeByName(eventFrontDTO.getSport_type())).thenReturn(null);


        ResponseEntity response = tournamentController.createEvent(eventFrontDTO);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("The sport type for the event doesn't exist.", response.getBody());
        verify(organizerService, times(1)).getOrganizerByUsername(user.getUsername());
        verify(sportsTypeService, times(1)).getSport_typeByName(eventFrontDTO.getSport_type());
        verify(tournamentService, never()).saveTournament(any());
    }


    @Test
    public void testDeleteTournament() {
        User user = User.builder()
                .id(1L)
                .username("Organizer")
                .email("user@example.com")
                .build();
        Long tournamentId = 1L;
        Organizer organizer = new Organizer();
        organizer.setId(1L);
        organizer.setUsername("Organizer");
        Sports_type sportsType = new Sports_type();
        sportsType.setName("Sport Type");

        Tournament tournament = new Tournament();
        tournament.setId(1L);
        tournament.setInscription(false);
        tournament.setCapacity(-1);
        tournament.setOrganizer(organizer);
        tournament.setSport_type(sportsType);

        when(userService.isAuthorized()).thenReturn(user);
        when(tournamentService.getTournament(tournamentId)).thenReturn(tournament);

        ResponseEntity response = tournamentController.deleteTournament(tournamentId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(userService, times(1)).isAuthorized();
        verify(tournamentService, times(1)).getTournament(tournamentId);
    }

    @Test
    public void testDeleteTournament_InvalidOrganizerId() {
        User user = User.builder()
                .id(1L)
                .username("Organizer")
                .email("user@example.com")
                .build();
        Long tournamentId = 1L;
        Organizer organizer = new Organizer();
        organizer.setId(2L);
        organizer.setUsername("Organizer");
        Sports_type sportsType = new Sports_type();
        sportsType.setName("Sport Type");

        Tournament tournament = new Tournament();
        tournament.setId(1L);
        tournament.setInscription(false);
        tournament.setCapacity(-1);
        tournament.setOrganizer(organizer);
        tournament.setSport_type(sportsType);

        when(userService.isAuthorized()).thenReturn(user);
        when(tournamentService.getTournament(tournamentId)).thenReturn(tournament);

        ResponseEntity response = tournamentController.deleteTournament(tournamentId);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals("Bad request, the organizer id for the tournament doesn't match with the id on the organizer.", response.getBody());
        verify(userService, times(1)).isAuthorized();
        verify(tournamentService, times(1)).getTournament(tournamentId);
        verify(tournamentService, never()).deleteTournament(tournamentId);
    }

    @Test
    public void testDeleteTournament_InvalidTournamentId() {
        User user = User.builder()
                .id(1L)
                .username("Organizer")
                .email("user@example.com")
                .build();
        Long tournamentId = 1L;
        Organizer organizer = new Organizer();
        organizer.setId(1L);
        organizer.setUsername("Organizer");
        Sports_type sportsType = new Sports_type();
        sportsType.setName("Sport Type");

        Tournament tournament = new Tournament();
        tournament.setId(1L);
        tournament.setInscription(false);
        tournament.setCapacity(-1);
        tournament.setOrganizer(organizer);
        tournament.setSport_type(sportsType);

        when(userService.isAuthorized()).thenReturn(user);
        when(tournamentService.getTournament(tournamentId)).thenReturn(null);

        ResponseEntity response = tournamentController.deleteTournament(tournamentId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Bad request, there is no tournament for that id.", response.getBody());
        verify(userService, times(1)).isAuthorized();
        verify(tournamentService, times(1)).getTournament(tournamentId);
        verify(tournamentService, never()).deleteTournament(tournamentId);
    }


    @Test
    public void testGetInscriptionsforTournament() {
        User user = User.builder()
                .id(1L)
                .username("Organizer")
                .email("user@example.com")
                .build();
        Long tournamentId = 1L;
        Athlete athlete = new Athlete();
        athlete.setId(1L);
        Organizer organizer = new Organizer();
        organizer.setId(1L);
        organizer.setUsername("Organizer");
        Sports_type sportsType = new Sports_type();
        sportsType.setName("Sport Type");

        Tournament tournament = new Tournament();
        tournament.setId(1L);
        tournament.setEnabled(true);
        tournament.setInscription(false);
        tournament.setCapacity(-1);
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

        when(userService.isAuthorized()).thenReturn(user);
        when(tournamentService.getTournament(tournamentId)).thenReturn(tournament);
        when(inscriptionService.getInscriptionsforTournament(tournamentId)).thenReturn(inscriptions);

        ResponseEntity response = tournamentController.getInscriptionsforTournament(tournamentId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(userService, times(1)).isAuthorized();
        verify(tournamentService, times(1)).getTournament(tournamentId);
        verify(inscriptionService, times(1)).getInscriptionsforTournament(tournamentId);
    }

    @Test
    public void testGetInscriptionsforTournament_InvalidUser() {
        User user = User.builder()
                .id(1L)
                .username("Organizer")
                .email("user@example.com")
                .build();
        Long tournamentId = 1L;
        Athlete athlete = new Athlete();
        athlete.setId(1L);
        Organizer organizer = new Organizer();
        organizer.setId(1L);
        organizer.setUsername("Organizer");
        Sports_type sportsType = new Sports_type();
        sportsType.setName("Sport Type");

        Tournament tournament = new Tournament();
        tournament.setId(1L);
        tournament.setEnabled(true);
        tournament.setInscription(false);
        tournament.setCapacity(-1);
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

        when(userService.isAuthorized()).thenReturn(null);
        when(tournamentService.getTournament(tournamentId)).thenReturn(tournament);


        ResponseEntity response = tournamentController.getInscriptionsforTournament(tournamentId);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals("This user can't do that operation.", response.getBody());
        verify(userService, times(1)).isAuthorized();
        verify(inscriptionService, never()).getInscriptionsforTournament(tournamentId);
    }

    @Test
    public void testGetInscriptionsforTournament_InvalidTournamentId() {
        User user = User.builder()
                .id(1L)
                .username("Organizer")
                .email("user@example.com")
                .build();
        Long tournamentId = 1L;
        Athlete athlete = new Athlete();
        athlete.setId(1L);
        Organizer organizer = new Organizer();
        organizer.setId(1L);
        organizer.setUsername("Organizer");
        Sports_type sportsType = new Sports_type();
        sportsType.setName("Sport Type");

        Tournament tournament = new Tournament();
        tournament.setId(1L);
        tournament.setEnabled(true);
        tournament.setInscription(false);
        tournament.setCapacity(-1);
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

        when(userService.isAuthorized()).thenReturn(user);
        when(tournamentService.getTournament(tournamentId)).thenReturn(null);


        ResponseEntity response = tournamentController.getInscriptionsforTournament(tournamentId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("The tournament by id has not been found.", response.getBody());
        verify(userService, times(1)).isAuthorized();
        verify(tournamentService, times(1)).getTournament(tournamentId);
        verify(inscriptionService, never()).getInscriptionsforTournament(tournamentId);
    }

    @Test
    public void testGetInscriptionsforTournament_DisabledTournament() {
        User user = User.builder()
                .id(1L)
                .username("Organizer")
                .email("user@example.com")
                .build();
        Long tournamentId = 1L;
        Athlete athlete = new Athlete();
        athlete.setId(1L);
        Organizer organizer = new Organizer();
        organizer.setId(1L);
        organizer.setUsername("Organizer");
        Sports_type sportsType = new Sports_type();
        sportsType.setName("Sport Type");

        Tournament tournament = new Tournament();
        tournament.setId(1L);
        tournament.setEnabled(false);
        tournament.setInscription(false);
        tournament.setCapacity(-1);
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

        when(userService.isAuthorized()).thenReturn(user);
        when(tournamentService.getTournament(tournamentId)).thenReturn(tournament);


        ResponseEntity response = tournamentController.getInscriptionsforTournament(tournamentId);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals("The tournament that you asked is disabled.", response.getBody());
        verify(userService, times(1)).isAuthorized();
        verify(tournamentService, times(1)).getTournament(tournamentId);
        verify(inscriptionService, never()).getInscriptionsforTournament(tournamentId);
    }


}
