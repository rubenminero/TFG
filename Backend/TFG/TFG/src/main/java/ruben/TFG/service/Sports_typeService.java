package ruben.TFG.service;

import org.springframework.stereotype.Service;
import ruben.TFG.model.Sports_type;
import ruben.TFG.model.Tournament;
import ruben.TFG.repository.Sports_typeRepository;
import ruben.TFG.repository.TournamentRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Service for Sports_type class.
 */
@Service
public class Sports_typeService {

    /**
     * Access for Sports_type data.
     */
    private final Sports_typeRepository sportsTypeRepository;

    public Sports_typeService(Sports_typeRepository sportsTypeRepository) {

        this.sportsTypeRepository = sportsTypeRepository;
    }

    /**
     * Recover a sport type from the database.
     * @param id the id of the sport type.
     * @return sport type the sport type with the id.
     */
    public Sports_type getSport_type(Long id){
        return sportsTypeRepository.findById(id).orElse(null);
    }

    /**
     * Save a sport type in the database.
     * @param sport_type the sport type to be saved.
     */
    public Sports_type saveSport_type(Sports_type sport_type){

        return sportsTypeRepository.save(sport_type);
    }

    /**
     * Disable or enable a sport type in the database,depends on the last state.
     * @param id the id of the sport type to be changed.
     */
    public void changeStateSport_type(Long id){
        Sports_type sport_type = this.getSport_type(id);
        sport_type.setEnabled(!sport_type.isEnabled());
        sportsTypeRepository.save(sport_type);
    }


    /**
     * Gets all the sport types from the database.
     * Only for admins.
     * @return A list with all the sport types.
     */
    public List<Sports_type> getAllSports_types() {

        return sportsTypeRepository.findAll();
    }
    /**
     * Gets all the enabled sport types from the database.
     * @return A list with all the sport types.
     */
    public List<Sports_type> getEnabledSport_types() {
        List<Sports_type> sports_types_enabled = new ArrayList<Sports_type>();
        List<Sports_type> sports_types = this.getAllSports_types();
        for (Sports_type s : sports_types) {
            if (s.isEnabled()){
                sports_types_enabled.add(s);
            }
        }
        return sports_types_enabled;
    }

    /**
     * Recover a sport type from the database.
     * @param name the name of the sport type.
     * @return sport type the sport type with the name.
     */
    public Sports_type getSport_typetByName(String name) {

        return sportsTypeRepository.findByName(name).orElse(null);
    }

    }
