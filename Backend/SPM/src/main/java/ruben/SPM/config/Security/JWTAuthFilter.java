package ruben.SPM.config.Security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
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
import ruben.SPM.repository.TokenRepository.TokenRepository;
import ruben.SPM.service.Auth.JWTService;

import java.io.IOException;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class JWTAuthFilter extends OncePerRequestFilter {

    private final JWTService jwtService;
    private final UserDetailsService userDetailsService;
    private final TokenRepository tokenRepository;
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws IOException {
        try {


            if (!request.getServletPath().startsWith("/api") || request.getServletPath().startsWith("/api/auth")){
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
        } catch (ExpiredJwtException jwt) {
            log.error(jwt.getMessage(), jwt);
            String errorPayload = objectMapper.writeValueAsString(Map.of(
                    "type", "JWT_Expired",
                    "title", "JWT",
                    "status", HttpServletResponse.SC_UNAUTHORIZED,
                    "detail", "This token is already expired."
            ));
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/problem+json");
            response.getWriter().write(errorPayload);
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
