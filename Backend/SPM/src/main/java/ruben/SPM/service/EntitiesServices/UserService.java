package ruben.SPM.service.EntitiesServices;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ruben.SPM.model.Entities.User;
import ruben.SPM.repository.EntitiesRepositories.UserRepository;

import java.util.List;

/**
 * Service for user class.
 */
@Service
public class UserService {

    /**
     * Access for User data.
     */
    private final UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    /**
     * Recover a user from the database.
     * 
     * @param id the id of the user.
     * @return user the user with the id.
     */
    public User getUser(Long id) {

        return userRepository.findById(id).orElse(null);
    }

    /**
     * Save a user in the database.
     * 
     * @param user the user to be saved.
     */
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    /**
     * Update a user in the database.
     * 
     * @param user the user to be updated.
     */
    public User updateUser(User user) {
        userRepository.update(user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getFirst_name(),
                user.getLast_name(),
                user.getNif(),
                user.getEmail(),
                user.getRole());
        return user;
    }

    /**
     * Gets all the users from the database.
     * Only for admins.
     * 
     * @return A list with all the users.
     */
    public List<User> getAllUsers() {

        return userRepository.findAll();
    }

    /**
     * Recover a user from the database.
     * 
     * @param username the username of the user.
     * @return user the user with the username.
     */
    public User getUserByUsername(String username) {

        return userRepository.findByUsername(username).orElse(null);
    }

    public User isAuthorized() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = this.getUserByUsername(username);
        return user;
    }

    public boolean validUsername(String username) {
        User username_check = this.getUserByUsername(username);
        if (username_check != null) {
            return true;
        } else {
            return false;
        }
    }

}
