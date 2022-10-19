package com.docto.protechdoctolib.creneaux;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.util.GregorianCalendar;
import java.util.GregorianCalendar;
import java.util.List;

@Entity
public class Creneaux {

    public Creneaux() {
    }

    public Creneaux(Long id, GregorianCalendar dateDebut, GregorianCalendar dateFin, List<DayOfWeek> jours, GregorianCalendar timeDebut, GregorianCalendar timeFin) {
        this.id = id;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.jours = jours;
        this.timeDebut = timeDebut;
        this.timeFin = timeFin;
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
    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm:ss[.SSS][.SS][.S]")
     private GregorianCalendar dateDebut;

    /**
     * Dernier jour de la plage de temps
     */
    @Column
    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm:ss[.SSS][.SS][.S]")
     private GregorianCalendar dateFin;

    /**
     * Liste des jours pour lequels on peut prendre des rendez-vous
     */

    @ElementCollection(targetClass=DayOfWeek.class)
    @Enumerated(EnumType.STRING)
    @Column
    private List<DayOfWeek> jours;

    /**
     * Heure de début de la plage de prise de rendez-vous pour la journée
     */
    @Column
    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm:ss[.SSS][.SS][.S]")
    private GregorianCalendar timeDebut;

    /**
     * Heure de fin de la plage de prise de rendez-vous pour la journée
     */
    @Column
    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm:ss[.SSS][.SS][.S]")
    private GregorianCalendar timeFin;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public GregorianCalendar getDateDebut() {
        return this.dateDebut;
    }

    public void setDateDebut(GregorianCalendar dateDebut) {
        this.dateDebut = dateDebut;
    }

    public GregorianCalendar getDateFin() {
        return this.dateFin;
    }

    public void setDateFin(GregorianCalendar dateFin) {
        this.dateFin = dateFin;
    }

    public List<DayOfWeek> getJours() {
        return jours;
    }

    public void setJours(List<DayOfWeek> jours) {
        this.jours = jours;
    }

    public GregorianCalendar getTimeDebut() {
        return this.timeDebut;
    }

    public void setTimeDebut(GregorianCalendar timeDebut) {
        this.timeDebut = timeDebut;
    }

    public GregorianCalendar getTimeFin() {
        return this.timeFin;
    }

    public void setTimeFin(GregorianCalendar timeFin) {
        this.timeFin = timeFin;
    }
}
