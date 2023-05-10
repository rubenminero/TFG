package ruben.TFG.model.DTO.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import ruben.TFG.model.Entities.Athlete;
import ruben.TFG.model.Entities.Inscription;
import ruben.TFG.model.Entities.Tournament;
import ruben.TFG.model.Entities.Watchlist;

import java.util.List;
import java.util.stream.Collectors;

/**
 * DTO to manage the inscription data.
 */
@Data
@AllArgsConstructor
public class InscriptionDTO {

    private Long id;
    private Long tournament;
    private Long athlete;
    private Boolean enabled;
    public InscriptionDTO(Inscription inscription) {
        this.id = inscription.getId();
        this.tournament = inscription.getTournament().getId();
        this.athlete = inscription.getAthlete().getId();
        this.enabled = inscription.isEnabled();
    }

    public InscriptionDTO() {

    }


    public static InscriptionDTO fromInscription(Inscription inscription) {
        return new InscriptionDTO(inscription);
    }

    public static List<InscriptionDTO> fromInscriptions(List<Inscription> inscriptions) {
        return inscriptions.stream().map(InscriptionDTO::fromInscription).collect(Collectors.toList());
    }

    public static Inscription toInscription(InscriptionDTO inscriptionDTO, Tournament tournament, Athlete athlete) {

        return new Inscription(inscriptionDTO,tournament,athlete);
    }
}