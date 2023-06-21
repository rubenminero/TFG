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
import ruben.SPM.model.DTO.Front.EventFrontDTO;
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
@SpringBootTest
public class Sports_typesAdminControllerTest {

    @InjectMocks
    private AdminController adminController;
    @Mock
    private AdminService adminService;
    @Mock
    private DeleteService deleteService;
    @Test
    public void testGetAllSports_types() {
        List<Sports_type> sportsTypes = new ArrayList<>();
        sportsTypes.add(new Sports_type());
        sportsTypes.add(new Sports_type());

        when(adminService.getAllSports_types()).thenReturn(sportsTypes);

        ResponseEntity response = adminController.getAllSports_types_Admin();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(sportsTypes.size(), ((List<Sports_typeDTO>) response.getBody()).size());
        verify(adminService, times(1)).getAllSports_types();
    }

    @Test
    public void testGetAllSports_types_NotFound() {
        when(adminService.getAllSports_types()).thenReturn(Collections.emptyList());

        ResponseEntity response = adminController.getAllSports_types_Admin();

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("There are no sports types.", response.getBody());
        verify(adminService, times(1)).getAllSports_types();
    }

    @Test
    public void testUpdateSport_type() {
        Long sportTypeId = 1L;
        Sports_type sportsType = new Sports_type();
        sportsType.setId(sportTypeId);

        when(adminService.getSport_type(sportTypeId)).thenReturn(sportsType);

        ResponseEntity response = adminController.updateSport_type(sportTypeId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(adminService, times(1)).getSport_type(sportTypeId);
        verify(adminService, times(1)).changeStateSport_type(sportTypeId);
    }

    @Test
    public void testUpdateSport_type_NotFound() {
        Long sportTypeId = 1L;

        when(adminService.getSport_type(sportTypeId)).thenReturn(null);

        ResponseEntity response = adminController.updateSport_type(sportTypeId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("There is no sport type with this id.", response.getBody());
        verify(adminService, times(1)).getSport_type(sportTypeId);
        verify(adminService, times(0)).changeStateSport_type(anyLong());
    }

    @Test
    public void testDeleteSport_type() {
        Long sportTypeId = 1L;
        Sports_type sportsType = new Sports_type();
        sportsType.setId(sportTypeId);

        when(adminService.getSport_type(sportTypeId)).thenReturn(sportsType);

        ResponseEntity response = adminController.deleteSport_type(sportTypeId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(adminService, times(1)).getSport_type(sportTypeId);
        verify(deleteService, times(1)).deleteSportsType(sportTypeId);
    }

    @Test
    public void testDeleteSport_type_NotFound() {
        Long sportTypeId = 1L;

        when(adminService.getSport_type(sportTypeId)).thenReturn(null);

        ResponseEntity response = adminController.deleteSport_type(sportTypeId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("There is no sport type with this id.", response.getBody());
        verify(adminService, times(1)).getSport_type(sportTypeId);
        verify(deleteService, times(0)).deleteSportsType(anyLong());
    }
}
