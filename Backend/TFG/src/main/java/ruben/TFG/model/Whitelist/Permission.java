package ruben.TFG.model.Whitelist;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {

    USER_READ("user:read"),
    USER_UPDATE("user:update"),
    USER_CREATE("user:create"),
    USER_DELETE("user:delete"),
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
