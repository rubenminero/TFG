package ruben.TFG.config.Security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ruben.TFG.repository.TokenRepository.TokenRepository;
import ruben.TFG.service.Auth.JWTService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class JWTAuthFIlter extends OncePerRequestFilter {

    private final JWTService jwtService;
    private final UserDetailsService userDetailsService;
    private final TokenRepository tokenRepository;
    private final ObjectMapper objectMapper;

    private final ArrayList<String> auth_whitelist =new ArrayList<String>(List.of(
                "/v2/api-docs",
                "/v3/api-docs",
                "/v3/api-docs/**",
                "/swagger-resources",
                "/swagger-resources/**",
                "/configuration/ui",
                "/configuration/security",
                "/swagger-ui/**",
                "/webjars/**",
                "/swagger-ui.html",
                "/swagger-ui/index.html",
                "/api/auth/register-organizer",
                "/api/auth/register-admin",
                "/api/auth/register-athlete",
                "/api/auth/authenticate",
                "/api/auth/refresh-token",
                "/error/**"
    ));
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws IOException {
        try {

            if (auth_whitelist.contains(request.getServletPath()) || request.getServletPath().startsWith("v3/api-docs/")|| request.getServletPath().startsWith("/swagger-ui/")){
                filterChain.doFilter(request, response);
                return;
            }
            final String authHeader = request.getHeader("Authorization");

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                String errorPayload = objectMapper.writeValueAsString(Map.of(
                        "type", "about:blank",
                        "title", "Bad Authorization",
                        "status", HttpServletResponse.SC_UNAUTHORIZED,
                        "detail", "The Authorization header should contain a Bearer token value."
                ));
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/problem+json");
                response.getWriter().write(errorPayload);
                return;
            }

            final String jwt = authHeader.substring(7);
            final String username = jwtService.extractUsername(jwt);
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                var isTokenValid = tokenRepository.findByToken(jwt)
                        .map(t -> !t.isExpired() && !t.isRevoked())
                        .orElse(false);

                if (jwtService.isTokenValid(jwt, userDetails) && isTokenValid) {
                    var authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
                    authToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }

            filterChain.doFilter(request, response);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            String errorPayload = objectMapper.writeValueAsString(Map.of(
                    "type", "about:blank",
                    "title", "SF1",
                    "status", HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "detail", "An error occurred while processing the request."
            ));
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setContentType("application/problem+json");
            response.getWriter().write(errorPayload);
        }
    }
}
