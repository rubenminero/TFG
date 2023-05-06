package ruben.TFG.controllers.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import ruben.TFG.model.Organizer;

import java.util.List;
import java.util.stream.Collectors;

/**
 * DTO to manage the organizer data.
 */
@Data
@AllArgsConstructor
public class OrganizerDTO {

    private Long id;
    private String username;
    private String password;
    private String nif;
    private String email;

    private Boolean enabled;

    public OrganizerDTO(Organizer organizer) {
        this.id = organizer.getId();
        this.username = organizer.getUsername();
        this.password = organizer.getPassword();
        this.nif = organizer.getNif();
        this.email = organizer.getEmail();
        this.enabled = organizer.isEnabled();
    }

    public static OrganizerDTO fromOrganizer(Organizer organizer) {
        return new OrganizerDTO(organizer);
    }

    public static List<OrganizerDTO> fromOrganizers(List<Organizer> organizers) {
        return organizers.stream().map(OrganizerDTO::fromOrganizer).collect(Collectors.toList());
    }

    public static Organizer toOrganizer(OrganizerDTO organizer) {
        return new Organizer(organizer.username,organizer.password,organizer.nif,organizer.email);
    }
}