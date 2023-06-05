package ruben.SPM.service.Auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;
import ruben.SPM.model.Entities.User;
import ruben.SPM.repository.TokenRepository.TokenRepository;
import ruben.SPM.service.EntitiesServices.UserService;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {
    private final UserService userService;
    private final AuthService authService;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

        final String authHeader = request.getHeader("Authorization");
        final String jwt;

        if (authHeader == null || !authHeader.startsWith("Bearer ")){
            return;
        }

        User user = this.userService.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        jwt = authHeader.substring(7);
        authService.revokeAllUserTokens(user);
        SecurityContextHolder.clearContext();
    }
}