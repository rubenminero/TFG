package ruben.SPM.service.EntitiesServices;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ruben.SPM.model.Entities.Organizer;
import ruben.SPM.model.Entities.Tournament;
import ruben.SPM.model.Whitelist.Role;
import ruben.SPM.repository.EntitiesRepositories.OrganizerRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Service for Organizer class.
 */
@Service
@AllArgsConstructor
public class OrganizerService {
    private final OrganizerRepository organizerRepository;

    private final TournamentService tournamentService;
    private final PasswordEncoder passwordEncoder;

    /**
     * Recover an organizer from the database.
     * 
     * @param id the id of the organizer.
     * @return organizer the organizer with the id.
     */
    public Organizer getOrganizer(Long id) {

        return organizerRepository.findById(id).orElse(null);
    }

    /**
     * Save an organizer in the database.
     * 
     * @param organizer the organizer to be saved.
     */
    public Organizer saveOrganizer(Organizer organizer) {
        organizer.setPassword(passwordEncoder.encode(organizer.getPassword()));
        organizer.setRole(Role.ORGANIZER);
        return organizerRepository.save(organizer);
    }

    /**
     * Update an organizer in the database.
     * 
     * @param organizer       the organizer to be updated.
     * @param organizer_saved the organizer stored in the db before the update.
     * @return organizer the organizer updated.
     */
    public Organizer updateOrganizer(Organizer organizer, Organizer organizer_saved) {
        organizer.setEnabled(organizer_saved.isEnabled());
        organizer.setDisabled_at(organizer_saved.getDisabled_at());
        organizer.setRole(organizer_saved.getRole());
        organizer.setPassword(organizer_saved.getPassword());
        organizerRepository.update(organizer.getId(),
                organizer.getUsername(),
                organizer.getPassword(),
                organizer.getFirst_name(),
                organizer.getLast_name(),
                organizer.getNif(),
                organizer.getEmail(),
                organizer.getRole(),
                organizer.getAddress(),
                organizer.getCompany(),
                organizer.getDisabled_at(),
                organizer.isEnabled());
        return organizer;
    }

    /**
     * Update an organizer in the database.
     * 
     * @param organizer the organizer to be updated.
     * @return organizer the organizer updated.
     */
    public Organizer updateOrganizer(Organizer organizer) {
        organizerRepository.update(organizer.getId(),
                organizer.getUsername(),
                organizer.getPassword(),
                organizer.getFirst_name(),
                organizer.getLast_name(),
                organizer.getNif(),
                organizer.getEmail(),
                organizer.getRole(),
                organizer.getAddress(),
                organizer.getCompany(),
                organizer.getDisabled_at(),
                organizer.isEnabled());
        return organizer;

    }

    /**
     * Delete an organizer in the database.
     * 
     * @param organizer the organizer to be deleted.
     */
    public void deleteOrganizer(Organizer organizer) {
        this.organizerRepository.delete(organizer);
    }

    /**
     * Return the organizer with the password hashed.
     * 
     * @param organizer the organizer to be updated.
     * @return organizer the organizer with the password updated.
     */
    public Organizer setPasswordHashed(Organizer organizer, String password) {
        organizer.setPassword(passwordEncoder.encode(password));
        return organizer;
    }

    /**
     * Disable or enable a organizer in the database,depends on the last state.
     * 
     * @param id the id of the organizer to be changed.
     */
    public void changeStateOrganizer(Long id) {
        Organizer organizer = this.getOrganizer(id);
        organizer.setEnabled(!organizer.isEnabled());
        if (!organizer.isEnabled()){
            organizer.setDisabled_at(new Date());
        }else{
            organizer.setDisabled_at(null);
        }
        organizerRepository.save(organizer);
    }

    /**
     * Gets all the organizers from the database.
     * Only for admins.
     * 
     * @return A list with all the organizers.
     */
    public List<Organizer> getAllOrganizers() {

        return organizerRepository.findAll();
    }

    /**
     * Gets all the tournaments for the organizer.
     * 
     * @return A list with all the tournaments.
     */
    public List<Tournament> getTournamentsOrganizer(Long id) {
        List<Tournament> tournaments = this.tournamentService.getEnabledTournaments();
        List<Tournament> tournaments_organizer = new ArrayList<Tournament>();
        for (Tournament t : tournaments) {
            if (t.getOrganizer().getId().equals(id)) {
                tournaments_organizer.add(t);
            }
        }
        return tournaments_organizer;
    }

    /**
     * Gets all the events for the organizer.
     * 
     * @return A list with all the events.
     */
    public List<Tournament> getEventsOrganizer(Long id) {
        List<Tournament> events = this.tournamentService.getEnabledEvents();
        List<Tournament> events_organizer = new ArrayList<Tournament>();
        for (Tournament t : events) {
            if (t.getOrganizer().getId().equals(id)) {
                events_organizer.add(t);
            }
        }
        return events_organizer;
    }

    /**
     * Gets all the enabled organizers from the database.
     * 
     * @return A list with all the organizers.
     */
    public List<Organizer> getEnabledOrganizers() {
        List<Organizer> organizers_enabled = new ArrayList<Organizer>();
        List<Organizer> organizers = organizerRepository.findAll();
        for (Organizer o : organizers) {
            if (o.isEnabled()) {
                organizers_enabled.add(o);
            }
        }
        return organizers_enabled;
    }

    /**
     * Recover an organizer from the database.
     *
     * @param username the username of the organizer.
     * @return organizer the organizer with the username.
     */
    public Organizer getOrganizerByUsername(String username) {
        Organizer organizer = organizerRepository.findByUsername(username);
        if (organizer == null) {
            return null;
        } else {
            return organizer;
        }
    }

    /**
     * Recover an organizer from the database.
     *
     * @param company_name the company_name of the organizer.
     * @return organizer the organizer with the company_name.
     */
    public Organizer getOrganizerByCompany_name(String company_name) {
        Organizer organizer = organizerRepository.findByCompany(company_name);
        if (organizer == null) {
            return null;
        } else {
            return organizer;
        }
    }

    /**
     * Checks if the company name is valid.
     * 
     * @param company_name the company_name of the organizer.
     * @return true if valid, false otherwise.
     */
    public boolean validCompany_name(String company_name) {
        Organizer organizer = this.getOrganizerByCompany_name(company_name);
        if (organizer != null) {
            return true;
        } else {
            return false;
        }
    }

}
