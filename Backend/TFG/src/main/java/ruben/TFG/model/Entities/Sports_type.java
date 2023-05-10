package ruben.TFG.model.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import ruben.TFG.model.DTO.Entities.Sports_typeDTO;

@Entity
@Table(name= "sports_types")
@AllArgsConstructor
@NoArgsConstructor
public class Sports_type {
    private Long id;
    private String name;
    private Boolean enabled = Boolean.TRUE;

    public Sports_type(Sports_typeDTO sports_typeDTO) {
        this.id = sports_typeDTO.getId();
        this.name = sports_typeDTO.getName();
        this.enabled = sports_typeDTO.getEnabled();
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
