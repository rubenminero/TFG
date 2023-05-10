package ruben.TFG.controllers.AuthController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ruben.TFG.model.DTO.Auth.AuthRegisterDTOs.AuthRegisterAdminDTO;
import ruben.TFG.model.DTO.Auth.AuthRegisterDTOs.AuthRegisterAthleteDTO;
import ruben.TFG.model.DTO.Auth.AuthRegisterDTOs.AuthRegisterOrganizerDTO;
import ruben.TFG.model.DTO.Auth.AuthRequestDTO;
import ruben.TFG.model.DTO.Auth.AuthResponseDTO;
import ruben.TFG.service.Auth.AuthService;

import java.io.IOException;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name="Authentication")
public class AuthController {
    private final AuthService service;

    @PostMapping("/register-organizer")
    @Operation(summary = "Register a organizer in the database. Returning the token for that user.")
    public ResponseEntity<AuthResponseDTO> registerOrganizer(
            @RequestBody AuthRegisterOrganizerDTO request
    ) {
        return ResponseEntity.ok(service.registerOrganizer(request));
    }

    @PostMapping("/register-athlete")
    @Operation(summary = "Register a athlete in the database. Returning the token for that user.")
    public ResponseEntity<AuthResponseDTO> registerAthlete(
            @Parameter(name = "The DTO that contains the athlete data") @RequestBody AuthRegisterAthleteDTO request
    ) {
        return ResponseEntity.ok(service.registerAthlete(request));
    }
    @PostMapping("/register-admin")
    @Operation(summary = "Register a admin in the database. Returning the token for that user.")
    public ResponseEntity<AuthResponseDTO> registerAdmin(
            @Parameter(name = "The DTO that contains the admin data") @RequestBody AuthRegisterAdminDTO request
    ) {
        return ResponseEntity.ok(service.registerAdmin(request));
    }

    @PostMapping("/authenticate")
    @Operation(summary = "Authenticate the user for authenticated operations.Returning the token for that user ")
    public ResponseEntity<AuthResponseDTO> authenticate(
            @Parameter(name = "The DTO that contains the login data") @RequestBody AuthRequestDTO request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @PostMapping("/refresh-token")
    @Operation(summary = "Refresh the token from a user, generating a new token pair.")
    public void refreshToken(
            @Parameter(name = "Servlet request")HttpServletRequest request,
            @Parameter(name = "Servlet response")HttpServletResponse response
    ) throws IOException {
        service.refreshToken(request, response);
    }


}

