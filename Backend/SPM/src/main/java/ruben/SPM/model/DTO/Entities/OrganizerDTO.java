package ruben.SPM.model.DTO.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import ruben.SPM.model.Entities.Organizer;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * DTO to manage the organizer data.
 */
@Data
@AllArgsConstructor
public class OrganizerDTO {

    private String username;
    private String password;
    private String first_name;
    private String last_name;
    private String nif;
    private String email;
    private Long id;
    private String company_name;
    private String address;
    private Boolean enabled = Boolean.TRUE;
    private Date disabled_at = null;

    public OrganizerDTO(Organizer organizer) {
        this.id = organizer.getId();
        this.username = organizer.getUsername();
        this.first_name = organizer.getFirst_name();
        this.last_name = organizer.getLast_name();
        this.password = organizer.getPassword();
        this.nif = organizer.getNif();
        this.email = organizer.getEmail();
        this.company_name = organizer.getCompany_name();
        this.address = organizer.getAddress();
        this.disabled_at = organizer.getDisabled_at();
        this.enabled = organizer.isEnabled();
    }

    public static OrganizerDTO fromOrganizer(Organizer organizer) {
        return new OrganizerDTO(organizer);
    }

    public static List<OrganizerDTO> fromOrganizers(List<Organizer> organizers) {
        return organizers.stream().map(OrganizerDTO::fromOrganizer).collect(Collectors.toList());
    }

    public static Organizer toOrganizer(OrganizerDTO organizer) {
        return new Organizer(organizer);
    }
}