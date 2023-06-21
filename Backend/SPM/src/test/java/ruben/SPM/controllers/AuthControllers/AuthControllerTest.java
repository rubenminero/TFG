package ruben.SPM.controllers.AuthControllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import ruben.SPM.controllers.AuthController.AuthController;
import ruben.SPM.model.DTO.Auth.AuthRegisterDTOs.AuthRegisterAthleteDTO;
import ruben.SPM.model.DTO.Auth.AuthRegisterDTOs.AuthRegisterOrganizerDTO;
import ruben.SPM.model.DTO.Auth.AuthRequestDTO;
import ruben.SPM.model.DTO.Auth.AuthResponseDTO;
import ruben.SPM.model.Entities.User;
import ruben.SPM.model.Whitelist.Role;
import ruben.SPM.service.Auth.AuthService;
import ruben.SPM.service.Auth.JWTService;
import ruben.SPM.service.EntitiesServices.OrganizerService;
import ruben.SPM.service.EntitiesServices.UserService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class AuthControllerTest {

    @InjectMocks
    private AuthController authController;

    @Mock
    private UserService userService;
    @Mock
    private JWTService jwtService;

    @Mock
    private AuthService authService;
    @Mock
    private OrganizerService organizerService;

    @Test
    public void testRegisterOrganizer() {
        // Arrange
        AuthRegisterOrganizerDTO request = new AuthRegisterOrganizerDTO();
        request.setUsername("testuser");
        request.setPassword("testpassword");
        request.setEmail("test@example.com");

        AuthResponseDTO authResponseDTO = AuthResponseDTO.builder()
                        .accessToken("access_token")
                        .refreshToken("refresh_token")
                        .build();

        when(userService.validUsername(request.getUsername())).thenReturn(false);
        when(authService.registerOrganizer(request)).thenReturn(authResponseDTO);

        // Act
        ResponseEntity<?> response = authController.registerOrganizer(request);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(userService, times(1)).validUsername(request.getUsername());
        verify(authService, times(1)).registerOrganizer(request);
    }

    @Test
    public void testRegisterOrganizer_UsernameTaken() {
        // Arrange
        AuthRegisterOrganizerDTO request = new AuthRegisterOrganizerDTO();
        request.setUsername("testuser");
        request.setPassword("testpassword");
        request.setEmail("test@example.com");

        when(userService.validUsername(request.getUsername())).thenReturn(true);

        // Act
        ResponseEntity<?> response = authController.registerOrganizer(request);

        // Assert
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals("This username is already taken.", response.getBody());
        verify(userService, times(1)).validUsername(request.getUsername());
        verifyNoInteractions(authService);
    }

    @Test
    public void testRegisterOrganizer_EmailTaken() {
        // Arrange
        AuthRegisterOrganizerDTO request = new AuthRegisterOrganizerDTO();
        request.setUsername("testuser");
        request.setPassword("testpassword");
        request.setEmail("test@example.com");

        when(userService.validUsername(request.getUsername())).thenReturn(false);
        when(userService.validEmail(request.getEmail())).thenReturn(true);
        // Act
        ResponseEntity<?> response = authController.registerOrganizer(request);

        // Assert
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals("This email is already taken.", response.getBody());
        verify(userService, times(1)).validUsername(request.getUsername());
        verifyNoInteractions(authService);
    }
    @Test
    public void testRegisterOrganizer_CompanyTaken() {
        // Arrange
        AuthRegisterOrganizerDTO request = new AuthRegisterOrganizerDTO();
        request.setUsername("testuser");
        request.setPassword("testpassword");
        request.setEmail("test@example.com");

        when(userService.validUsername(request.getUsername())).thenReturn(false);
        when(userService.validEmail(request.getEmail())).thenReturn(false);
        when(organizerService.validCompany_name(request.getCompany())).thenReturn(true);
        // Act
        ResponseEntity<?> response = authController.registerOrganizer(request);

        // Assert
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals("This company is already taken.", response.getBody());
        verify(userService, times(1)).validUsername(request.getUsername());
        verifyNoInteractions(authService);
    }


    @Test
    public void testRegisterAthlete() {
        // Arrange
        AuthRegisterAthleteDTO request = new AuthRegisterAthleteDTO();
        request.setUsername("testuser");
        request.setPassword("testpassword");
        request.setEmail("test@example.com");

        AuthResponseDTO authResponseDTO = AuthResponseDTO.builder()
                .accessToken("access_token")
                .refreshToken("refresh_token")
                .build();

        when(userService.validUsername(request.getUsername())).thenReturn(false);
        when(authService.registerAthlete(request)).thenReturn(authResponseDTO);

        // Act
        ResponseEntity<?> response = authController.registerAthlete(request);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(userService, times(1)).validUsername(request.getUsername());
        verify(authService, times(1)).registerAthlete(request);
    }

    @Test
    public void testRegisterAthlete_UsernameTaken() {
        // Arrange
        AuthRegisterAthleteDTO request = new AuthRegisterAthleteDTO();
        request.setUsername("testuser");
        request.setPassword("testpassword");
        request.setEmail("test@example.com");

        when(userService.validUsername(request.getUsername())).thenReturn(true);

        // Act
        ResponseEntity<?> response = authController.registerAthlete(request);

        // Assert
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals("This username is already taken.", response.getBody());
        verify(userService, times(1)).validUsername(request.getUsername());
        verifyNoInteractions(authService);
    }

    @Test
    public void testRegisterAthlete_EmailTaken() {
        // Arrange
        AuthRegisterAthleteDTO request = new AuthRegisterAthleteDTO();
        request.setUsername("testuser");
        request.setPassword("testpassword");
        request.setEmail("test@example.com");

        when(userService.validUsername(request.getUsername())).thenReturn(false);
        when(userService.validEmail(request.getEmail())).thenReturn(true);

        // Act
        ResponseEntity<?> response = authController.registerAthlete(request);

        // Assert
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals("This email is already taken.", response.getBody());
        verify(userService, times(1)).validUsername(request.getUsername());
        verifyNoInteractions(authService);
    }


}