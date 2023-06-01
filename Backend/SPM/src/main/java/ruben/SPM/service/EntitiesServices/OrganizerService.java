package ruben.SPM.service.EntitiesServices;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ruben.SPM.model.Entities.Athlete;
import ruben.SPM.model.Entities.Organizer;
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
        organizer.setEnabled(organizer.isEnabled());
        organizer.setDisabled_at(organizer.getDisabled_at());
        organizer.setRole(organizer.getRole());
        organizer.setPassword(organizer.getPassword());
        organizerRepository.update(organizer.getId(),
                organizer.getUsername(),
                organizer.getPassword(),
                organizer.getFirst_name(),
                organizer.getLast_name(),
                organizer.getNif(),
                organizer.getEmail(),
                organizer.getRole(),
                organizer.getAddress(),
                organizer.getCompany_name(),
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
                organizer.getCompany_name(),
                organizer.getDisabled_at(),
                organizer.isEnabled());
        return organizer;

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
        organizer.setDisabled_at(new Date());
        this.updateOrganizer(organizer);
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

}
