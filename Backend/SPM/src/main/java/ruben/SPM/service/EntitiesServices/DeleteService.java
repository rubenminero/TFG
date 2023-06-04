package ruben.SPM.service.EntitiesServices;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ruben.SPM.model.DTO.Front.DeleteDisabledSummaryFrontDTO;
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
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;


    /**
     * Delete all entities disabled from the database.
     */
    public void deleteDisableds() {
        this.deleteWatchlistsDisabled();
        this.deleteInscriptionsDisabled();
        this.deleteTournamentsDisabled();
        this.deleteTokensDisabled();
        this.deleteSportsTypesDisabled();
        this.deleteUsersDisabled();
    }

    /**
     * Count all entities disabled from the database.
     */
    public DeleteDisabledSummaryFrontDTO countDisableds() {
        var disableds = DeleteDisabledSummaryFrontDTO.builder()
                .watchlists_disabled(this.countWatchlistsDisabled())
                .inscriptions_disabled(this.countInscriptionsDisabled())
                .tournaments_disabled(this.countTournamentsDisabled())
                .tokens_disabled(this.countTokensDisabled())
                .sports_types_disabled(this.countSportsTypesDisabled())
                .users_disabled(this.countUsersDisabled())
                .build();
        return disableds;
    }




    /**
     * Count how many users are disabled in the database.
     */
    public int countUsersDisabled() {
        int count = 0;
        List<User> users = this.userRepository.findAll();

        for (User u : users) {
            if (!u.isEnabled()){
                count += 1;
            }
        }
        return count;
    }

    /**
     * Delete users that are disabled from the database.
     */
    public void deleteUsersDisabled() {
        List<User> users = this.userRepository.findAll();

        for (User u : users) {
            if (!u.isEnabled()){
                userRepository.delete(u);
            }
        }
    }

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
     * Delete a watchlist from the database.
     * @param id the id of the watchlist for delete.
     */
    public void deleteWatchlist(Long id) {
        Watchlist watchlist = this.watchlistRepository.findById(id).orElse(null);
        if (watchlist != null){
            this.watchlistRepository.delete(watchlist);
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
     * Count how many watchlists are disabled in the database.
     */
    public int countWatchlistsDisabled() {
        int count = 0;
        List<Watchlist> watchlists = this.watchlistRepository.findAll();

        for (Watchlist w : watchlists) {
            if (!w.isEnabled()){
                count += 1;
            }
        }
        return count;
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
     * Delete a inscription from the database.
     * @param id the id of the inscription for delete.
     */
    public void deleteInscription(Long id) {
        Inscription inscription = this.inscriptionRepository.findById(id).orElse(null);
        if (inscription != null){
            this.inscriptionRepository.delete(inscription);
        }
    }

    /**
     * Count how many inscriptions are disabled in the database.
     */
    public int countInscriptionsDisabled() {
        int count = 0;
        List<Inscription> inscriptions = this.inscriptionRepository.findAll();

        for (Inscription i : inscriptions) {
            if (!i.isEnabled()){
                count += 1;
            }
        }
        return count;
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
     * Delete a inscription from the database.
     * @param id the id of the inscription for delete.
     */
    public void deleteToken(Integer id) {
        Token token = this.tokenRepository.findById(id).orElse(null);
        if (token != null){
            this.tokenRepository.delete(token);
        }
    }


    /**
     * Count how many tokens are disabled in the database.
     */
    public int countTokensDisabled() {
        int count = 0;
        List<Token> tokens = this.tokenRepository.findAll();

        for (Token t : tokens) {
            if (t.isExpired() && t.isRevoked()){
                count += 1;
            }
        }
        return count;
    }

    /**
     * Delete a tournament from the database.
     * @param id the id of the tournament to be deleted.
     */
    public void deleteTournament(Long id) {
        Tournament tournament = this.tournamentRepository.findById(id).orElse(null);

        if (tournament != null) {
            this.deleteWatchlistsTournament(tournament.getId());
            this.deleteInscriptionsTournament(tournament.getId());
            tournamentRepository.delete(tournament);
        }
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

    /**
     * Count how many tournaments are disabled in the database.
     */
    public int countTournamentsDisabled() {
        int count = 0;
        List<Tournament> tournaments = this.tournamentRepository.findAll();

        for (Tournament t : tournaments) {
            if (!t.isEnabled()){
                count += 1;
            }
        }
        return count;
    }

    /**
     * Delete sports types that are disabled from the database.
     */
    public void deleteSportsTypesDisabled() {
        List<Sports_type> sports_types = this.sportsTypeRepository.findAll();

        for (Sports_type s : sports_types) {
            if (!s.isEnabled()){
                sportsTypeRepository.delete(s);
            }
        }
    }

    /**
     * Delete a sport type from the database.
     * @param id the id of the sport type to be deleted.
     */
    public void deleteSportsType(Long id) {
        Sports_type sportsType = this.sportsTypeRepository.findById(id).orElse(null);
        if (sportsType != null){
            this.sportsTypeRepository.delete(sportsType);
        }
    }

    /**
     * Count how many sports types are disabled in the database.
     */
    public int countSportsTypesDisabled() {
        int count = 0;
        List<Sports_type> sports_types = this.sportsTypeRepository.findAll();

        for (Sports_type s : sports_types) {
            if (!s.isEnabled()){
                count += 1;
            }
        }
        return count;
    }
}
