package ruben.SPM.model.DTO.Front;

import lombok.AllArgsConstructor;
import lombok.Data;
import ruben.SPM.model.Entities.Organizer;
import ruben.SPM.model.Entities.Sports_type;
import ruben.SPM.model.Entities.Tournament;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class TournamentFrontDTO {
    private Long id;
    private String name;
    private String location;
    private String address;
    private String description;
    private Boolean enabled;
    private Boolean inscription;
    private int capacity;
    private String organizer;
    private String sport_type;

    public TournamentFrontDTO(Tournament tournament) {
        this.id = tournament.getId();
        this.name = tournament.getName();
        this.location = tournament.getLocation();
        this.address = tournament.getAddress();
        this.description = tournament.getDescription();
        this.enabled = tournament.isEnabled();
        this.inscription = tournament.getInscription();
        this.capacity = tournament.getCapacity();
        this.organizer = tournament.getOrganizer().getCompany();
        this.sport_type = tournament.getSport_type().getName();
    }

    public TournamentFrontDTO() {

    }

    public static TournamentFrontDTO fromTournament(Tournament tournament) {
        return new TournamentFrontDTO(tournament);
    }

    public static List<TournamentFrontDTO> fromTournaments(List<Tournament> tournaments) {
        return tournaments.stream().map(TournamentFrontDTO::fromTournament).collect(Collectors.toList());
    }

    public static Tournament toTournament(TournamentFrontDTO tournament, Organizer organizer, Sports_type sportsType) {
        return new Tournament(tournament, organizer, sportsType);
    }
}
