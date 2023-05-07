package ruben.TFG.service.EntitiesServices;

import org.springframework.stereotype.Service;
import ruben.TFG.model.Entities.Athlete;
import ruben.TFG.repository.EntitiesRepositories.AthleteRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Service for user class.
 */
@Service
public class AthleteService {

    /**
     * Access for Athlete data.
     */
    private final AthleteRepository athleteRepository;

    public AthleteService(AthleteRepository athleteRepository) {

        this.athleteRepository = athleteRepository;
    }

    /**
     * Recover a athlete from the database.
     * @param id the id of the athlete.
     * @return athlete the athlete with the id.
     */
    public Athlete getAthlete(Long id){

        return athleteRepository.findById(id).orElse(null);
    }

    /**
     * Save a athlete in the database.
     * @param athlete the athlete to be saved.
     */
    public Athlete saveAthlete(Athlete athlete){
        return athleteRepository.save(athlete);
    }

    /**
     * Disable or enable a athlete in the database,depends on the last state.
     * @param id the id of the athlete to be changed.
     */
    public void changeStateAthlete(Long id){
        Athlete athlete = this.getAthlete(id);
        athlete.setEnabled(!athlete.isEnabled());
        athleteRepository.save(athlete);
    }

    /**
     * Gets all the athletes from the database.
     * Only for admins.
     * @return A list with all the athletes.
     */
    public List<Athlete> getAllAthletes() {

        return athleteRepository.findAll();
    }
    /**
     * Gets all the enabled athletes from the database.
     * @return A list with all the athletes.
     */
    public List<Athlete> getEnabledAthletes() {
        List<Athlete> athletes_enabled = new ArrayList<Athlete>();
        List<Athlete> athletes = athleteRepository.findAll();
        for (Athlete u : athletes) {
            if (u.isEnabled()){
                athletes_enabled.add(u);
            }
        }
        return athletes_enabled;
    }
    /**
     * Recover a athlete from the database.
     * @param username the username of the athlete.
     * @return athlete the athlete with the username.
     */
    public Athlete getAthleteByUsername(String username) {

        return athleteRepository.findByUsername(username).orElse(null);
    }

    }
