package ruben.TFG.model.Whitelist;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static ruben.TFG.model.Whitelist.Permission.*;

@RequiredArgsConstructor
public enum Role {

    ATHLETE(
            Set.of(
                    ATHLETE_READ,
                    ATHLETE_UPDATE,
                    ATHLETE_DELETE,
                    ATHLETE_CREATE
            )
    ),
    ORGANIZER(
            Set.of(
                    ORGANIZER_READ,
                    ORGANIZER_UPDATE,
                    ORGANIZER_DELETE,
                    ORGANIZER_CREATE,
                    ATHLETE_READ,
                    ATHLETE_UPDATE,
                    ATHLETE_DELETE,
                    ATHLETE_CREATE
            )
    ),
    ADMIN(
            Set.of(
                    ADMIN_READ,
                    ADMIN_UPDATE,
                    ADMIN_DELETE,
                    ADMIN_CREATE,
                    ORGANIZER_READ,
                    ORGANIZER_UPDATE,
                    ORGANIZER_DELETE,
                    ORGANIZER_CREATE,
                    ATHLETE_READ,
                    ATHLETE_UPDATE,
                    ATHLETE_DELETE,
                    ATHLETE_CREATE
            )
    )


    ;

    @Getter
    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}
