package ruben.TFG.model;

import jakarta.persistence.*;

@Entity
@Table(name= "organisers")
<<<<<<< Updated upstream
public class Organizer {
=======
@Inheritance(strategy = InheritanceType.JOINED)
public class Organizer extends User {

    @Transient
    private String username;
    @Transient
    private String password;
    @Transient
    private String first_name;
    @Transient
    private String last_name;
    @Transient
    private String nif;
    @Transient
    private String email;
>>>>>>> Stashed changes
    private Long id;
    private String username;
    private String password;
    private String nif;
    private String email;
    private Boolean enabled = Boolean.TRUE;

    /**
     * Constructor without any parameters
     */
    public Organizer(){

    }

    /**
     * Constructor with all the parameters for a organiser.
     * @param username, the name of the company that will be used in the app.
     * @param password, the password of the organiser.
     * @param nif, the nif of the organiser.
     * @param email, the email of the organiser.
     */
    public Organizer(String username, String password, String nif, String email){
        this.username = username;
        this.password = password;
        this.nif = nif;
        this.email = email;
    }


    // Getters of this model

    /**
     * Get the id of the organiser.
     * @return the id of the organiser.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
<<<<<<< Updated upstream
    @Column(name = "id")
=======
    @OneToOne(targetEntity = User.class, cascade = CascadeType.REMOVE, mappedBy = "id")
>>>>>>> Stashed changes
    public Long getId() {
        return id;
    }

    /**
     * Get the name of the company of the organiser.
     * @return the name of the company of the organiser.
     */
    @Column(name = "company_name")
    public String getUsername() {
        return username;
    }

    /**
     * Get the password of the organiser.
     * @return the password of the organiser.
     */
    @Column(name = "password")
    public String getPassword() {
        return password;
    }
    /**
     * Get the NIF of the organiser.
     * @return the NIF of the organiser.
     */
    @Column(name = "NIF")
    public String getNif() {
        return nif;
    }

    /**
     * Get the email of the organiser.
     * @return the email of the organiser.
     */
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    /**
     * Get the enabled of the organiser.
     * @return the enabled of the organiser.
     */
    @Column(name = "enabled")
    public Boolean isEnabled() { return enabled; }

    // Setters of this model

    /**
     * Set the id of the organiser, which cannot be repeated.
     * @param id the new id for this organiser.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Set the company name of the organiser.
     * @param username the new name of the company for this organiser.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Set the password of the organiser.
     * @param password the new password for this organiser.
     */
    public void setPassword(String password) {
        this.password = password;
    }


    /**
     * Set the nif of the organiser.
     * @param nif the new nif for this organiser.
     */
    public void setNif(String nif) {
        this.nif = nif;
    }

    /**
     * Set the email of the organiser.
     * @param email the new mail for this organiser.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Set the enabled of the organiser.
     * @param enabled the new enabled for the organiser.
     */
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

}
