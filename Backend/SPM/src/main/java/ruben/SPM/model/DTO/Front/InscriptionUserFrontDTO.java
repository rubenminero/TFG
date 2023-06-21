package ruben.SPM.model.DTO.Front;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ruben.SPM.model.Entities.Athlete;
import ruben.SPM.model.Entities.Inscription;
import ruben.SPM.model.Entities.Tournament;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InscriptionUserFrontDTO {
    private Long id;
    private String athlete;
    private Long athlete_id;
    private Boolean enabled;
    private Long tournament_id;
    private String tournament_name;
    private String tournament_location;
    private String tournament_address;
    private String tournament_description;
    private Boolean tournament_enabled;
    private Boolean tournament_inscription;
    private int tournament_capacity;
    private String tournament_organizer;
    private String tournament_sport_type;

    public InscriptionUserFrontDTO(Inscription inscription) {
        this.id = inscription.getId();
        this.athlete = inscription.getAthlete().getUsername();
        this.athlete_id = inscription.getAthlete().getId();
        this.enabled = inscription.isEnabled();
        this.tournament_id = inscription.getTournament().getId();
        this.tournament_name = inscription.getTournament().getName();
        this.tournament_location = inscription.getTournament().getLocation();
        this.tournament_address = inscription.getTournament().getAddress();
        this.tournament_description = inscription.getTournament().getDescription();
        this.tournament_enabled = inscription.getTournament().isEnabled();
        this.tournament_inscription = inscription.getTournament().getInscription();
        this.tournament_capacity = inscription.getTournament().getCapacity();
        this.tournament_organizer = inscription.getTournament().getOrganizer().getCompany();
        this.tournament_sport_type = inscription.getTournament().getSport_type().getName();

    }

    public static InscriptionUserFrontDTO fromInscription(Inscription inscription) {
        return new InscriptionUserFrontDTO(inscription);
    }

    public static List<InscriptionUserFrontDTO> fromInscriptions(List<Inscription> inscriptions) {
        return inscriptions.stream().map(InscriptionUserFrontDTO::fromInscription).collect(Collectors.toList());
    }

    public static Inscription toInscription(InscriptionUserFrontDTO inscriptionFrontDTO, Tournament tournament,
            Athlete athlete) {

        return new Inscription(inscriptionFrontDTO, tournament, athlete);
    }
}
