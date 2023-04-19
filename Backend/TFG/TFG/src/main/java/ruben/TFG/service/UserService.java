package ruben.TFG.service;

import org.springframework.stereotype.Service;
import ruben.TFG.model.User;
import ruben.TFG.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Service for user class
 */
@Service
public class UserService {

    /**
     * Access for User data
     */
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    /**
     * Recover a user from the database
     * @param id the id of the user
     * @return user the user with the id
     */
    public User getUser(Long id){

        return userRepository.findById(id).orElse(null);
    }

    /**
     * Save a user in the database
     * @param user the user to be saved
     */
    public User saveUser(User user){
        return userRepository.save(user);
    }

    /**
     * Disable a user in the database
     * @param id the id of the user to be disabled
     */
    public void disableUser(Long id){
        User user = this.getUser(id);
        user.setEnabled(false);
        userRepository.save(user);
    }

    /**
     * Gets all the users from the database.
     * Only for admins
     * @return A list with all the users.
     */
    public List<User> getAllUsers() {

        return userRepository.findAll();
    }
    public List<User> getEnabledUsers() {
        List<User> users_enabled = new ArrayList<User>();
        List<User> users = userRepository.findAll();
        for (User u : users) {
            if (u.isEnabled()){
                users_enabled.add(u);
            }
        }
        return users_enabled;
    }

    public User getUserByUsername(String username) {

        return userRepository.findByUsername(username).orElse(null);
    }

    }
