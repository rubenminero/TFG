package ruben.TFG.model;

import jakarta.persistence.*;

@Entity
@Table(name= "inscriptions")
public class Inscription {
    private Long id;
    private Tournament tournament;
    private User user;
    private Boolean enabled = Boolean.TRUE;

    /**
     * Constructor without any parameters.
     */
    public Inscription(){

    }

    /**
     * Constructor with all the parameters for an inscription.
     * @param tournament, the tournament of the inscription.
     * @param user, the use of the inscription.
     */
    public Inscription(Tournament tournament, User user){
        this.tournament = tournament;
        this.user = user;
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
    @Column(name = "tournament")
    public Tournament getTournament() {
        return tournament;
    }

    /**
     * Get the user of the inscription.
     * @return the name of the user.
     */
    @Column(name = "user")
    public User getUser() {
        return user;
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
     * Set the user of the inscription.
     * @param user the new us for this inscription.
     */
    public void setUser(User user) {
        this.user = user;
    }
    /**
     * Set the enabled of the sport type.
     * @param enabled the new enabled for this sport type.
     */
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }


}
