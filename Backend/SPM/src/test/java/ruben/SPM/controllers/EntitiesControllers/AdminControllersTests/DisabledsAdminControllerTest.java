package ruben.SPM.controllers.EntitiesControllers.AdminControllersTests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ruben.SPM.controllers.EntitiesControllers.AdminController;
import ruben.SPM.model.DTO.Front.DeleteDisabledSummaryFrontDTO;
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
public class DisabledsAdminControllerTest {

    @InjectMocks
    private AdminController adminController;
    @Mock
    private AdminService adminService;
    @Mock
    private DeleteService deleteService;

    @Test
    public void testCountDisableds_Success() {
        DeleteDisabledSummaryFrontDTO disabledsSummary = new DeleteDisabledSummaryFrontDTO();
        disabledsSummary.setEvents_disabled(5);
        disabledsSummary.setSports_types_disabled(3);
        disabledsSummary.setInscriptions_disabled(2);

        when(deleteService.countDisableds()).thenReturn(disabledsSummary);

        ResponseEntity response = adminController.countDisableds();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(disabledsSummary, response.getBody());
        verify(deleteService, times(1)).countDisableds();
    }

    @Test
    public void testDeleteDisableds_Success() {
        DeleteDisabledSummaryFrontDTO disabledsSummary = new DeleteDisabledSummaryFrontDTO();
        ResponseEntity response = adminController.deleteDisableds();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(disabledsSummary, response.getBody());
        verify(deleteService, times(1)).deleteDisableds();
    }
}
