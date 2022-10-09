package com.docto.protechdoctolib.creneaux;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.util.Date;
import java.util.List;

@Entity
public class Creneaux {

    /**
     * Id du créneau
     */
     @Id
     @GeneratedValue
     private Long id;

    /**
     * Premier jour de la plage de temps
     */
    @Column
     private Date dateDebut;

    /**
     * Dernier jour de la plage de temps
     */
    @Column
     private Date dateFin;

    /**
     * Liste des jours pour lequels on peut prendre des rendez-vous
     */
    @Column
    @ElementCollection(targetClass=String.class)
    private List<DayOfWeek> jours;

    /**
     * Heure de début de la plage de prise de rendez-vous pour la journéé
     */
    @Column
    private Date TimeDebut;

    /**
     * Heure de fin de la plage de prise de rendez-vous pour la journéé
     */
    @Column
    private Date TimeFin;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public List<DayOfWeek> getJours() {
        return jours;
    }

    public void setJours(List<DayOfWeek> jours) {
        this.jours = jours;
    }

    public Date getTimeDebut() {
        return TimeDebut;
    }

    public void setTimeDebut(Date timeDebut) {
        TimeDebut = timeDebut;
    }

    public Date getTimeFin() {
        return TimeFin;
    }

    public void setTimeFin(Date timeFin) {
        TimeFin = timeFin;
    }
}
