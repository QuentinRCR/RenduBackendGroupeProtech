package com.docto.protechdoctolib.creneaux;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.util.Date;
import java.util.List;

@Entity
public class Creneaux {

    public Creneaux() {
    }

    public Creneaux(Long id, Date dateDebut, Date dateFin, List<DayOfWeek> jours, Date timeDebut, Date timeFin) {
        this.id = id;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.jours = jours;
        TimeDebut = timeDebut;
        TimeFin = timeFin;
    }

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
    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm:ss")
     private Date dateDebut;

    /**
     * Dernier jour de la plage de temps
     */
    @Column
    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm:ss")
     private Date dateFin;

    /**
     * Liste des jours pour lequels on peut prendre des rendez-vous
     */

    @ElementCollection(targetClass=DayOfWeek.class)
    @Enumerated(EnumType.STRING)
    @Column
    private List<DayOfWeek> jours;

    /**
     * Heure de début de la plage de prise de rendez-vous pour la journéé
     */
    @Column
    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    private Date TimeDebut;

    /**
     * Heure de fin de la plage de prise de rendez-vous pour la journéé
     */
    @Column
    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm:ss")
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
