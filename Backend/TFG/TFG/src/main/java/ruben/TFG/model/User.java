package ruben.TFG.model;

import jakarta.persistence.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name= "users")
@SQLDelete(sql = "UPDATE user SET enabled = false WHERE id=?")
@Where(clause = "enabled=true")
public class User {
    private Long id;
    private String username;
    private String password;
    private String first_name;
    private String last_name;
    private String nif;
    private String mail;
    private Boolean enabled = Boolean.TRUE;

    /**
     * Constructor without any parameters
     */
    public User(){

    }

    /**
     * Constructor with all the parameters for a user
     * @param username, the name that will be used in the app
     * @param first_name, the first name of this user
     * @param last_name, the last name of this user
     * @param password, the password of the user
     * @param nif, the nif of the user
     * @param mail, the email of the user
     */
    public User(String username, String first_name, String last_name, String password, String nif, String mail){
        this.username = username;
        this.first_name = first_name;
        this.last_name = last_name;
        this.password = password;
        this.nif = nif;
        this.mail = mail;
    }


    // Getters of this model

    /**
     * Get the id of the user
     * @return the id of the user.
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    /**
     * Get the username of the user
     * @return the username of the user.
     */
    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    /**
     * Get the password of the user
     * @return the password of the user.
     */
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    /**
     * Get the first name of the user
     * @return the first name of the user.
     */
    @Column(name = "first_name")
    public String getFirst_name() {
        return first_name;
    }

    /**
     * Get the last name of the user
     * @return the last name of the user.
     */
    @Column(name = "last_name")
    public String getLast_name() {
        return last_name;
    }

    /**
     * Get the NIF of the user
     * @return the NIF of the user.
     */
    @Column(name = "NIF")
    public String getNif() {
        return nif;
    }

    /**
     * Get the mail of the user
     * @return the mail of the user.
     */
    @Column(name = "mail")
    public String getEmail() {
        return mail;
    }

    /**
     * Get the enabled of the user
     * @return the enabled of the user.
     */
    @Column(name = "enabled")
    public Boolean isEnabled() { return enabled; }

    // Setters of this model

    /**
     * Set the id of the user, which cannot be repeated.
     * @param id the new id for this user
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Set the username of the user, which cannot be repeated.
     * @param username the new username for this user
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Set the password of the user.
     * @param password the new password for this user
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Set the first name of the user.
     * @param first_name the new first name for this user
     */
    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    /**
     * Set the last name of the user.
     * @param last_name the new last name for this user
     */
    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    /**
     * Set the nif of the user.
     * @param nif the new nif for this user
     */
    public void setNif(String nif) {
        this.nif = nif;
    }

    /**
     * Set the mail of the user.
     * @param mail the new mail for this user
     */
    public void setEmail(String mail) {
        this.mail = mail;
    }

    /**
     * Set the enabled of the user
     * @param enabled the new enabled for the user.
     */
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

}
