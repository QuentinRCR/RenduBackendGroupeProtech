package com.docto.protechdoctolib.creneaux;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;

public class CreneauxDTO {

    private Long id;

    private GregorianCalendar dateDebut;

    private GregorianCalendar dateFin;

    private List<DayOfWeek> jours;

    private List<HeuresDebutFinDTO> heuresDebutFin;

    //Ce constructeur est necessaire à spring
    public CreneauxDTO() {
    }

    public CreneauxDTO(Creneaux creneaux) {
        this.id = creneaux.getId();
        this.dateDebut = creneaux.getDateDebut();
        this.dateFin = creneaux.getDateFin();
        this.jours = creneaux.getJours();
        if (creneaux.getHeuresDebutFin()!=null) {
            this.heuresDebutFin = creneaux.getHeuresDebutFin().stream().map(HeuresDebutFinDTO::new).collect(Collectors.toList());
        }
        else{
            this.heuresDebutFin= new ArrayList<HeuresDebutFinDTO>();
        }
        //this.heuresDebutFin=creneaux.getHeuresDebutFin().stream().map(HeuresDebutFinDTO::new).collect(Collectors.toList());
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

    public List<HeuresDebutFinDTO> getHeuresDebutFin() {
        return heuresDebutFin;
    }

    public void setHeuresDebutFin(List<HeuresDebutFinDTO> heuresDebutFin) {
        this.heuresDebutFin = heuresDebutFin;
    }
}
