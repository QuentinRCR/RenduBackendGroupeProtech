package com.docto.protechdoctolib.creneaux;

import java.time.DayOfWeek;
import java.util.Date;
import java.util.List;

public class CreneauxDTO {

    private Long id;

    private Date dateDebut;

    private Date dateFin;

    private List<DayOfWeek> jours;

    private Date TimeDebut;

    private Date TimeFin;

    public CreneauxDTO() {
    }

    public CreneauxDTO(Creneaux creneaux) {
        this.id = creneaux.getId();
        this.dateDebut = creneaux.getDateDebut();
        this.dateFin = creneaux.getDateDebut();
        this.jours = creneaux.getJours();
        this.TimeDebut = creneaux.getTimeDebut();
        this.TimeFin = creneaux.getTimeFin();
    }

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
