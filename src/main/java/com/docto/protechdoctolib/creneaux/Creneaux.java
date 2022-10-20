package com.docto.protechdoctolib.creneaux;


import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.util.GregorianCalendar;
import java.util.List;

@Entity
public class Creneaux {

    public Creneaux() {
    }

    public Creneaux(Long id, GregorianCalendar dateDebut, GregorianCalendar dateFin, List<DayOfWeek> jours, List<HeuresDebutFin> heuresDebutFin) {
        this.id = id;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.jours = jours;
        this.heuresDebutFin = heuresDebutFin;

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

    @OneToMany(mappedBy = "idCreneaux")
    private List<HeuresDebutFin> heuresDebutFin;


    public List<HeuresDebutFin> getHeuresDebutFin() {
        return heuresDebutFin;
    }

    public void setHeuresDebutFin(List<HeuresDebutFin> heuresDebutFin) {
        this.heuresDebutFin = heuresDebutFin;
    }

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
}
