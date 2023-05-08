package ruben.TFG.model.Whitelist;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {

    ATHLETE_READ("athlete:read"),
    ATHLETE_UPDATE("athlete:update"),
    ATHLETE_CREATE("athlete:create"),
    ATHLETE_DELETE("athlete:delete"),
    ORGANIZER_READ("organizer:read"),
    ORGANIZER_UPDATE("organizer:update"),
    ORGANIZER_CREATE("organizer:create"),
    ORGANIZER_DELETE("organizer:delete"),
    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_CREATE("admin:create"),
    ADMIN_DELETE("admin:delete")
    ;

    @Getter
    private final String permission;
}
