package com.docto.protechdoctolib.creneaux;

import java.time.DayOfWeek;
import java.util.GregorianCalendar;
import java.util.List;

public class CreneauxDTO {

    private Long id;

    private GregorianCalendar dateDebut;

    private GregorianCalendar dateFin;

    private List<DayOfWeek> jours;

    private GregorianCalendar timeDebut;

    private GregorianCalendar timeFin;

    //Ce constructeur est necessaire à spring
    public CreneauxDTO() {
    }

    public CreneauxDTO(Creneaux creneaux) {
        this.id = creneaux.getId();
        this.dateDebut = creneaux.getDateDebut();
        this.dateFin = creneaux.getDateDebut();
        this.jours = creneaux.getJours();
        this.timeDebut = creneaux.getTimeDebut();
        this.timeFin = creneaux.getTimeFin();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public GregorianCalendar getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(GregorianCalendar dateDebut) {
        this.dateDebut = dateDebut;
    }

    public GregorianCalendar getDateFin() {
        return dateFin;
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
        return timeFin;
    }

    public void setTimeFin(GregorianCalendar timeFin) {
        this.timeFin = timeFin;
    }
}
