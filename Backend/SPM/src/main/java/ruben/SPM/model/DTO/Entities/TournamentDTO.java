package ruben.SPM.model.DTO.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import ruben.SPM.model.Entities.Organizer;
import ruben.SPM.model.Entities.Sports_type;
import ruben.SPM.model.Entities.Tournament;

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
    private int capacity;
    private Long organizer;
    private Long sport_type;

    public TournamentDTO(Tournament tournament) {
        this.id = tournament.getId();
        this.name = tournament.getName();
        this.location = tournament.getLocation();
        this.address = tournament.getAddress();
        this.description = tournament.getDescription();
        this.enabled = tournament.isEnabled();
        this.inscription = tournament.getInscription();
        this.capacity = tournament.getCapacity();
        this.organizer = tournament.getOrganizer().getId();
        this.sport_type = tournament.getSport_type().getId();
    }

    public TournamentDTO() {

    }

    public static TournamentDTO fromTournament(Tournament tournament) {
        return new TournamentDTO(tournament);
    }

    public static List<TournamentDTO> fromTournaments(List<Tournament> tournaments) {
        return tournaments.stream().map(TournamentDTO::fromTournament).collect(Collectors.toList());
    }

    public static Tournament toTournament(TournamentDTO tournament, Organizer organizer, Sports_type sportsType) {
        return new Tournament(tournament, organizer, sportsType);
    }
}