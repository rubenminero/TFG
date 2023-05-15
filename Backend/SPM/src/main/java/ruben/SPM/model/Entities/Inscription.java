package ruben.SPM.model.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import ruben.SPM.model.DTO.Entities.InscriptionDTO;

@Entity
@Table(name= "inscription")
@AllArgsConstructor
@NoArgsConstructor
public class Inscription {
    private Long id;
    private Tournament tournament;
    private Athlete athlete;
    private Boolean enabled = Boolean.TRUE;



    public Inscription(InscriptionDTO inscriptionDTO,Tournament tournament, Athlete athlete) {
        this.id = inscriptionDTO.getId();
        this.tournament = tournament;
        this.athlete = athlete;
        this.enabled = inscriptionDTO.getEnabled();
    }

    // Getters of this model

    /**
     * Get the id of the inscription.
     * @return the id of the inscription.
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    /**
     * Get the tournament of the inscription.
     * @return the name of the inscription.
     */
    @JoinColumn(name = "id_tournament")
    @ManyToOne(targetEntity = Tournament.class)
    public Tournament getTournament() {
        return tournament;
    }

    /**
     * Get the athlete of the inscription.
     * @return the name of the athlete.
     */
    @JoinColumn(name = "id_user")
    @ManyToOne(targetEntity = Athlete.class)
    public Athlete getAthlete() {
        return athlete;
    }

    /**
     * Get the enabled of the inscription.
     * @return the enabled of the inscription.
     */
    @Column(name = "enabled")
    public Boolean isEnabled() { return enabled; }

    // Setters of this model

    /**
     * Set the id of the inscription, which cannot be repeated.
     * @param id the new id for this inscription.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Set the tournament of the inscription.
     * @param tournament the new tournament for this inscription.
     */
    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    /**
     * Set the athlete of the inscription.
     * @param athlete the new athlete for this inscription.
     */
    public void setAthlete(Athlete athlete) {
        this.athlete = athlete;
    }
    /**
     * Set the enabled of the inscription.
     * @param enabled the new enabled for this inscription.
     */
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }


}
