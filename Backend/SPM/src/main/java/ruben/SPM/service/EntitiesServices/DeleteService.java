package ruben.SPM.service.EntitiesServices;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ruben.SPM.model.Entities.*;
import ruben.SPM.model.JWT_Token.Token;
import ruben.SPM.repository.EntitiesRepositories.*;
import ruben.SPM.repository.TokenRepository.TokenRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class DeleteService {

    private final AthleteRepository athleteRepository;
    private final InscriptionRepository inscriptionRepository;
    private final OrganizerRepository organizerRepository;
    private final Sports_typeRepository sportsTypeRepository;
    private final TournamentRepository tournamentRepository;
    private final WatchlistRepository  watchlistRepository;
    private final TokenRepository tokenRepository;


    /**
     * Delete a user from the database.
     * @param athlete the user to be deleted.
     */
    public void deleteAthlete(Athlete athlete) {
        this.deleteWatchlistsUser(athlete.getId());
        this.deleteInscriptionUser(athlete.getId());
        this.deleteTokensUser(athlete.getId());
        this.athleteRepository.delete(athlete);
    }

    /**
     * Delete a user from the database.
     * @param organizer the user to be deleted.
     */
    public void deleteOrganizer(Organizer organizer) {
        this.deleteWatchlistsUser(organizer.getId());
        this.deleteInscriptionUser(organizer.getId());
        this.deleteTokensUser(organizer.getId());
        this.deleteTournamentsUser(organizer.getId());
        this.organizerRepository.delete(organizer);
    }

    /**
     * Delete watchlists from the database.
     * @param id the id of the user for delete.
     */
    public void deleteWatchlistsUser(Long id) {
        List<Watchlist> watchlists = this.watchlistRepository.findAll();

        for (Watchlist w : watchlists) {
            if (id == w.getAthlete().getId()){
                watchlistRepository.delete(w);
            }
        }
    }

    /**
     * Delete watchlists that are disabled from the database.
     */
    public void deleteWatchlistsDisabled() {
        List<Watchlist> watchlists = this.watchlistRepository.findAll();

        for (Watchlist w : watchlists) {
            if (!w.isEnabled()){
                watchlistRepository.delete(w);
            }
        }
    }

    /**
     * Delete watchlists for a tournament from the database.
     */
    public void deleteWatchlistsTournament(Long id) {
        List<Watchlist> watchlists = this.watchlistRepository.findAll();

        for (Watchlist w : watchlists) {
            if (w.getTournament().getId() == id){
                watchlistRepository.delete(w);
            }
        }
    }

    /**
     * Delete  inscriptions from the database.
     * @param id the id of the user for delete.
     */
    public void deleteInscriptionUser(Long id) {
        List<Inscription> inscriptions = this.inscriptionRepository.findAll();

        for (Inscription i : inscriptions) {
            if (id == i.getAthlete().getId()){
                inscriptionRepository.delete(i);
            }
        }
    }

    /**
     * Delete inscriptions that are disabled from the database.
     */
    public void deleteInscriptionsDisabled() {
        List<Inscription> inscriptions = this.inscriptionRepository.findAll();

        for (Inscription i : inscriptions) {
            if (!i.isEnabled()){
                inscriptionRepository.delete(i);
            }
        }
    }

    /**
     * Delete inscriptions for a tournament from the database.
     */
    public void deleteInscriptionsTournament(Long id) {
        List<Inscription> inscriptions = this.inscriptionRepository.findAll();

        for (Inscription i : inscriptions) {
            if (i.getTournament().getId() == id){
                inscriptionRepository.delete(i);
            }
        }
    }

    /**
     * Delete  tokens from the database.
     * @param id the id of the user for delete.
     */
    public void deleteTokensUser(Long id){
        List<Token> tokens = this.tokenRepository.findAll();

        for (Token t : tokens) {
            if (id == t.getUser().getId()){
                this.tokenRepository.delete(t);
            }
        }
    }

    /**
     * Delete tokens that are revoked from the database.
     */
    public void deleteTokensDisabled() {
        List<Token> tokens = this.tokenRepository.findAll();

        for (Token t : tokens) {
            if (t.isRevoked() && t.isExpired()){
                tokenRepository.delete(t);
            }
        }
    }

    /**
     * Delete a tournament from the database.
     * @param tournament the tournament to be deleted.
     */
    public void deleteTournament(Tournament tournament) {
        this.deleteWatchlistsTournament(tournament.getId());
        this.deleteInscriptionsTournament(tournament.getId());
        tournamentRepository.delete(tournament);
    }
    /**
     * Delete tournaments from the database.
     * @param id the id of the user for delete.
     */
    public void deleteTournamentsUser(Long id) {
        List<Tournament> tournaments = this.tournamentRepository.findAll();

        for (Tournament t : tournaments) {
            if (id == t.getOrganizer().getId()){
                this.deleteWatchlistsTournament(t.getId());
                this.deleteInscriptionsTournament(t.getId());
                tournamentRepository.delete(t);
            }
        }
    }

    /**
     * Delete tournaments that are disabled from the database.
     */
    public void deleteTournamentsDisabled() {
        List<Tournament> tournaments = this.tournamentRepository.findAll();

        for (Tournament t : tournaments) {
            if (!t.isEnabled()){
                tournamentRepository.delete(t);
            }
        }
    }
}
