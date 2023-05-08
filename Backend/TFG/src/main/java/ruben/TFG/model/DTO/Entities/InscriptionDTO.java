package ruben.TFG.model.DTO.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import ruben.TFG.model.Entities.Inscription;

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
    private Boolean enabled;

    public InscriptionDTO(Long tournament, Long user) {
        this.tournament = tournament;
        this.user = user;
        this.enabled = true;
    }
    public InscriptionDTO(Inscription inscription) {
        this.id = inscription.getId();
        this.tournament = inscription.getTournament().getId();
        this.user = inscription.getUser().getId();
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

    public static Inscription toInscription() {
        return new Inscription();
    }
}