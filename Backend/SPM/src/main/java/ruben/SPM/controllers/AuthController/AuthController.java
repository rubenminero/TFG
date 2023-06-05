package ruben.SPM.controllers.AuthController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.bind.annotation.*;
import ruben.SPM.model.DTO.Auth.AuthRegisterDTOs.AuthRegisterAdminDTO;
import ruben.SPM.model.DTO.Auth.AuthRegisterDTOs.AuthRegisterAthleteDTO;
import ruben.SPM.model.DTO.Auth.AuthRegisterDTOs.AuthRegisterOrganizerDTO;
import ruben.SPM.model.DTO.Auth.AuthRequestDTO;
import ruben.SPM.model.DTO.Auth.PasswordChangeDTO;
import ruben.SPM.model.Entities.Admin;
import ruben.SPM.model.Entities.Athlete;
import ruben.SPM.model.Entities.Organizer;
import ruben.SPM.model.Entities.User;
import ruben.SPM.model.Whitelist.Role;
import ruben.SPM.service.Auth.AuthService;
import ruben.SPM.service.EntitiesServices.AdminService;
import ruben.SPM.service.EntitiesServices.AthleteService;
import ruben.SPM.service.EntitiesServices.OrganizerService;
import ruben.SPM.service.EntitiesServices.UserService;

import java.io.IOException;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name="Authentication")
@Slf4j
@CrossOrigin(origins = {"http://localhost:4200"})
public class AuthController {
    private final AuthService service;
    private final UserService userService;
    private final AthleteService athleteService;
    private final OrganizerService organizerService;
    private final AdminService adminService;

    @PostMapping("/register-organizer")
    @Operation(summary = "Register a organizer in the database. Returning the token for that user.")
    public ResponseEntity<?> registerOrganizer(@Parameter(name = "The DTO that contains the organizer data") @RequestBody AuthRegisterOrganizerDTO request) {
        Boolean username_check = userService.validUsername(request.getUsername());
        if (username_check){
            String msg = "This username is already taken.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(msg);
        }
        return ResponseEntity.ok(service.registerOrganizer(request));
    }

    @PostMapping("/register-athlete")
    @Operation(summary = "Register a athlete in the database. Returning the token for that user.")
    public ResponseEntity<?> registerAthlete(@Parameter(name = "The DTO that contains the athlete data") @RequestBody AuthRegisterAthleteDTO request) {
        Boolean username_check = userService.validUsername(request.getUsername());
        if (username_check){
            String msg = "This username is already taken.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(msg);
        }
        return ResponseEntity.ok(service.registerAthlete(request));
    }
    @PostMapping("/register-admin")
    @Operation(summary = "Register a admin in the database. Returning the token for that user.")
    public ResponseEntity<?> registerAdmin(@Parameter(name = "The DTO that contains the admin data") @RequestBody AuthRegisterAdminDTO request) {
        Boolean username_check = userService.validUsername(request.getUsername());
        if (username_check){
            String msg = "This username is already taken.";
            log.warn(msg);
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(msg);
        }
        return ResponseEntity.ok(service.registerAdmin(request));
    }

    @PostMapping("/authenticate")
    @Operation(summary = "Authenticate the user for authenticated operations.Returning the token for that user ")
    public ResponseEntity<?> authenticate(@Parameter(name = "The DTO that contains the login data") @RequestBody AuthRequestDTO request) {
        return service.authenticate(request);
    }

    @PostMapping("/refresh-token")
    @Operation(summary = "Refresh the token from a user, generating a new token pair.")
    @PreAuthorize("hasAuthority('athlete:read')")
    public void refreshToken(@Parameter(name = "Servlet request")HttpServletRequest request, @Parameter(name = "Servlet response")HttpServletResponse response
    ) throws IOException {
        service.refreshToken(request, response);
    }


    @PutMapping("/logout")
    @Operation(summary = "Log out the user from the app.")
    @PreAuthorize("hasAuthority('athlete:read')")
    public void log_out(@Parameter(name = "Servlet request")HttpServletRequest request, @Parameter(name = "Servlet response")HttpServletResponse response
    ) throws IOException {
        service.log_out();
    }

}

