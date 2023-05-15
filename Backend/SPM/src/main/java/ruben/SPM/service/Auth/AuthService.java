package ruben.SPM.service.Auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import ruben.SPM.model.DTO.Auth.AuthRegisterDTOs.AuthRegisterAdminDTO;
import ruben.SPM.model.DTO.Auth.AuthRegisterDTOs.AuthRegisterAthleteDTO;
import ruben.SPM.model.Entities.Admin;
import ruben.SPM.model.Entities.Athlete;
import ruben.SPM.model.Entities.Organizer;
import ruben.SPM.model.Entities.User;
import ruben.SPM.model.JWT_Token.Token;
import ruben.SPM.model.DTO.Auth.AuthRequestDTO;
import ruben.SPM.model.DTO.Auth.AuthResponseDTO;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ruben.SPM.model.DTO.Auth.AuthRegisterDTOs.AuthRegisterOrganizerDTO;
import ruben.SPM.model.JWT_Token.TokenType;
import ruben.SPM.repository.EntitiesRepositories.UserRepository;
import ruben.SPM.repository.TokenRepository.TokenRepository;
import ruben.SPM.service.EntitiesServices.AdminService;
import ruben.SPM.service.EntitiesServices.AthleteService;
import ruben.SPM.service.EntitiesServices.OrganizerService;
import ruben.SPM.service.EntitiesServices.UserService;

import java.io.IOException;

@Service
@RequiredArgsConstructor

public class AuthService {
    private final UserService userService;
    private final OrganizerService organizerService;
    private final AdminService adminService;
    private final AthleteService athleteService;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponseDTO registerOrganizer(AuthRegisterOrganizerDTO request) {
        var user = User.builder()
                .username(request.getUsername())
                .first_name(request.getFirst_name())
                .last_name(request.getLast_name())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .nif(request.getNif())
                .role(request.getRole())
                .build();

        Organizer organizer = new Organizer(user,request.getCompany_name(),request.getAddress());


        var saveUser = organizerService.saveOrganizer(organizer);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(saveUser, jwtToken);

        return AuthResponseDTO.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }
    public AuthResponseDTO registerAthlete(AuthRegisterAthleteDTO request) {
        var user = User.builder()
                .username(request.getUsername())
                .first_name(request.getFirst_name())
                .last_name(request.getLast_name())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .nif(request.getNif())
                .role(request.getRole())
                .build();

        Athlete athlete = new Athlete(user,request.getPhone_number());


        var saveUser = athleteService.saveAthlete(athlete);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(saveUser, jwtToken);

        return AuthResponseDTO.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }
    public AuthResponseDTO registerAdmin(AuthRegisterAdminDTO request) {
        var user = User.builder()
                .username(request.getUsername())
                .first_name(request.getFirst_name())
                .last_name(request.getLast_name())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .nif(request.getNif())
                .role(request.getRole())
                .build();

        Admin admin = new Admin(user,request.getValid_from());


        var saveUser = adminService.saveAdmin(admin);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(saveUser, jwtToken);

        return AuthResponseDTO.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthResponseDTO authenticate(User user) {
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthResponseDTO.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String username;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        username = jwtService.extractUsername(refreshToken);
        if (username != null) {
            var user = this.userService.getUserByUsername(username);
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                var authResponse = AuthResponseDTO.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }


}