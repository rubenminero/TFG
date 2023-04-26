package ruben.TFG.controllers.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import ruben.TFG.model.Inscription;
import ruben.TFG.model.Sports_type;

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
    private Long user;

    public InscriptionDTO(Inscription inscription) {
        this.id = inscription.getId();
        this.tournament = inscription.getTournament().getId();
        this.user = inscription.getUser().getId();
    }

    public static InscriptionDTO fromInscription(Inscription inscription) {
        return new InscriptionDTO(inscription);
    }

    public static List<InscriptionDTO> fromInscriptions(List<Inscription> inscriptions) {
        return inscriptions.stream().map(InscriptionDTO::fromInscription).collect(Collectors.toList());
    }

    public static Inscription toInscription() {
        return new Inscription();
    }
}