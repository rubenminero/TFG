package ruben.SPM.model.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ruben.SPM.model.DTO.Entities.AthleteDTO;

import java.util.Date;

@Entity
@Table(name= "athletes")
@Inheritance(strategy = InheritanceType.JOINED)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
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
    @Email
    private String email;
    private Long id;
    private String phone_number;
    private Date disabled_at = null;
    private Boolean enabled = Boolean.TRUE;

    public Athlete( User user, String phone_number) {
        super(user.getId(), user.getUsername(),user.getPassword(),user.getFirst_name(),user.getLast_name(),user.getNif(),user.getEmail(),user.getRole(),user.getTokens());
        this.phone_number = phone_number;
    }

    public Athlete(AthleteDTO athletedto) {
        this.id = athletedto.getId();
        this.username = athletedto.getUsername();
        this.first_name = athletedto.getFirst_name();
        this.last_name = athletedto.getLast_name();
        this.password = athletedto.getPassword();
        this.nif = athletedto.getNif();
        this.email = athletedto.getEmail();
        this.enabled = athletedto.getEnabled();
        this.phone_number = athletedto.getPhone_number();
    }

    // Getters of this model

    /**
     * Get the id of the athlete.
     * @return the id of the athlete.
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @OneToOne(targetEntity = User.class, cascade = CascadeType.REMOVE, mappedBy = "id")
    public Long getId() {
        return id;
    }


    /**
     * Get the enabled of the athlete.
     *
     * @return the enabled of the athlete.
     */
    @Column(name = "enabled")
    public boolean isEnabled() { return enabled; }

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
