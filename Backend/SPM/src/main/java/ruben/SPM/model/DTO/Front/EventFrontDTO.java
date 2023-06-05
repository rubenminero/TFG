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
public class EventFrontDTO {
        private Long id;
        private String name;
        private String location;
        private String address;
        private String description;
        private Boolean enabled;
        private String organizer;
        private String sport_type;

        public EventFrontDTO(Tournament tournament){
            this.id = tournament.getId();
            this.name = tournament.getName();
            this.location = tournament.getLocation();
            this.address = tournament.getAddress();
            this.description = tournament.getDescription();
            this.enabled = tournament.isEnabled();
            this.organizer = tournament.getOrganizer().getCompany();
            this.sport_type = tournament.getSport_type().getName();
        }

        public EventFrontDTO(){

        }

        public static EventFrontDTO fromTournament(Tournament tournament) {
            return new EventFrontDTO(tournament);
        }

        public static List<EventFrontDTO> fromTournaments(List<Tournament> tournaments) {
            return tournaments.stream().map(EventFrontDTO::fromTournament).collect(Collectors.toList());
        }

        public static Tournament toTournament(EventFrontDTO tournament, Organizer organizer, Sports_type sportsType) {
            return new Tournament(tournament, organizer, sportsType);
        }
}
