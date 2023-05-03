package ruben.TFG.model;

import jakarta.persistence.*;

@Entity
@Table(name= "admins")
public class Admin {
    private Long id;
    private String username;
    private String password;
    private String first_name;
    private String last_name;
    private String nif;
    private String email;

    /**
     * Constructor without any parameters.
     */
    public Admin(){

    }

    /**
     * Constructor with all the parameters for a user.
     * @param username, the name that will be used in the app.
     * @param first_name, the first name of this user.
     * @param last_name, the last name of this user.
     * @param password, the password of the user.
     * @param nif, the nif of the user.
     * @param email, the email of the user.
     */
    public Admin(String username, String first_name, String last_name, String password, String nif, String email){
        this.username = username;
        this.first_name = first_name;
        this.last_name = last_name;
        this.password = password;
        this.nif = nif;
        this.email = email;
    }


    // Getters of this model

    /**
     * Get the id of the admin.
     * @return the id of the admin.
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    /**
     * Get the username of the admin.
     * @return the username of the admin.
     */
    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    /**
     * Get the password of the admin.
     * @return the password of the admin.
     */
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    /**
     * Get the first name of the admin.
     * @return the first name of the admin.
     */
    @Column(name = "first_name")
    public String getFirst_name() {
        return first_name;
    }

    /**
     * Get the last name of the admin.
     * @return the last name of the admin.
     */
    @Column(name = "last_name")
    public String getLast_name() {
        return last_name;
    }

    /**
     * Get the NIF of the admin.
     * @return the NIF of the admin.
     */
    @Column(name = "NIF")
    public String getNif() {
        return nif;
    }

    /**
     * Get the email of the admin.
     * @return the email of the admin.
     */
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    // Setters of this model

    /**
     * Set the id of the admin, which cannot be repeated.
     * @param id the new id for this admin.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Set the username of the admin.
     * @param username the new username for this admin.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Set the password of the admin.
     * @param password the new password for this admin.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Set the first name of the admin.
     * @param first_name the new first name for this admin.
     */
    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    /**
     * Set the last name of the admin.
     * @param last_name the new last name for this admin.
     */
    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    /**
     * Set the nif of the admin.
     * @param nif the new nif for this admin.
     */
    public void setNif(String nif) {
        this.nif = nif;
    }

    /**
     * Set the email of the admin.
     * @param email the new email for this admin.
     */
    public void setEmail(String email) {
        this.email = email;
    }


}
