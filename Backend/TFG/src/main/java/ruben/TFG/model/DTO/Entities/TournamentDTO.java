package ruben.TFG.model.DTO.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import ruben.TFG.model.Entities.Tournament;

import java.util.List;
import java.util.stream.Collectors;

/**
 * DTO to manage the tournament data.
 */
@Data
@AllArgsConstructor
public class TournamentDTO {

    private Long id;
    private String name;
    private String location;
    private String address;
    private String description;
    private Boolean enabled;
    private Boolean inscription;
    private Long organizer;
    private Long sport_type;

    public TournamentDTO(String name, String location, String address, String description, Boolean inscription, Long organizer, Long sport_type  ) {
        this.name = name;
        this.location = location;
        this.address = address;
        this.description = description;
        this.enabled = true;
        this.inscription = inscription;
        this.organizer = organizer;
        this.sport_type = sport_type;
    }
    public TournamentDTO(Tournament tournament) {
        this.id = tournament.getId();
        this.name = tournament.getName();
        this.location = tournament.getLocation();
        this.address = tournament.getAddress();
        this.description = tournament.getDescription();
        this.enabled = tournament.isEnabled();
        this.inscription = tournament.allows_Inscriptions();
        this.organizer = tournament.getOrganizer().getId();
        this.sport_type = tournament.getSport_type().getId();
    }

    public TournamentDTO(){

    }

    public static TournamentDTO fromTournament(Tournament tournament) {
        return new TournamentDTO(tournament);
    }

    public static List<TournamentDTO> fromTournaments(List<Tournament> tournaments) {
        return tournaments.stream().map(TournamentDTO::fromTournament).collect(Collectors.toList());
    }

    public static Tournament toTournament(TournamentDTO tournament) {
        return new Tournament(tournament.name,tournament.inscription,tournament.location,tournament.address,tournament.description);
    }
}