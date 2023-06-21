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
import ruben.SPM.model.Entities.Athlete;
import ruben.SPM.model.Entities.Organizer;
import ruben.SPM.service.EntitiesServices.AdminService;
import ruben.SPM.service.EntitiesServices.DeleteService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class AthletesAdminControllerTest {

    @InjectMocks
    private AdminController adminController;
    @Mock
    private AdminService adminService;
    @Mock
    private DeleteService deleteService;

    @Test
    public void testGetAllAthletes_Admin() {
        List<Athlete> athletes = new ArrayList<>();
        athletes.add(new Athlete());
        athletes.add(new Athlete());

        when(adminService.getAllAthletes()).thenReturn(athletes);

        ResponseEntity response = adminController.getAllAthletes_Admin();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(athletes.size(), ((List<OrganizerDTO>) response.getBody()).size());
        verify(adminService, times(1)).getAllAthletes();
    }

    @Test
    public void testGetAllAthletes_Admin_NotFound() {
        List<Athlete> athletes = new ArrayList<>();

        when(adminService.getAllAthletes()).thenReturn(athletes);

        ResponseEntity response = adminController.getAllAthletes_Admin();

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("There is no athletes.", response.getBody());
        verify(adminService, times(1)).getAllAthletes();
    }

    @Test
    public void testUpdateAthlete() {
        Long athleteid = 1L;
        Athlete athlete = new Athlete();
        athlete.setId(athleteid);

        when(adminService.getAthlete(athleteid)).thenReturn(athlete);

        ResponseEntity response = adminController.updateAthlete(athleteid);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(adminService, times(1)).changeStateAthlete(athleteid);
        verify(adminService, times(1)).getAthlete(athleteid);
    }

    @Test
    public void testUpdateAthlete_NotFound() {
        Long athleteid = 1L;

        when(adminService.getAthlete(athleteid)).thenReturn(null);

        ResponseEntity response = adminController.updateAthlete(athleteid);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("There is no athlete with this id.", response.getBody());
        verify(adminService, times(0)).changeStateAthlete(athleteid);
        verify(adminService, times(1)).getAthlete(athleteid);
    }

    @Test
    public void testDeleteAthlete() {
        Long athleteid = 1L;
        Athlete athlete = new Athlete();
        athlete.setId(athleteid);

        when(adminService.getAthlete(athleteid)).thenReturn(athlete);

        ResponseEntity response = adminController.deleteAthlete(athleteid);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(adminService, times(1)).getAthlete(athleteid);
        verify(deleteService, times(1)).deleteAthlete(athlete);
    }

    @Test
    public void testDeleteAthlete_NotFound() {
        Long athleteid = 1L;

        when(adminService.getAthlete(athleteid)).thenReturn(null);

        ResponseEntity response = adminController.deleteAthlete(athleteid);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("There is no athlete with this id.", response.getBody());
        verify(adminService, times(1)).getAthlete(athleteid);
        verify(deleteService, times(0)).deleteAthlete(any(Athlete.class));
    }

}
