package ruben.TFG.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name= "athletes")
@Inheritance(strategy = InheritanceType.JOINED)
public class Athlete extends User {
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
    private Long id;
    private String phone_number;
    private Date disabled_at = null;
    private Boolean enabled = Boolean.TRUE;

    /**
     * Constructor without any parameters.
     */
    public Athlete(){

    }

    /**
     * Constructor with all the parameters for a user.
     * @param username, the name that will be used in the app.
     * @param first_name, the first name of this user.
     * @param last_name, the last name of this user.
     * @param password, the password of the user.
     * @param nif, the nif of the user.
     * @param email, the email of the user.
     * @param phone_number, the phone_number of the user.
     */
    public Athlete(String username, String first_name, String last_name, String password, String nif, String email, String phone_number){
        super(username, password, first_name, last_name, nif, email);
        this.phone_number = phone_number;

    }


    // Getters of this model

    /**
     * Get the id of the athlete.
     * @return the id of the athlete.
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @OneToOne(targetEntity = User.class, cascade = CascadeType.REMOVE, mappedBy = "id_athlete")
    public Long getId() {
        return id;
    }


    /**
     * Get the enabled of the athlete.
     * @return the enabled of the athlete.
     */
    @Column(name = "enabled")
    public Boolean isEnabled() { return enabled; }

    /**
     * Get the phone number of the athlete.
     * @return the phone number of the athlete.
     */
    @Column(name = "phone number")
    public String getPhone_number() {
        return phone_number;
    }
    /**
     * Get the disabled_at of the athlete.
     * @return the disabled_at of the athlete.
     */
    @Column(name = "disabled_at")
    public Date getDisabled_at() {
        return disabled_at;
    }

    // Setters of this model

    /**
     * Set the id of the athlete, which cannot be repeated.
     * @param id the new id for this athlete.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Set the enabled of the athlete.
     * @param enabled the new enabled for the athlete.
     */
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Set the phone number of the athlete.
     * @param phone_number the new phone number for the athlete.
     */
    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    /**
     * Set the disabled_at of the athlete.
     * @param disabled_at the new disabled_at for the athlete.
     */
    public void setDisabled_at(Date disabled_at) {
        this.disabled_at = disabled_at;
    }
}
