package ruben.SPM.service.EntitiesServices;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ruben.SPM.model.Entities.Athlete;
import ruben.SPM.model.Entities.Inscription;
import ruben.SPM.model.Entities.Organizer;
import ruben.SPM.model.Whitelist.Role;
import ruben.SPM.repository.EntitiesRepositories.AthleteRepository;
import ruben.SPM.service.Auth.TokenService;

import java.util.ArrayList;
import java.util.List;

/**
 * Service for user class.
 */
@Service
@AllArgsConstructor
public class AthleteService {

    /**
     * Access for Athlete data.
     */
    private final AthleteRepository athleteRepository;
    private final InscriptionService inscriptionService;
    private final WatchlistService watchlistService;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;
    private final DeleteService deleteService;

    /**
     * Recover a athlete from the database.
     * 
     * @param id the id of the athlete.
     * @return athlete the athlete with the id.
     */
    public Athlete getAthlete(Long id) {

        return athleteRepository.findById(id).orElse(null);
    }

    /**
     * Save a athlete in the database.
     * 
     * @param athlete the athlete to be saved.
     */
    public Athlete saveAthlete(Athlete athlete) {
        athlete.setPassword(passwordEncoder.encode(athlete.getPassword()));
        athlete.setRole(Role.ATHLETE);
        return athleteRepository.save(athlete);
    }

    /**
     * Disable or enable a athlete in the database,depends on the last state.
     * 
     * @param id the id of the athlete to be changed.
     */
    public void changeStateAthlete(Long id) {
        Athlete athlete = this.getAthlete(id);
        athlete.setEnabled(!athlete.isEnabled());
        athleteRepository.save(athlete);
    }

    /**
     * Delete a user from the database.
     * 
     * @param athlete the user to be deleted.
     */
    public void deleteAthlete(Athlete athlete) {
        this.deleteService.deleteAthlete(athlete);
    }

    /**
     * Return the athlete with the password hashed.
     *
     * @param athlete the athlete to be updated.
     * @return athlete the athlete with the password updated.
     */
    public Athlete setPasswordHashed(Athlete athlete, String password) {
        athlete.setPassword(passwordEncoder.encode(password));
        return athlete;
    }

    /**
     * Gets all the athletes from the database.
     * Only for admins.
     * 
     * @return A list with all the athletes.
     */
    public List<Athlete> getAllAthletes() {

        return athleteRepository.findAll();
    }

    /**
     * Gets all the enabled athletes from the database.
     * 
     * @return A list with all the athletes.
     */
    public List<Athlete> getEnabledAthletes() {
        List<Athlete> athletes_enabled = new ArrayList<Athlete>();
        List<Athlete> athletes = athleteRepository.findAll();
        for (Athlete u : athletes) {
            if (u.isEnabled()) {
                athletes_enabled.add(u);
            }
        }
        return athletes_enabled;
    }

    /**
     * Recover a athlete from the database.
     * 
     * @param username the username of the athlete.
     * @return athlete the athlete with the username.
     */
    public Athlete getAthleteByUsername(String username) {

        return athleteRepository.findByUsername(username).orElse(null);
    }

    /**
     * Update an athlete in the database.
     * 
     * @param athlete       the athlete to be updated.
     * @param athlete_saved the athlete stored in the db before the update.
     * @return athlete the athlete updated.
     */
    public Athlete updateAthlete(Athlete athlete, Athlete athlete_saved) {
        athlete.setEnabled(athlete_saved.isEnabled());
        athlete.setDisabled_at(athlete_saved.getDisabled_at());
        athlete.setRole(athlete_saved.getRole());
        athlete.setPassword(athlete_saved.getPassword());
        athleteRepository.update(
                athlete.getId(),
                athlete.getUsername(),
                athlete.getPassword(),
                athlete.getFirst_name(),
                athlete.getLast_name(),
                athlete.getNif(),
                athlete.getEmail(),
                athlete.getRole(),
                athlete.getDisabled_at(),
                athlete.isEnabled(),
                athlete.getPhone_number());
        return athlete;
    }

    /**
     * Update an athlete in the database.
     * 
     * @param athlete the athlete to be updated.
     * @return athlete the athlete updated.
     */
    public Athlete updateAthlete(Athlete athlete) {
        athleteRepository.update(
                athlete.getId(),
                athlete.getUsername(),
                athlete.getPassword(),
                athlete.getFirst_name(),
                athlete.getLast_name(),
                athlete.getNif(),
                athlete.getEmail(),
                athlete.getRole(),
                athlete.getDisabled_at(),
                athlete.isEnabled(),
                athlete.getPhone_number());
        return athlete;

    }
}
