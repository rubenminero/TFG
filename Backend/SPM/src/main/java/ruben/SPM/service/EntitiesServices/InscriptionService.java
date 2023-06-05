package ruben.SPM.service.EntitiesServices;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ruben.SPM.model.Entities.Athlete;
import ruben.SPM.model.Entities.Inscription;
import ruben.SPM.model.Entities.Tournament;
import ruben.SPM.model.Entities.Watchlist;
import ruben.SPM.repository.EntitiesRepositories.InscriptionRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Service for Inscription class.
 */
@Service
@AllArgsConstructor
public class InscriptionService {

    /**
     * Access for Inscription data.
     */
    private final InscriptionRepository inscriptionRepository;
    private final TournamentService tournamentService;

    /**
     * Recover a inscription from the database.
     * 
     * @param id the id of the inscription.
     * @return inscription the inscription with the id.
     */
    public Inscription getInscription(Long id) {
        return inscriptionRepository.findById(id).orElse(null);
    }

    /**
     * Gets all the inscriptions for a tournament.
     * 
     * @param id the id of the tournament.
     * @return A list with all the inscriptions.
     */
    public List<Inscription> getInscriptionsforTournament(Long id) {
        List<Inscription> inscriptions = this.getAllInscriptions();
        List<Inscription> inscriptions_tournament = new ArrayList<Inscription>();

        for (Inscription i : inscriptions) {
            if (i.getTournament().getId() == id) {
                inscriptions_tournament.add(i);
            }
        }
        return inscriptions_tournament;
    }

    /**
     * Delete a inscription from the database.
     * 
     * @param inscription the inscription to be deleted.
     */
    public void deleteInscription(Inscription inscription) {
        inscriptionRepository.delete(inscription);
    }

    /**
     * Delete a inscription from the database.
     * 
     * @param id the id of the inscription.
     */
    public void deleteInscription(Long id) {
        Inscription inscription = this.getInscription(id);
        this.tournamentService.moreCapacity(inscription.getTournament());
        this.inscriptionRepository.delete(this.getInscription(id));
    }

    /**
     * Save a inscription in the database.
     * 
     * @param inscription the inscription to be saved.
     */
    public Inscription saveInscription(Inscription inscription) {
        this.tournamentService.lessCapacity(inscription.getTournament());
        return inscriptionRepository.save(inscription);
    }

    /**
     * Disable or enable a inscription in the database,depends on the last state.
     * 
     * @param id the id of the inscription to be changed.
     */
    public void changeStateInscription(Long id) {
        Inscription inscription = this.getInscription(id);
        inscription.setEnabled(!inscription.isEnabled());
        if (!inscription.isEnabled()){
            this.tournamentService.moreCapacity(inscription.getTournament());
        }else{
            this.tournamentService.lessCapacity(inscription.getTournament());
        }
        inscriptionRepository.save(inscription);
    }

    /**
     * Gets all the inscriptions from the database.
     * Only for admins.
     * 
     * @return A list with all the inscriptions.
     */
    public List<Inscription> getAllInscriptions() {

        return inscriptionRepository.findAll();
    }

    /**
     * Gets all the enabled inscriptions for a user.
     * 
     * @return A list with all the inscriptions for this user.
     */
    public List<Inscription> getEnabledInscriptions(Long id) {
        List<Inscription> inscriptions_enabled = new ArrayList<Inscription>();
        List<Inscription> inscriptions = this.getAllInscriptions();
        for (Inscription i : inscriptions) {
            if (i.isEnabled() && i.getAthlete().getId() == id) {
                inscriptions_enabled.add(i);
            }
        }
        return inscriptions_enabled;
    }

    /**
     * Checks if the inscription is valid to be saved.
     * @return true if is valid, false otherwise.
     */
    public Boolean validInscription(Athlete athlete, Tournament tournament) {
        List<Inscription> inscriptions = this.getAllInscriptions();
        for (Inscription i : inscriptions) {
            if (athlete.getId() == i.getAthlete().getId() && tournament.getId() == i.getTournament().getId()) {
                return true;
            }
        }
        return false;
    }

}
