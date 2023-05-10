package ruben.TFG.model.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import ruben.TFG.model.DTO.Entities.TournamentDTO;

@Entity
@Table(name= "tournaments")
@AllArgsConstructor
public class Tournament {
    private Long id;
    private String name;
    private boolean inscription;
    private String location;
    private String address;
    private String description;
    private Boolean enabled = Boolean.TRUE;
    private Organizer organizer;
    private Sports_type sport_type;

    public Tournament(){

    }
    public Tournament(TournamentDTO tournament, Organizer organizer, Sports_type sportsType) {
        this.id = tournament.getId();
        this.name = tournament.getName();
        this.location = tournament.getLocation();
        this.address = tournament.getAddress();
        this.description = tournament.getDescription();
        this.enabled = tournament.getEnabled();
        this.inscription = tournament.getInscription();
        this.organizer = organizer;
        this.sport_type = sportsType;
    }

    // Getters of this model

    /**
     * Get the id of the tournament.
     * @return the id of the tournament.
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    /**
     * Get the name of the tournament.
     * @return the name of the tournament.
     */
    @Column(name = "name")
    public String getName() {
        return name;
    }

    /**
     * Get the location of the tournament.
     * @return the location of the tournament.
     */
    @Column(name = "location")
    public String getLocation() {
        return location;
    }

    /**
     * Get the address of the tournament.
     * @return the address of the tournament.
     */
    @Column(name = "address")
    public String getAddress() {
        return address;
    }

    /**
     * Get the location of the tournament.
     * @return the location of the tournament.
     */
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    /**
     * Get the enabled of the tournament.
     * @return the enabled of the tournament.
     */
    @Column(name = "enabled")
    public Boolean isEnabled() { return enabled; }

    /**
     * Get the inscription of the tournament.
     * @return the inscription of the tournament.
     */
    @Column(name = "inscription")
    public Boolean allows_Inscriptions() { return inscription; }

    /**
     * Get the id of the organizer.
     * @return the id of the organizer.
     */
    @JoinColumn(name = "id_organizers")
    @ManyToOne(targetEntity = Organizer.class)
    public Organizer getOrganizer() {
        return organizer;
    }

    /**
     * Get the id of the sport type.
     * @return the id of the sport type.
     */
    @JoinColumn(name = "id_sports_type")
    @ManyToOne(targetEntity = Sports_type.class)
    public Sports_type getSport_type() {
        return sport_type;
    }

    // Setters of this model

    /**
     * Set the id of the tournament, which cannot be repeated.
     * @param id the new id for this tournament.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Set the name of the tournament.
     * @param name the new name for this tournament.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Set the location of the tournament.
     * @param location the new location for this tournament.
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Set the address of the tournament.
     * @param address the new address  for this tournament.
     */
    public void setAddress(String address ) {
        this.address  = address ;
    }

    /**
     * Set the description of the tournament.
     * @param description the description for this tournament.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Set the inscription of the tournament.
     * @param inscription the new inscription for this tournament.
     */
    public void setInscription(Boolean inscription) {
        this.inscription = inscription;
    }

    /**
     * Set the enabled of the tournament.
     * @param enabled the new enabled for this tournament.
     */
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Set the organizer of the tournament.
     * @param organizer the new organizer for this tournament.
     */
    public void setOrganizer(Organizer organizer) {
        this.organizer = organizer;
    }

    /**
     * Set the sport type of the tournament.
     * @param sport_type the new sport_type for this tournament.
     */
    public void setSport_type(Sports_type sport_type) {
        this.sport_type = sport_type;
    }

}
