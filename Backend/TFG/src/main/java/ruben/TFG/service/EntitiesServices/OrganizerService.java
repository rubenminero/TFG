package ruben.TFG.service.EntitiesServices;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ruben.TFG.model.Entities.Organizer;
import ruben.TFG.model.Whitelist.Role;
import ruben.TFG.repository.EntitiesRepositories.OrganizerRepository;

import java.util.ArrayList;
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
     * @param id the id of the organizer.
     * @return organizer the organizer with the id.
     */
    public Organizer getOrganizer(Long id){

        return organizerRepository.findById(id).orElse(null);
    }

    /**
     * Save an organizer in the database.
     * @param organizer the organizer to be saved.
     */
    public Organizer saveOrganizer(Organizer organizer){
        organizer.setPassword(passwordEncoder.encode(organizer.getPassword()));
        organizer.setRole(Role.ORGANIZER);
        return organizerRepository.save(organizer);
    }

    /**
     * Disable or enable a organizer in the database,depends on the last state.
     * @param id the id of the organizer to be changed.
     */
    public void changeStateOrganizer(Long id){
        Organizer organizer = this.getOrganizer(id);
        organizer.setEnabled(!organizer.isEnabled());
        organizerRepository.save(organizer);
    }

    /**
     * Gets all the organizers from the database.
     * Only for admins.
     * @return A list with all the organizers.
     */
    public List<Organizer> getAllOrganizers() {

        return organizerRepository.findAll();
    }

    /**
     * Gets all the enabled organizers from the database.
     * @return A list with all the organizers.
     */
    public List<Organizer> getEnabledOrganizers() {
        List<Organizer> organizers_enabled = new ArrayList<Organizer>();
        List<Organizer> organizers = organizerRepository.findAll();
        for (Organizer o : organizers) {
            if (o.isEnabled()){
                organizers_enabled.add(o);
            }
        }
        return organizers_enabled;
    }
    /**
     * Recover an organizer from the database.
     * @param username the username of the organizer.
     * @return organizer the organizer with the username.
     */
    public Organizer getOrganizerByUsername(String username) {
        Organizer organizer = organizerRepository.findByUsername(username);
        if (organizer == null){
            return null;
        }else{
            return organizer;
        }
    }

    }
