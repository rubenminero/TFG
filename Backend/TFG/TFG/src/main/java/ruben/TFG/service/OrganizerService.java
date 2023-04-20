package ruben.TFG.service;

import org.springframework.stereotype.Service;
import ruben.TFG.model.Organizer;
import ruben.TFG.repository.OrganizerRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Service for Organizer class.
 */
@Service
public class OrganizerService {

    /**
     * Access for Organizer data.
     */
    private final OrganizerRepository organizerRepository;

    public OrganizerService(OrganizerRepository organizerRepository) {

        this.organizerRepository = organizerRepository;
    }

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

        return organizerRepository.save(organizer);
    }

    /**
     * Disable an organizer in the database.
     * @param id the id of the organizer to be disabled.
     */
    public void disableOrganizer(Long id){
        Organizer organizer = this.getOrganizer(id);
        organizer.setEnabled(false);
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

    public Organizer getOrganizerByUsername(String username) {

        return organizerRepository.findByUsername(username).orElse(null);
    }

    }