package ruben.SPM.controllers.EntitiesControllers;

import org.antlr.v4.runtime.atn.SemanticContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ruben.SPM.model.Entities.*;
import ruben.SPM.service.EntitiesServices.Sports_typeService;
import ruben.SPM.service.EntitiesServices.UserService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class Sports_typeControllerTest {
    @InjectMocks
    private Sports_typeController sportsTypeController;
    @Mock
    private Sports_typeService sportsTypeService;
    @Mock
    private UserService userService;

    @Test
    public void testCreateSportType() {
        Sports_type sportType = new Sports_type();
        sportType.setName("Football");

        when(sportsTypeService.validName("Football")).thenReturn(false);
        when(sportsTypeService.saveSport_type(sportType)).thenReturn(sportType);

        ResponseEntity response = sportsTypeController.createSport_Type(sportType);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(sportsTypeService, times(1)).validName("Football");
        verify(sportsTypeService, times(1)).saveSport_type(sportType);
    }

    @Test
    public void testCreateSportType_NameAlreadyTaken() {
        Sports_type sportType = new Sports_type();
        sportType.setName("Football");

        when(sportsTypeService.validName("Football")).thenReturn(true);

        ResponseEntity response = sportsTypeController.createSport_Type(sportType);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("The name is already taken.", response.getBody());
        verify(sportsTypeService, times(1)).validName("Football");
    }


    @Test
    public void testUpdateSportType() {
        Long id = 1L;
        Sports_type sportType = new Sports_type();
        sportType.setId(id);
        sportType.setName("Football");

        when(userService.isAuthorized()).thenReturn(new User());
        when(sportsTypeService.validName("Football")).thenReturn(false);
        when(sportsTypeService.getSport_type(id)).thenReturn(sportType);
        when(sportsTypeService.saveSport_type(sportType)).thenReturn(sportType);

        ResponseEntity response = sportsTypeController.updateSport_Type(id, sportType);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(userService, times(1)).isAuthorized();
        verify(sportsTypeService, times(1)).validName("Football");
        verify(sportsTypeService, times(1)).getSport_type(id);
        verify(sportsTypeService, times(1)).saveSport_type(sportType);
    }

    @Test
    public void testUpdateSportType_NameAlreadyTaken() {
        Long id = 1L;
        Sports_type sportType = new Sports_type();
        sportType.setId(id);
        sportType.setName("Football");

        when(userService.isAuthorized()).thenReturn(new User());
        when(sportsTypeService.validName("Football")).thenReturn(true);

        ResponseEntity response = sportsTypeController.updateSport_Type(id, sportType);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("The name is already taken.", response.getBody());
        verify(userService, times(1)).isAuthorized();
        verify(sportsTypeService, times(1)).validName("Football");

    }

    @Test
    public void testUpdateSportType_InvalidId() {
        Long id = 1L;
        Sports_type sportType = new Sports_type();
        sportType.setId(2L);
        sportType.setName("Football");

        when(userService.isAuthorized()).thenReturn(new User());

        ResponseEntity response = sportsTypeController.updateSport_Type(id, sportType);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals("Bad request ,the id given in the path doesnt match with the id on the sport type.", response.getBody());

    }

    @Test
    public void testUpdateSportType_SportTypeNotFound() {
        Long id = 1L;
        Sports_type sportType = new Sports_type();
        sportType.setId(id);
        sportType.setName("Football");

        when(userService.isAuthorized()).thenReturn(new User());
        when(sportsTypeService.getSport_type(id)).thenReturn(null);

        ResponseEntity response = sportsTypeController.updateSport_Type(id, sportType);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Bad request, there is no sport type for that id.", response.getBody());
        verify(userService, times(1)).isAuthorized();
        verify(sportsTypeService, times(1)).getSport_type(id);

    }


    @Test
    public void testGetSportTypeById() {
        Long id = 1L;
        Sports_type sportType = new Sports_type();
        sportType.setId(id);
        sportType.setEnabled(true);

        when(sportsTypeService.getSport_type(id)).thenReturn(sportType);

        ResponseEntity response = sportsTypeController.getSport_typeById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(sportsTypeService, times(1)).getSport_type(id);
    }

    @Test
    public void testGetSportTypeById_Disabled() {
        Long id = 1L;
        Sports_type sportType = new Sports_type();
        sportType.setId(id);
        sportType.setEnabled(false);

        when(sportsTypeService.getSport_type(id)).thenReturn(sportType);

        ResponseEntity response = sportsTypeController.getSport_typeById(id);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals("The sport type that you asked is disabled.", response.getBody());
        verify(sportsTypeService, times(1)).getSport_type(id);
    }

    @Test
    public void testGetSportTypeById_NonExistingSportType() {
        Long id = 1L;

        when(sportsTypeService.getSport_type(id)).thenReturn(null);

        ResponseEntity response = sportsTypeController.getSport_typeById(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("The sport type that you asked doesn't exist.", response.getBody());
        verify(sportsTypeService, times(1)).getSport_type(id);
    }

    @Test
    public void testGetAllSportsTypes() {
        List<Sports_type> sportsTypes = new ArrayList<>();
        sportsTypes.add(new Sports_type());
        sportsTypes.add(new Sports_type());

        when(sportsTypeService.getEnabledSport_types()).thenReturn(sportsTypes);

        ResponseEntity response = sportsTypeController.getAllSports_types();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(sportsTypeService, times(1)).getEnabledSport_types();
    }

    @Test
    public void testGetAllSportsTypes_NoEnabledSportsTypesExist() {
        List<Sports_type> sportsTypes = new ArrayList<>();

        when(sportsTypeService.getEnabledSport_types()).thenReturn(sportsTypes);

        ResponseEntity response = sportsTypeController.getAllSports_types();

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("There is no sports types.", response.getBody());
        verify(sportsTypeService, times(1)).getEnabledSport_types();
    }
}
