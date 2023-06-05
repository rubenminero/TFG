package ruben.SPM.model.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ruben.SPM.model.DTO.Entities.OrganizerDTO;
import ruben.SPM.model.Whitelist.Role;

import java.util.Date;

@Entity
@Table(name= "organisers")
@Inheritance(strategy = InheritanceType.JOINED)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
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
    @Email
    private String email;
    @Transient
    private Role role;
    private Long id;
    private String company;
    private String address;
    private Boolean enabled = Boolean.TRUE;
    private Date disabled_at = null;

    public Organizer(User user, String company, String address) {
        super(user.getId(), user.getUsername(),user.getPassword(),user.getFirst_name(),user.getLast_name(),user.getNif(),user.getEmail(),user.getRole(),user.getTokens());
        this.company = company;
        this.address = address;
        this.enabled = false;
    }


    public Organizer(OrganizerDTO organizerDTO) {
        this.id = organizerDTO.getId();
        this.username = organizerDTO.getUsername();
        this.first_name = organizerDTO.getFirst_name();
        this.last_name = organizerDTO.getLast_name();
        this.password = organizerDTO.getPassword();
        this.nif = organizerDTO.getNif();
        this.email = organizerDTO.getEmail();
        this.company = organizerDTO.getCompany();
        this.address = organizerDTO.getAddress();
        this.disabled_at = organizerDTO.getDisabled_at();
        this.enabled = false;
    }


    // Getters of this model

    /**
     * Get the id of the organiser.
     * @return the id of the organiser.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @OneToOne(targetEntity = User.class, cascade = CascadeType.REMOVE, mappedBy = "id")
    public Long getId() {
        return id;
    }

    /**
     * Get the name of the company of the organiser.
     * @return the name of the company of the organiser.
     */
    @Column(name = "company_name")
    public String getCompany() {
        return company;
    }

    /**
     * Get the address of the company of the organiser.
     * @return the address of the company of the organiser.
     */
    @Column(name = "address")
    public String getAddress() {
        return address;
    }

    /**
     * Get the disabled_at of the organiser.
     * @return the disabled_atof the organiser.
     */
    @Column(name = "disabled_at")
    public Date getDisabled_at() {
        return disabled_at;
    }

    /**
     * Get the enabled of the organiser.
     *
     * @return the enabled of the organiser.
     */
    @Column(name = "enabled")
    public boolean isEnabled() { return enabled; }

    // Setters of this model

    /**
     * Set the id of the organiser, which cannot be repeated.
     * @param id the new id for this organiser.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Set the name of the company of the organiser.
     * @param company_name the new name of the company of the organiser.
     */
    public void setCompany(String company_name) {
        this.company = company_name;
    }

    /**
     * Set the address of the company of the organiser.
     * @param address the new address of the company of the organiser.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Set the disabled_at of the company of the organiser.
     * @param disabled_at the new disabled_at of the company of the organiser.
     */
    public void setDisabled_at(Date disabled_at) {
        this.disabled_at = disabled_at;
    }

    /**
     * Set the enabled of the organiser.
     * @param enabled the new enabled for the organiser.
     */
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

}
