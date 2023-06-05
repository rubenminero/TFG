package ruben.SPM.model.DTO.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import ruben.SPM.model.Entities.Sports_type;

import java.util.List;
import java.util.stream.Collectors;

/**
 * DTO to manage the sport type data.
 */
@Data
@AllArgsConstructor
public class Sports_typeDTO {

    private Long id;
    private String name;
    private Boolean enabled;

    public Sports_typeDTO(Sports_type sport_type) {
        this.id = sport_type.getId();
        this.name = sport_type.getName();
        this.enabled = sport_type.isEnabled();
    }

    public static Sports_typeDTO fromSport_type(Sports_type sport_type) {
        return new Sports_typeDTO(sport_type);
    }

    public static List<Sports_typeDTO> fromSports_type(List<Sports_type> sports_types) {
        return sports_types.stream().map(Sports_typeDTO::fromSport_type).collect(Collectors.toList());
    }

    public static Sports_type toSport_type(Sports_typeDTO sport_type) {
        return new Sports_type(sport_type);
    }
}