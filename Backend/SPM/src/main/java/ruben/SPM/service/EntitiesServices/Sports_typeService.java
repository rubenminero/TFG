package ruben.SPM.service.EntitiesServices;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ruben.SPM.model.Entities.Sports_type;
import ruben.SPM.model.Entities.Watchlist;
import ruben.SPM.repository.EntitiesRepositories.Sports_typeRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Service for Sports_type class.
 */
@Service
@AllArgsConstructor
public class Sports_typeService {

    /**
     * Access for Sports_type data.
     */
    private final Sports_typeRepository sportsTypeRepository;
    /**
     * Recover a sport type from the database.
     *
     * @param id the id of the sport type.
     * @return sport type the sport type with the id.
     */
    public Sports_type getSport_type(Long id) {
        return sportsTypeRepository.findById(id).orElse(null);
    }

    /**
     * Save a sport type in the database.
     * 
     * @param sport_type the sport type to be saved.
     */
    public Sports_type saveSport_type(Sports_type sport_type) {

        return sportsTypeRepository.save(sport_type);
    }

    /**
     * Update a sport type in the database.
     * 
     * @param sport_type the sport type to be updated.
     */
    public Sports_type updateSport_type(Sports_type sport_type) {
        sportsTypeRepository.update(sport_type.getId(),
                sport_type.isEnabled(),
                sport_type.getName());
        return sport_type;
    }

    /**
     * Delete a sport type from the database.
     * 
     * @param id the id of the sport type.
     */
    public void deleteSport_type(Long id) {
        this.sportsTypeRepository.delete(this.getSport_type(id));
    }

    /**
     * Disable or enable a sport type in the database,depends on the last state.
     * 
     * @param id the id of the sport type to be changed.
     */
    public void changeStateSport_type(Long id) {
        Sports_type sport_type = this.getSport_type(id);
        sport_type.setEnabled(!sport_type.isEnabled());
        sportsTypeRepository.update(sport_type.getId(),
                sport_type.isEnabled(),
                sport_type.getName());
    }


    /**
     * Gets all the sport types from the database.
     * Only for admins.
     *
     * @return A list with all the sport types.
     */
    public List<Sports_type> getAllSports_types() {

        return sportsTypeRepository.findAll();
    }
    /**
     * Gets all the enabled sport types from the database.
     * 
     * @return A list with all the sport types.
     */
    public List<Sports_type> getEnabledSport_types() {
        List<Sports_type> sports_types_enabled = new ArrayList<Sports_type>();
        List<Sports_type> sports_types = this.getAllSports_types();
        for (Sports_type s : sports_types) {
            if (s.isEnabled()) {
                sports_types_enabled.add(s);
            }
        }
        return sports_types_enabled;
    }

    /**
     * Recover a sport type from the database.
     * 
     * @param name the name of the sport type.
     * @return sport type the sport type with the name.
     */
    public Sports_type getSport_typeByName(String name) {

        return sportsTypeRepository.findByName(name).orElse(null);
    }

    /**
     * Checks if the name is valid.
     *
     * @param name the name of the sport type.
     * @return true if is valid, false otherwise.
     */
    public Boolean validName(String name) {
        Sports_type sportsType = this.getSport_typeByName(name);

        if (sportsType != null){
            return true;
        }else{
            return false;
        }
    }


}
