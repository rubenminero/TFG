package ruben.SPM.service.EntitiesServices;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ruben.SPM.model.DTO.Front.DeleteDisabledSummaryFrontDTO;
import ruben.SPM.model.Entities.*;
import ruben.SPM.model.JWT_Token.Token;
import ruben.SPM.repository.EntitiesRepositories.*;
import ruben.SPM.repository.TokenRepository.TokenRepository;
import ruben.SPM.service.Auth.TokenService;

import java.util.List;

@Service
@AllArgsConstructor
public class DeleteService {

    private final AthleteService athleteService;
    private final InscriptionService inscriptionService;
    private final OrganizerService organizerService;
    private final Sports_typeService sportsTypeService;
    private final TournamentService tournamentService;
    private final WatchlistService  watchlistService;
    private final TokenService tokenService;
    private final UserRepository userRepository;


    /**
     * Delete all entities disabled from the database.
     */
    public void deleteDisableds() {
        this.deleteWatchlistsDisabled();
        this.deleteInscriptionsDisabled();
        this.deleteTournamentsDisabled();
        this.deleteEventsDisabled();
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
                .events_disabled(this.countEventsDisabled())
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
                this.deleteTokensUser(u.getId());
                this.deleteWatchlistsUser(u.getId());
                this.deleteInscriptionUser(u.getId());
                this.deleteTournamentsUser(u.getId());
                this.deleteEventsUser(u.getId());
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
        this.athleteService.deleteAthlete(athlete);
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
        this.deleteEventsUser(organizer.getId());
        this.organizerService.deleteOrganizer(organizer);
    }

    /**
     * Delete watchlists from the database.
     * @param id the id of the user for delete.
     */
    public void deleteWatchlistsUser(Long id) {
        List<Watchlist> watchlists = this.watchlistService.getAllWatchlists();

        for (Watchlist w : watchlists) {
            if (id.equals(w.getAthlete().getId())){
                this.watchlistService.deleteWatchlist(w.getId());
            }
        }
    }

    /**
     * Delete a watchlist from the database.
     * @param id the id of the watchlist for delete.
     */
    public void deleteWatchlist(Long id) {
        Watchlist watchlist = this.watchlistService.getWatchList(id);
        if (watchlist != null){
            this.watchlistService.deleteWatchlist(watchlist.getId());
        }
    }

    /**
     * Delete watchlists that are disabled from the database.
     */
    public void deleteWatchlistsDisabled() {
        List<Watchlist> watchlists = this.watchlistService.getAllWatchlists();

        for (Watchlist w : watchlists) {
            if (!w.isEnabled()){
                this.watchlistService.deleteWatchlist(w.getId());
            }
        }
    }

    /**
     * Count how many watchlists are disabled in the database.
     */
    public int countWatchlistsDisabled() {
        int count = 0;
        List<Watchlist> watchlists = this.watchlistService.getAllWatchlists();

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
        List<Watchlist> watchlists = this.watchlistService.getAllWatchlists();

        for (Watchlist w : watchlists) {
            if (w.getTournament().getId().equals(id)){
                this.watchlistService.deleteWatchlist(w.getId());
            }
        }
    }

    /**
     * Delete  inscriptions from the database.
     * @param id the id of the user for delete.
     */
    public void deleteInscriptionUser(Long id) {
        List<Inscription> inscriptions = this.inscriptionService.getAllInscriptions();

        for (Inscription i : inscriptions) {
            if (id.equals(i.getAthlete().getId())){
                this.inscriptionService.deleteInscription(i);
            }
        }
    }

    /**
     * Delete inscriptions that are disabled from the database.
     */
    public void deleteInscriptionsDisabled() {
        List<Inscription> inscriptions = this.inscriptionService.getAllInscriptions();

        for (Inscription i : inscriptions) {
            if (!i.isEnabled()){
                this.inscriptionService.deleteInscription(i.getId());
            }
        }
    }

    /**
     * Delete a inscription from the database.
     * @param id the id of the inscription for delete.
     */
    public void deleteInscription(Long id) {
        Inscription inscription = this.inscriptionService.getInscription(id);
        if (inscription != null){
            this.inscriptionService.deleteInscription(inscription.getId());
        }
    }

    /**
     * Count how many inscriptions are disabled in the database.
     */
    public int countInscriptionsDisabled() {
        int count = 0;
        List<Inscription> inscriptions = this.inscriptionService.getAllInscriptions();

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
        List<Inscription> inscriptions = this.inscriptionService.getAllInscriptions();

        for (Inscription i : inscriptions) {
            if (i.getTournament().getId().equals(id)){
                this.inscriptionService.deleteInscription(i.getId());
            }
        }
    }

