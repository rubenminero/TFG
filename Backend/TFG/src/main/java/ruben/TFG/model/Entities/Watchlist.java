package ruben.TFG.model.Entities;

import jakarta.persistence.*;

@Entity
@Table(name= "inscription")
public class Watchlist {
    private Long id;
    private Tournament tournament;
    private Athlete athlete;
    private Boolean enabled = Boolean.TRUE;

    /**
     * Constructor without any parameters.
     */
    public Watchlist(){

    }

    /**
     * Constructor with all the parameters for a watchlist.
     * @param tournament, the tournament of the watchlist.
     * @param athlete, the use of the watchlist.
     */
    public Watchlist(Tournament tournament, Athlete athlete){
        this.tournament = tournament;
        this.athlete = athlete;
    }

    // Getters of this model

    /**
     * Get the id of the watchlist.
     * @return the id of the watchlist.
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    /**
     * Get the tournament of the watchlist.
     * @return the name of the tournament.
     */
    @JoinColumn(name = "id_tournament")
    @ManyToOne(targetEntity = Tournament.class)
    public Tournament getTournament() {
        return tournament;
    }

    /**
     * Get the athlete of the watchlist.
     * @return the name of the athlete.
     */
    @JoinColumn(name = "id_user")
    @ManyToOne(targetEntity = Athlete.class)
    public Athlete getUser() {
        return athlete;
    }

    /**
     * Get the enabled of the watchlist.
     * @return the enabled of the watchlist.
     */
    @Column(name = "enabled")
    public Boolean isEnabled() { return enabled; }

    // Setters of this model

    /**
     * Set the id of the watchlist, which cannot be repeated.
     * @param id the new id for this watchlist.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Set the tournament of the watchlist.
     * @param tournament the new tournament for this watchlist.
     */
    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    /**
     * Set the athlete of the watchlist.
     * @param athlete the new athlete for this watchlist.
     */
    public void setUser(Athlete athlete) {
        this.athlete = athlete;
    }
    /**
     * Set the enabled of the watchlist.
     * @param enabled the new enabled for this watchlist.
     */
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }


}
