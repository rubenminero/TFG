package ruben.SPM.service.EntitiesServices;

import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class UserService {

    /**
     * Access for User data.
     */
    private final UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    private final DeleteService deleteService;

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
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
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

    /**
     * Recover a user from the database.
     *
     * @param email the email of the user.
     * @return user the user with the email.
     */
    public User getUserByEmail(String email) {

        return userRepository.findByEmail(email).orElse(null);
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

    public boolean validEmail(String email) {
        User email_check = this.getUserByEmail(email);
        if (email_check != null) {
            return true;
        } else {
            return false;
        }
    }

}
