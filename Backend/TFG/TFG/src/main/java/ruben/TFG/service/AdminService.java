package ruben.TFG.service;

import org.springframework.stereotype.Service;
import ruben.TFG.model.Organizer;
import ruben.TFG.model.User;
import ruben.TFG.repository.AdminRepository;
import ruben.TFG.service.UserService;

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

    public AdminService(AdminRepository adminRepository, OrganizerService organizerService, UserService userService) {

        this.adminRepository = adminRepository;
        this.userService = userService;
        this.organizerService = organizerService;
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


    }
