package ruben.TFG.model.DTO;
import java.util.List;
import java.util.stream.Collectors;

import ruben.TFG.model.Entities.User;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DTO to manage the user data.
 */
@Data
@AllArgsConstructor
public class UserDTO {

    private Long id;
    private String username;
    private String first_name;
    private String last_name;
    private String password;
    private String nif;
    private String email;

    public UserDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.first_name = user.getFirst_name();
        this.last_name = user.getLast_name();
        this.password = user.getPassword();
        this.nif = user.getNif();
        this.email = user.getEmail();
    }

    public static UserDTO fromUser(User user) {
        return new UserDTO(user);
    }

    public static List<UserDTO> fromUsers(List<User> users) {
        return users.stream().map(UserDTO::fromUser).collect(Collectors.toList());
    }

    public static User toUser(UserDTO user) {
        return new User(user);
    }
}