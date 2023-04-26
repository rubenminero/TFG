package ruben.TFG.service;

import org.springframework.stereotype.Service;
import ruben.TFG.model.Organizer;
import ruben.TFG.model.Tournament;
import ruben.TFG.repository.OrganizerRepository;
import ruben.TFG.repository.TournamentRepository;

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
     * @param id the id of the tournament.
     * @return tournament the tournament with the id.
     */
    public Tournament getTournament(Long id){
        return tournamentRepository.findById(id).orElse(null);
    }

    /**
     * Save a tournament in the database.
     * @param tournament the tournament to be saved.
     */
    public Tournament saveTournament(Tournament tournament){

        return tournamentRepository.save(tournament);
    }

    /**
     * Disable or enable a tournament in the database,depends on the last state.
     * @param id the id of the tournament to be changed.
     */
    public void changeStateTournament(Long id){
        Tournament tournament = this.getTournament(id);
        tournament.setEnabled(!tournament.isEnabled());
        tournamentRepository.save(tournament);
    }

    /**
     * Disable or enable the inscriptions for a tournament in the database,depends on the last state.
     * @param id the id of the tournament to be changed.
     */
    public void changeInscriptionTournament(Long id){
        Tournament tournament = this.getTournament(id);
        tournament.setInscription(!tournament.allows_Inscriptions());
        tournamentRepository.save(tournament);
    }

    /**
     * Gets all the tournaments from the database.
     * Only for admins.
     * @return A list with all the tournaments.
     */
    public List<Tournament> getAllTournaments() {

        return tournamentRepository.findAll();
    }
    /**
     * Gets all the enabled tournaments from the database.
     * @return A list with all the tournaments.
     */
    public List<Tournament> getEnabledTournaments() {
        List<Tournament> tournaments_enabled = new ArrayList<Tournament>();
        List<Tournament> tournaments = this.getAllTournaments();
        for (Tournament t : tournaments) {
            if (t.isEnabled()){
                tournaments_enabled.add(t);
            }
        }
        return tournaments_enabled;
    }

    /**
     * Recover a tournament from the database.
     * @param name the name of the tournament.
     * @return tournament the tournament with the name.
     */
    public Tournament getTournamentByName(String name) {

        return tournamentRepository.findByName(name).orElse(null);
    }

    }
