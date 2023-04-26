package ruben.TFG.model;

import jakarta.persistence.*;

@Entity
@Table(name= "sports_types")
public class Sports_type {
    private Long id;
    private String name;
    private Boolean enabled = Boolean.TRUE;

    /**
     * Constructor without any parameters.
     */
    public Sports_type(){

    }

    /**
     * Constructor with all the parameters for a tournament.
     * @param name, the name of the tournament.
     */
    public Sports_type(String name){
        this.name = name;
    }

    // Getters of this model

    /**
     * Get the id of the sport type.
     * @return the id of the sport type.
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    /**
     * Get the name of the sport type.
     * @return the name of the sport type.
     */
    @Column(name = "name")
    public String getName() {
        return name;
    }

    /**
     * Get the enabled of the sport type.
     * @return the enabled of the sport type.
     */
    @Column(name = "enabled")
    public Boolean isEnabled() { return enabled; }

    // Setters of this model

    /**
     * Set the id of the sport type, which cannot be repeated.
     * @param id the new id for this sport type.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Set the name of the sport type.
     * @param name the new name for this sport type.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Set the enabled of the sport type.
     * @param enabled the new enabled for this sport type.
     */
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }


}
