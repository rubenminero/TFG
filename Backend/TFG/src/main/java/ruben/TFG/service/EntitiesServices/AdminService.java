package ruben.TFG.service.EntitiesServices;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ruben.TFG.model.Entities.*;
import ruben.TFG.model.Whitelist.Role;
import ruben.TFG.repository.EntitiesRepositories.AdminRepository;
import ruben.TFG.repository.EntitiesRepositories.AthleteRepository;
import ruben.TFG.repository.EntitiesRepositories.OrganizerRepository;
import ruben.TFG.repository.EntitiesRepositories.UserRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Service for Admin class.
 */
@Service
@AllArgsConstructor
public class AdminService {
    private final AdminRepository adminRepository;

    private final OrganizerService organizerService;
    private final AthleteService athleteService;
    private final TournamentService tournamentService;
    private final Sports_typeService sportsTypeService;
    private final InscriptionService inscriptionService;
    private final WatchlistService watchlistService;

    private final PasswordEncoder passwordEncoder;

    /**
     * Enable or disable a user in the database.
     * @param id the id of the user to be changed.
     */
    public void changeStateOrganizer(Long id){
        organizerService.changeStateOrganizer(id);
    }

    /**
     * Gets all the organizers from the database.
     * Only for admins.
     * @return A list with all the organizers.
     */
    public List<Organizer> getAllOrganizers() {
        return organizerService.getAllOrganizers();
    }
    /**
     * Enable or disable a athlete in the database.
     * @param id the id of the athlete to be changed.
     */
    public void changeStateAthlete(Long id){
        athleteService.changeStateAthlete(id);
    }
    /**
     * Gets all the athletes from the database.
     * Only for admins
     * @return A list with all the athletes.
     */
    public List<Athlete> getAllAthletes() {
        return athleteService.getAllAthletes();
    }

    /**
     * Enable or disable a sport type in the database.
     * @param id the id of the sport type to be changed.
     */
    public void changeStateSport_type(Long id){
        sportsTypeService.changeStateSport_type(id);
    }

    /**
     * Gets all the sports types from the database.
     * Only for admins
     * @return A list with all the sports types.
     */
    public List<Sports_type> getAllSports_types() {
        return sportsTypeService.getAllSports_types();
    }


    /**
     * Enable or disable a tournament in the database.
     * @param id the id of the tournament to be changed.
     */
    public void changeStateTournament(Long id){
        tournamentService.changeStateTournament(id);
    }

    /**
     * Gets all the tournaments from the database.
     * Only for admins
     * @return A list with all the sports types.
     */
    public List<Tournament> getAllTournaments() {
        return tournamentService.getAllTournaments();
    }

    /**
     * Enable or disable an inscription in the database.
     * @param id the id of the inscription to be changed.
     */
    public void changeStateInscription(Long id){
        inscriptionService.changeStateInscription(id);
    }

    /**
     * Gets all the inscriptions from the database.
     * Only for admins
     * @return A list with all the inscriptions.
     */
    public List<Inscription> getAllInscriptions() {
        return inscriptionService.getAllInscriptions();
    }


    /**
     * Enable or disable a watchlist in the database.
     * @param id the id of the watchlist to be changed.
     */
    public void changeStateWatchlist(Long id){
        watchlistService.changeStateWatchlist(id);
    }

    /**
     * Gets all the watchlists from the database.
     * Only for admins
     * @return A list with all the watchlists.
     */
    public List<Watchlist> getAllWatchlists() {
        return watchlistService.getAllWatchlists();
    }

    /**
     * Recover an organizer from the database.
     * @param id the id of the organizer.
     * @return organizer the organizer with the id.
     */
    public Organizer getOrganizer(Long id){

        return organizerService.getOrganizer(id);
    }

    /**
     * Recover a athlete from the database
     * @param id the id of the athlete
     * @return athlete the athlete with the id
     */
    public Athlete getAthlete(Long id){

        return athleteService.getAthlete(id);
    }

    /**
     * Recover a tournament from the database.
     * @param id the id of the tournament.
     * @return tournament the tournament with the id.
     */
    public Tournament getTournament(Long id){

        return tournamentService.getTournament(id);
    }

    /**
     * Recover a tournament from the database.
     * @param id the id of the tournament.
     * @return tournament the tournament with the id.
     */
    public Sports_type getSport_type(Long id){

        return sportsTypeService.getSport_type(id);
    }

    /**
     * Recover an inscription from the database.
     * @param id the id of the inscription.
     * @return inscription the inscription with the id.
     */
    public Inscription getInscription(Long id){

        return inscriptionService.getInscription(id);
    }
    /**
     * Recover a watchlist from the database.
     * @param id the id of the watchlist.
     * @return watchlist the watchlist with the id.
     */
    public Watchlist getWatchlist(Long id){

        return watchlistService.getWatchList(id);
    }

    /**
     * Recover an admin from the database.
     * @param id the id of the admin.
     * @return admin the admin with the id.
     */
    public Admin getAdmin(Long id){
        return adminRepository.findById(id).orElse(null);
    }

    /**
     * Save an admin in the database.
     * @param admin the admin to be saved.
     */
    public Admin saveAdmin(Admin admin){
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        admin.setRole(Role.ADMIN);
        return adminRepository.save(admin);
    }

    /**
     * Disable or enable an admin in the database,depends on the last state.
     * @param id the id of the admin to be changed.
     */
    public void changeStateAdmin(Long id){
        Admin admin = this.getAdmin(id);
        if (admin.getValid_to() == null){
            admin.setValid_to(new Date());
        }else{
            admin.setValid_to(null);
        }
        adminRepository.save(admin);
    }

    /**
     * Gets all the admins from the database.
     * @return A list with all the admins.
     */
    public List<Admin> getAllAdmins() {

        return adminRepository.findAll();
    }
    /**
     * Gets all the enabled admins from the database.
     * @return A list with all the admins.
     */
    public List<Admin> getEnabledAdmins() {
        List<Admin> admins_enabled = new ArrayList<Admin>();
        List<Admin> admins = adminRepository.findAll();
        for (Admin a : admins) {
            if (a.getValid_to() != null){
                admins_enabled.add(a);
            }
        }
        return admins_enabled;
    }
    /**
     * Recover a admin from the database.
     * @param username the username of the admin.
     * @return admin the admin with the username.
     */
    public Admin getAdminByUsername(String username) {

        return adminRepository.findByUsername(username).orElse(null);
    }
}
