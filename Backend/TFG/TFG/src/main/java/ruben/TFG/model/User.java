package ruben.TFG.model;


import jakarta.persistence.*;

<<<<<<< Updated upstream
@Entity
@Table(name= "users")
public class User {
=======
import java.util.Objects;

/**
 * User class, represents a user in the database.
 */
@Entity
@Table(name = "persons")
@Inheritance(strategy = InheritanceType.JOINED)
public class User {

>>>>>>> Stashed changes
    private Long id;
    private String username;
    private String password;
    private String first_name;
    private String last_name;
    private String nif;
    private String email;
<<<<<<< Updated upstream
    private Boolean enabled = Boolean.TRUE;
=======
>>>>>>> Stashed changes

    /**
     * Constructor without any parameters.
     */
    public User() {
    }

    /**
     * Constructor with all the parameters for a person.
     * @param username, the name that will be used in the app.
     * @param first_name, the first name of this user.
     * @param last_name, the last name of this user.
     * @param password, the password of the user.
     * @param nif, the nif of the user.
     * @param email, the email of the user.
     */
    public User(String username, String first_name, String last_name, String password, String nif, String email){
        this.username = username;
        this.first_name = first_name;
        this.last_name = last_name;
        this.password = password;
        this.nif = nif;
        this.email = email;
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

<<<<<<< Updated upstream
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

=======
>>>>>>> Stashed changes
    /**
     * Get the username of the person.
     * @return the username of the person.
     */
<<<<<<< Updated upstream
    @Column(name = "enabled")
    public Boolean isEnabled() { return enabled; }
=======
    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    /**
     * Get the password of the person.
     * @return the password of the person.
     */
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    /**
     * Get the first name of the person.
     * @return the first name of the person.
     */
    @Column(name = "first_name")
    public String getFirst_name() {
        return first_name;
    }

    /**
     * Get the last name of the person.
     * @return the last name of the person.
     */
    @Column(name = "last_name")
    public String getLast_name() {
        return last_name;
    }

    /**
     * Get the NIF of the person.
     * @return the NIF of the person.
     */
    @Column(name = "NIF")
    public String getNif() {
        return nif;
    }

    /**
     * Get the email of the person.
     * @return the email of the person.
     */
    @Column(name = "email")
    public String getEmail() {
        return email;
    }
>>>>>>> Stashed changes

    // Setters of this model

    /**
     * Set the id of the person, which cannot be repeated.
     * @param id the new id for this person.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
<<<<<<< Updated upstream
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

    /**
     * Set the enabled of the user.
     * @param enabled the new enabled for the user.
=======
     * Set the username of the person, which cannot be repeated.
     * @param username the new person for this user.
>>>>>>> Stashed changes
     */
    public void setUsername(String username) {
        this.username = username;
    }

<<<<<<< Updated upstream
=======
    /**
     * Set the password of the person.
     * @param password the new password for this person.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Set the first name of the person.
     * @param first_name the new first name for this person.
     */
    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    /**
     * Set the last name of the person.
     * @param last_name the new last name for this person.
     */
    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    /**
     * Set the nif of the person.
     * @param nif the new nif for this person.
     */
    public void setNif(String nif) {
        this.nif = nif;
    }

    /**
     * Set the email of the person.
     * @param email the new email for this person.
     */
    public void setEmail(String email) {
        this.email = email;
    }




>>>>>>> Stashed changes
}
