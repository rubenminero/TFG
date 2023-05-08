package ruben.TFG.controllers.AuthController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
public class AuthController {

    private final AuthService service;

    @PostMapping("/register-organizer")

    public ResponseEntity<AuthResponseDTO> registerOrganizer(
            @RequestBody AuthRegisterOrganizerDTO request
    ) {
        return ResponseEntity.ok(service.registerOrganizer(request));
    }

    @PostMapping("/register-athlete")
    public ResponseEntity<AuthResponseDTO> registerOrganizer(
            @RequestBody AuthRegisterAthleteDTO request
    ) {
        return ResponseEntity.ok(service.registerAthlete(request));
    }
    @PostMapping("/register-admin")
    public ResponseEntity<AuthResponseDTO> registerOrganizer(
            @RequestBody AuthRegisterAdminDTO request
    ) {
        return ResponseEntity.ok(service.registerAdmin(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponseDTO> authenticate(
            @RequestBody AuthRequestDTO request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        service.refreshToken(request, response);
    }


}

