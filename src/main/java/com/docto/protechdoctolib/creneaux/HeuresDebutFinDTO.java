package com.docto.protechdoctolib.creneaux;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import java.time.LocalTime;
import java.util.GregorianCalendar;

public class HeuresDebutFinDTO {

    public HeuresDebutFinDTO() {
    }

    public HeuresDebutFinDTO(HeuresDebutFin heuresDebutFin) {
        this.id=heuresDebutFin.getId();
        this.idCreneaux = heuresDebutFin.getIdCreneaux();
        this.tempsDebut = heuresDebutFin.getTempsDebut();
        this.tempsFin = heuresDebutFin.getTempsFin();
    }

    private  Long id;

    private  Long idCreneaux;

    private LocalTime tempsDebut;

    private LocalTime tempsFin;

    public Long getIdCreneaux() {
        return idCreneaux;
    }

    public void setIdCreneaux(Long idCreneaux) {
        this.idCreneaux = idCreneaux;
    }

    public LocalTime getTempsDebut() {
        return tempsDebut;
    }

    public void setTempsDebut(LocalTime tempsDebut) {
        this.tempsDebut = tempsDebut;
    }

    public LocalTime getTempsFin() {
        return tempsFin;
    }

    public void setTempsFin(LocalTime tempsFin) {
        this.tempsFin = tempsFin;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
