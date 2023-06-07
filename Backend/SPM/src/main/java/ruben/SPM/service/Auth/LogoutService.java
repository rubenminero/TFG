package ruben.SPM.service.Auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;
import ruben.SPM.model.Entities.User;
import ruben.SPM.service.EntitiesServices.UserService;

@Service
@AllArgsConstructor
public class LogoutService implements LogoutHandler {

    private final UserService userService;
    private final AuthService authService;
    private final JWTService jwtService;


    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String username;
        final String token;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
        }else{
            token = authHeader.substring(7);
            username = jwtService.extractUsername(token);
            User user = this.userService.getUserByUsername(username);
            this.authService.revokeAllUserTokens(user);
            SecurityContextHolder.clearContext();
            System.out.println("Log out");
        }
    }
}