package ruben.SPM.service.EntitiesServices;

import org.springframework.stereotype.Service;
import ruben.SPM.model.Entities.Tournament;
import ruben.SPM.repository.EntitiesRepositories.TournamentRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Service for Tournament class.
 */
@Service
public class TournamentService {

    /**
     * Access for Tournament data.
     */
    private final TournamentRepository tournamentRepository;

    public TournamentService(TournamentRepository tournamentRepository) {

        this.tournamentRepository = tournamentRepository;
    }

    /**
     * Recover a tournament from the database.
     * 
     * @param id the id of the tournament.
     * @return tournament the tournament with the id.
     */
    public Tournament getTournament(Long id) {
        return tournamentRepository.findById(id).orElse(null);
    }

    /**
     * Save a tournament in the database.
     * 
     * @param tournament the tournament to be saved.
     */
    public Tournament saveTournament(Tournament tournament) {

        return tournamentRepository.save(tournament);
    }

    /**
     * Update a tournament in the database.
     * 
     * @param tournament the tournament to be updated.
     */
    public Tournament updateTournament(Tournament tournament) {

        tournamentRepository.update(tournament.getId(),
                tournament.getAddress(),
                tournament.getDescription(),
                tournament.getInscription(),
                tournament.getCapacity(),
                tournament.isEnabled(),
                tournament.getLocation(),
                tournament.getName(),
                tournament.getOrganizer(),
                tournament.getSport_type());
        return tournament;
    }

    /**
     * Updates a tournament,adding one to the capacity
     * @param tournament the tournament to be updated.
     */
    public void moreCapacity(Tournament tournament) {
        tournament.setCapacity(tournament.getCapacity() + 1);
        this.updateTournament(tournament);
    }

    /**
     * Updates a tournament, subtracting one from the capacity
     * @param tournament the tournament to be updated.
     */
    public void lessCapacity(Tournament tournament) {
        tournament.setCapacity(tournament.getCapacity() -1);
        this.updateTournament(tournament);
    }

    /**
     * Disable or enable a tournament in the database,depends on the last state.
     * 
     * @param id the id of the tournament to be changed.
     */
    public void changeStateTournament(Long id) {
        Tournament tournament = this.getTournament(id);
        tournament.setEnabled(!tournament.isEnabled());
        this.updateTournament(tournament);
    }

    /**
     * Disable or enable the Inscription for a tournament in the database,depends on
     * the last state.
     * 
     * @param id the id of the tournament to be changed.
     */
    public void changeInscriptionTournament(Long id) {
        Tournament tournament = this.getTournament(id);
        tournament.setInscription(!tournament.getInscription());
        this.updateTournament(tournament);
    }

    /**
     * Gets all the tournaments from the database.
     * Only for admins.
     * 
     * @return A list with all the tournaments.
     */
    public List<Tournament> getAllTournaments() {

        return tournamentRepository.findAll();
    }

    /**
     * Gets all the enabled tournaments from the database.
     * 
     * @return A list with all the tournaments.
     */
    public List<Tournament> getEnabledTournaments() {
        List<Tournament> tournaments_enabled = new ArrayList<Tournament>();
        List<Tournament> tournaments = this.getAllTournaments();
        for (Tournament t : tournaments) {
            if (t.isEnabled() && t.getInscription() && t.getCapacity() > 0) {
                tournaments_enabled.add(t);
            }
        }
        return tournaments_enabled;
    }

    /**
     * Gets all the enabled events from the database.
     * 
     * @return A list with all the events.
     */
    public List<Tournament> getEnabledEvents() {
        List<Tournament> events_enabled = new ArrayList<Tournament>();
        List<Tournament> events = this.getAllTournaments();
        for (Tournament t : events) {
            if (t.isEnabled() && !t.getInscription()) {
                events_enabled.add(t);
            }
        }
        return events_enabled;
    }

    /**
     * Recover a tournament from the database.
     * 
     * @param name the name of the tournament.
     * @return tournament the tournament with the name.
     */
    public Tournament getTournamentByName(String name) {

        return tournamentRepository.findByName(name).orElse(null);
    }

}
