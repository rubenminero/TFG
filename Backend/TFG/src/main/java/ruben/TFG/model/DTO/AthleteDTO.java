package ruben.TFG.model.DTO;
import java.util.List;
import java.util.stream.Collectors;

import ruben.TFG.model.Entities.Athlete;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DTO to manage the athlete data.
 */
@Data
@AllArgsConstructor
public class AthleteDTO {

    private Long id;
    private String username;
    private String first_name;
    private String last_name;
    private String password;
    private String nif;
    private String email;
    private String phone_number;
    private Boolean enabled;

    public AthleteDTO(Athlete athlete) {
        this.id = athlete.getId();
        this.username = athlete.getUsername();
        this.first_name = athlete.getFirst_name();
        this.last_name = athlete.getLast_name();
        this.password = athlete.getPassword();
        this.nif = athlete.getNif();
        this.email = athlete.getEmail();
        this.enabled = athlete.isEnabled();
        this.phone_number = athlete.getPhone_number();
    }

    public static AthleteDTO fromUser(Athlete athlete) {
        return new AthleteDTO(athlete);
    }

    public static List<AthleteDTO> fromUsers(List<Athlete> athletes) {
        return athletes.stream().map(AthleteDTO::fromUser).collect(Collectors.toList());
    }

    public static Athlete toUser(AthleteDTO user) {
        return new Athlete(user);
    }

}