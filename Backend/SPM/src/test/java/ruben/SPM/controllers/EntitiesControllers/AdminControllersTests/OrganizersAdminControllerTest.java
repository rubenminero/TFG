package ruben.SPM.controllers.EntitiesControllers.AdminControllersTests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import ruben.SPM.controllers.EntitiesControllers.AdminController;
import ruben.SPM.controllers.EntitiesControllers.AthleteController;
import ruben.SPM.model.DTO.Entities.AthleteDTO;
import ruben.SPM.model.DTO.Entities.OrganizerDTO;
import ruben.SPM.model.DTO.Front.InscriptionFrontDTO;
import ruben.SPM.model.DTO.Front.WatchlistFrontDTO;
import ruben.SPM.model.Entities.*;
import ruben.SPM.service.EntitiesServices.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class OrganizersAdminControllerTest {

    @InjectMocks
    private AdminController adminController;
    @Mock
    private AdminService adminService;
    @Mock
    private DeleteService deleteService;

    @Test
    public void testGetAllOrganizers_Admin() {
        List<Organizer> organizers = new ArrayList<>();
        organizers.add(new Organizer());
        organizers.add(new Organizer());

        when(adminService.getAllOrganizers()).thenReturn(organizers);

        ResponseEntity response = adminController.getAllOrganizers_Admin();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(organizers.size(), ((List<OrganizerDTO>) response.getBody()).size());
        verify(adminService, times(1)).getAllOrganizers();
    }

    @Test
    public void testGetAllOrganizers_Admin_NotFound() {
        List<Organizer> organizers = new ArrayList<>();

        when(adminService.getAllOrganizers()).thenReturn(organizers);

        ResponseEntity response = adminController.getAllOrganizers_Admin();

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("There is no organizers.", response.getBody());
        verify(adminService, times(1)).getAllOrganizers();
    }

    @Test
    public void testUpdateOrganizer() {
        Long organizerId = 1L;
        Organizer organizer = new Organizer();
        organizer.setId(organizerId);

        when(adminService.getOrganizer(organizerId)).thenReturn(organizer);

        ResponseEntity response = adminController.updateOrganizer(organizerId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(adminService, times(1)).changeStateOrganizer(organizerId);
        verify(adminService, times(1)).getOrganizer(organizerId);
    }

    @Test
    public void testUpdateOrganizer_NotFound() {
        Long organizerId = 1L;

        when(adminService.getOrganizer(organizerId)).thenReturn(null);

        ResponseEntity response = adminController.updateOrganizer(organizerId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("There is no organizer with this id.", response.getBody());
        verify(adminService, times(0)).changeStateOrganizer(organizerId);
        verify(adminService, times(1)).getOrganizer(organizerId);
    }

    @Test
    public void testDeleteOrganizer() {
        Long organizerId = 1L;
        Organizer organizer = new Organizer();
        organizer.setId(organizerId);

        when(adminService.getOrganizer(organizerId)).thenReturn(organizer);

        ResponseEntity response = adminController.deleteOrganizer(organizerId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(adminService, times(1)).getOrganizer(organizerId);
        verify(deleteService, times(1)).deleteOrganizer(organizer);
    }

    @Test
    public void testDeleteOrganizer_NotFound() {
        Long organizerId = 1L;

        when(adminService.getOrganizer(organizerId)).thenReturn(null);

        ResponseEntity response = adminController.deleteOrganizer(organizerId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("There is no organizer with this id.", response.getBody());
        verify(adminService, times(1)).getOrganizer(organizerId);
        verify(deleteService, times(0)).deleteOrganizer(any(Organizer.class));
    }

}
