package ruben.TFG.config.Security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import static org.springframework.http.HttpMethod.*;
import static ruben.TFG.model.Whitelist.Permission.*;
import static ruben.TFG.model.Whitelist.Role.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

    private final JWTAuthFIlter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers(
                        "/api/auth/**",
                        "/v2/api-docs",
                        "/v3/api-docs",
                        "/v3/api-docs/**",
                        "/swagger-resources",
                        "/swagger-resources/**",
                        "/configuration/ui",
                        "/configuration/security",
                        "/swagger-ui/**",
                        "/webjars/**",
                        "/swagger-ui.html"
                )
                .permitAll()


                .requestMatchers("/api/admins/**").hasAnyRole(ADMIN.name())


                .requestMatchers(GET, "/api/admins/**").hasAnyAuthority(ADMIN_READ.name())
                .requestMatchers(POST, "/api/admins/**").hasAnyAuthority(ADMIN_CREATE.name())
                .requestMatchers(PUT, "/api/admins/**").hasAnyAuthority(ADMIN_UPDATE.name())
                .requestMatchers(DELETE, "/api/admins/**").hasAnyAuthority(ADMIN_DELETE.name())

                .requestMatchers("/api/admins/**").hasAnyRole(ADMIN.name(),ORGANIZER.name())

                .requestMatchers(GET, "/api/organizers/**").hasAnyAuthority(ADMIN_READ.name(),ORGANIZER_READ.name())
                .requestMatchers(POST, "/api/organizers/**").hasAnyAuthority(ADMIN_CREATE.name(),ORGANIZER_CREATE.name())
                .requestMatchers(PUT, "/api/organizers/**").hasAnyAuthority(ADMIN_UPDATE.name(),ORGANIZER_UPDATE.name())
                .requestMatchers(DELETE, "/api/organizers/**").hasAnyAuthority(ADMIN_DELETE.name(),ORGANIZER_DELETE.name())

                .requestMatchers("/api/admins/**").hasAnyRole(ADMIN.name(),ORGANIZER.name(),ATHLETE.name())

                .requestMatchers(GET, "/api/athletes/**").hasAnyAuthority(ADMIN_READ.name(),ORGANIZER_READ.name(),ATHLETE_READ.name())
                .requestMatchers(POST, "/api/athletes/**").hasAnyAuthority(ADMIN_CREATE.name(),ORGANIZER_CREATE.name(),ATHLETE_CREATE.name())
                .requestMatchers(PUT, "/api/athletes/**").hasAnyAuthority(ADMIN_UPDATE.name(),ORGANIZER_UPDATE.name(),ATHLETE_UPDATE.name())
                .requestMatchers(DELETE, "/api/athletes/**").hasAnyAuthority(ADMIN_DELETE.name(),ORGANIZER_DELETE.name(),ATHLETE_DELETE.name())



                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout()
                .logoutUrl("/api/auth/logout")
                .addLogoutHandler(logoutHandler)
                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
        ;

        return http.build();
    }
}