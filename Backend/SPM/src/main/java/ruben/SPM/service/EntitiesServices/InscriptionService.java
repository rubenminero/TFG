package ruben.SPM.service.EntitiesServices;

import org.springframework.stereotype.Service;
import ruben.SPM.model.Entities.Inscription;
import ruben.SPM.repository.EntitiesRepositories.InscriptionRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Service for Inscription class.
 */
@Service
public class InscriptionService {

    /**
     * Access for Inscription data.
     */
    private final InscriptionRepository inscriptionRepository;

    public InscriptionService(InscriptionRepository inscriptionRepository) {

        this.inscriptionRepository = inscriptionRepository;
    }

    /**
     * Recover a inscription from the database.
     * @param id the id of the inscription.
     * @return inscription the inscription with the id.
     */
    public Inscription getInscription(Long id){
        return inscriptionRepository.findById(id).orElse(null);
    }

    /**
     * Save a inscription in the database.
     * @param inscription the inscription to be saved.
     */
    public Inscription saveInscription(Inscription inscription){

        return inscriptionRepository.save(inscription);
    }

    /**
     * Disable or enable a inscription in the database,depends on the last state.
     * @param id the id of the inscription to be changed.
     */
    public void changeStateInscription(Long id){
        Inscription inscription = this.getInscription(id);
        inscription.setEnabled(!inscription.isEnabled());
        inscriptionRepository.save(inscription);
    }

    /**
     * Gets all the inscriptions from the database.
     * Only for admins.
     * @return A list with all the inscriptions.
     */
    public List<Inscription> getAllInscriptions() {

        return inscriptionRepository.findAll();
    }
    /**
     * Gets all the enabled inscriptions for a user.
     * @return A list with all the inscriptions for this user.
     */
    public List<Inscription> getEnabledInscriptions(Long id) {
        List<Inscription> inscriptions_enabled = new ArrayList<Inscription>();
        List<Inscription> inscriptions = this.getAllInscriptions();
        for (Inscription i : inscriptions) {
            if (i.isEnabled() && i.getAthlete().getId() == id){
                inscriptions_enabled.add(i);
            }
        }
        return inscriptions_enabled;
    }


    }
