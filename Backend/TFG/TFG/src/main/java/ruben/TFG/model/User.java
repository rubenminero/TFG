package ruben.TFG.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import ruben.TFG.controllers.DTO.UserDTO;

/**
 * User class, represents a user in the database.
 */
@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Long id;
    private String username;
    private String password;
    private String first_name;
    private String last_name;
    private String nif;
    private String email;


    public User(UserDTO userDTO) {
        this.id = userDTO.getId();
        this.username = userDTO.getUsername();
        this.first_name = userDTO.getFirst_name();
        this.last_name = userDTO.getLast_name();
        this.password = userDTO.getPassword();
        this.nif = userDTO.getNif();
        this.email = userDTO.getEmail();
    }
    // Getters of this model

    /**
     * Get the id of the person.
     * @return the id of the person.
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long getId() {
        return id;
    }
    /**
     * Get the username of the user.
     * @return the username of the user.
     */
    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    /**
     * Get the password of the user.
     * @return the password of the user.
     */
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    /**
     * Get the first name of the user.
     * @return the first name of the user.
     */
    @Column(name = "first_name")
    public String getFirst_name() {
        return first_name;
    }

    /**
     * Get the last name of the user.
     * @return the last name of the user.
     */
    @Column(name = "last_name")
    public String getLast_name() {
        return last_name;
    }

    /**
     * Get the NIF of the user.
     * @return the NIF of the user.
     */
    @Column(name = "NIF")
    public String getNif() {
        return nif;
    }

    /**
     * Get the email of the user.
     * @return the email of the user.
     */
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    // Setters of this model

    /**
     * Set the id of the person, which cannot be repeated.
     * @param id the new id for this person.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Set the username of the user, which cannot be repeated.
     * @param username the new username for this user.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Set the password of the user.
     * @param password the new password for this user.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Set the first name of the user.
     * @param first_name the new first name for this user.
     */
    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    /**
     * Set the last name of the user.
     * @param last_name the new last name for this user.
     */
    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    /**
     * Set the nif of the user.
     * @param nif the new nif for this user.
     */
    public void setNif(String nif) {
        this.nif = nif;
    }

    /**
     * Set the email of the user.
     * @param email the new email for this user.
     */
    public void setEmail(String email) {
        this.email = email;
    }


}
