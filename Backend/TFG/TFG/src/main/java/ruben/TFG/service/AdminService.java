package ruben.TFG.service;

import org.springframework.stereotype.Service;
import ruben.TFG.model.Organizer;
import ruben.TFG.model.Sports_type;
import ruben.TFG.model.Tournament;
import ruben.TFG.model.User;
import ruben.TFG.repository.AdminRepository;

import java.util.List;

/**
 * Service for Admin class.
 */
@Service
public class AdminService {

    /**
     * Access for Admin data.
     */
    private final AdminRepository adminRepository;

    private final OrganizerService organizerService;
    private final UserService userService;
    private final TournamentService tournamentService;
    private final Sports_typeService sportsTypeService;

    public AdminService(AdminRepository adminRepository, OrganizerService organizerService, UserService userService, TournamentService tournamentService, Sports_typeService sportsTypeService) {

        this.adminRepository = adminRepository;
        this.userService = userService;
        this.organizerService = organizerService;
        this.tournamentService = tournamentService;
        this.sportsTypeService = sportsTypeService;
    }

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
     * Enable or disable a user in the database.
     * @param id the id of the user to be changed.
     */
    public void changeStateUser(Long id){
        userService.changeStateUser(id);
    }
    /**
     * Gets all the users from the database.
     * Only for admins
     * @return A list with all the users.
     */
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    /**
     * Enable or disable a tournament in the database.
     * @param id the id of the tournament to be changed.
     */
    public void changeStateTournament(Long id){
        tournamentService.changeStateTournament(id);
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
     * Enable or disable a sport type in the database.
     * @param id the id of the sport type to be changed.
     */
    public void changeStateSport_type(Long id){
        sportsTypeService.changeStateSport_type(id);
    }

    /**
     * Save a sport type in the database.
     * @param sport_type the sport type to be saved.
     * @return sport_type the sport type that have been saved.
     */
    public Sports_type saveSport_type(Sports_type sport_type){
        return sportsTypeService.saveSport_type(sport_type);
    }
    /**
     * Gets all the tournaments from the database.
     * Only for admins
     * @return A list with all the tournaments.
     */
    public List<Tournament> getAllTournaments() {
        return tournamentService.getAllTournaments();
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
     * Recover a user from the database
     * @param id the id of the user
     * @return user the user with the id
     */
    public User getUser(Long id){

        return userService.getUser(id);
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

    }
