package ruben.SPM.model.DTO.Front;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ruben.SPM.model.Entities.Athlete;
import ruben.SPM.model.Entities.Inscription;
import ruben.SPM.model.Entities.Tournament;
import ruben.SPM.model.Entities.Watchlist;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InscriptionFrontDTO {
    private Long id;
    private String tournament;
    private String athlete;

    private Long tournament_id;
    private Long athlete_id;
    private Boolean enabled;

    public InscriptionFrontDTO(Inscription inscription) {
        this.id = inscription.getId();
        this.tournament = inscription.getTournament().getName();
        this.athlete = inscription.getAthlete().getUsername();
        this.tournament_id = inscription.getTournament().getId();
        this.athlete_id = inscription.getAthlete().getId();
        this.enabled = inscription.isEnabled();
    }


    public static InscriptionFrontDTO fromInscription(Inscription inscription) {
        return new InscriptionFrontDTO(inscription);
    }

    public static List<InscriptionFrontDTO> fromInscriptions(List<Inscription> inscriptions) {
        return inscriptions.stream().map(InscriptionFrontDTO::fromInscription).collect(Collectors.toList());
    }

    public static Inscription toInscription(InscriptionFrontDTO inscriptionFrontDTO, Tournament tournament, Athlete athlete) {

        return new Inscription(inscriptionFrontDTO,tournament,athlete);
    }
}