    /**
     * Delete  tokens from the database.
     * @param id the id of the user for delete.
     */
    public void deleteTokensUser(Long id){
        List<Token> tokens = this.tokenService.getAllTokens();

        for (Token t : tokens) {
            if (id.equals(t.getUser().getId())){
                this.tokenService.deleteToken(t);
            }
        }
    }

    /**
     * Delete tokens that are revoked from the database.
     */
    public void deleteTokensDisabled() {
        List<Token> tokens = this.tokenService.getAllTokens();

        for (Token t : tokens) {
            if (t.isRevoked() && t.isExpired()){
                this.tokenService.deleteToken(t);
            }
        }
    }

    /**
     * Delete a inscription from the database.
     * @param id the id of the inscription for delete.
     */
    public void deleteToken(Integer id) {
        Token token = this.tokenService.getToken(id);
        if (token != null){
            this.tokenService.deleteToken(token);
        }
    }


    /**
     * Count how many tokens are disabled in the database.
     */
    public int countTokensDisabled() {
        int count = 0;
        List<Token> tokens = this.tokenService.getAllTokens();

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
        Tournament tournament = this.tournamentService.getTournament(id);

        if (tournament != null) {
            this.deleteWatchlistsTournament(tournament.getId());
            this.deleteInscriptionsTournament(tournament.getId());
            this.tournamentService.deleteTournament(tournament.getId());
        }
    }
    /**
     * Delete tournaments from the database.
     * @param id the id of the user for delete.
     */
    public void deleteTournamentsUser(Long id) {
        List<Tournament> tournaments = this.tournamentService.getAllTournaments();

        for (Tournament t : tournaments) {
            if (id.equals(t.getOrganizer().getId())){
                this.deleteWatchlistsTournament(t.getId());
                this.deleteInscriptionsTournament(t.getId());
                this.tournamentService.deleteTournament(t.getId());
            }
        }
    }

    /**
     * Delete tournaments that are disabled from the database.
     */
    public void deleteTournamentsDisabled() {
        List<Tournament> tournaments = this.tournamentService.getAllTournaments();

        for (Tournament t : tournaments) {
            if (!t.isEnabled()){
                this.tournamentService.deleteTournament(t.getId());
            }
        }
    }

    /**
     * Count how many tournaments are disabled in the database.
     */
    public int countTournamentsDisabled() {
        int count = 0;
        List<Tournament> tournaments = this.tournamentService.getAllTournaments();

        for (Tournament t : tournaments) {
            if (!t.isEnabled()){
                count += 1;
            }
        }
        return count;
    }
    /**
     * Delete events from the database.
     * @param id the id of the user for delete.
     */
    public void deleteEventsUser(Long id) {
        List<Tournament> tournaments = this.tournamentService.getAllEvents();

        for (Tournament t : tournaments) {
            if (id.equals(t.getOrganizer().getId())){
                this.deleteWatchlistsTournament(t.getId());
                this.deleteInscriptionsTournament(t.getId());
                this.tournamentService.deleteTournament(t.getId());
            }
        }
    }

    /**
     * Delete tournaments that are disabled from the database.
     */
    public void deleteEventsDisabled() {
        List<Tournament> tournaments = this.tournamentService.getAllEvents();

        for (Tournament t : tournaments) {
            if (!t.isEnabled()){
                this.tournamentService.deleteTournament(t.getId());
            }
        }
    }

    /**
     * Count how many tournaments are disabled in the database.
     */
    public int countEventsDisabled() {
        int count = 0;
        List<Tournament> tournaments = this.tournamentService.getAllEvents();

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
        List<Sports_type> sports_types = this.sportsTypeService.getAllSports_types();

        for (Sports_type s : sports_types) {
            if (!s.isEnabled()){
                this.sportsTypeService.deleteSport_type(s.getId());
            }
        }
    }

    /**
     * Delete a sport type from the database.
     * @param id the id of the sport type to be deleted.
     */
    public void deleteSportsType(Long id) {
        Sports_type sportsType = this.sportsTypeService.getSport_type(id);
        if (sportsType != null){
            this.sportsTypeService.deleteSport_type(sportsType.getId());
        }
    }

    /**
     * Count how many sports types are disabled in the database.
     */
    public int countSportsTypesDisabled() {
        int count = 0;
        List<Sports_type> sports_types = this.sportsTypeService.getAllSports_types();

        for (Sports_type s : sports_types) {
            if (!s.isEnabled()){
                count += 1;
            }
        }
        return count;
    }
}
