package ruben.TFG.model.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name= "admins")
@Inheritance(strategy = InheritanceType.JOINED)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Admin extends User {
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
    private Date valid_from;
    private Date valid_to;


    public Admin( User user, Date valid_from) {
        super(user.getId(), user.getUsername(),user.getPassword(),user.getFirst_name(),user.getLast_name(),user.getNif(),user.getEmail(),user.getRole(),user.getTokens());
        this.valid_from = valid_from;
        this.valid_to = null;
    }

    // Getters of this model

    /**
     * Get the id of the admin.
     * @return the id of the admin.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @OneToOne(targetEntity = User.class, cascade = CascadeType.REMOVE, mappedBy = "id")
    public Long getId() {
        return id;
    }

    /**
     * Get the valid_from of the admin.
     * @return the valid_from of the admin.
     */
    @Column(name = "valid_from")
    public Date getValidFrom() {
        return valid_from;
    }

    /**
     * Get the valid_to of the admin.
     * @return the valid_toC of the admin.
     */
    public Date getValid_to() {
        return valid_to;
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
     * Set the valid_from of the admin.
     * @param valid_from the new valid_from of the admin.
     */
    public void setValidFrom(Date valid_from) {
         this.valid_from = valid_from;
    }

    /**
     * Set the valid_to of the admin.
     * @param valid_to the new valid_to of the admin.
     */
    public void setValid_to(Date valid_to) {
        this.valid_to = valid_to;
    }
}
