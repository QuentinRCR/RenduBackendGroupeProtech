package com.docto.protechdoctolib.creneaux;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
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

    private GregorianCalendar tempsDebut;

    private GregorianCalendar tempsFin;

    public Long getIdCreneaux() {
        return idCreneaux;
    }

    public void setIdCreneaux(Long idCreneaux) {
        this.idCreneaux = idCreneaux;
    }

    public GregorianCalendar getTempsDebut() {
        return tempsDebut;
    }

    public void setTempsDebut(GregorianCalendar tempsDebut) {
        this.tempsDebut = tempsDebut;
    }

    public GregorianCalendar getTempsFin() {
        return tempsFin;
    }

    public void setTempsFin(GregorianCalendar tempsFin) {
        this.tempsFin = tempsFin;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
