package com.docto.protechdoctolib.creneaux;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.GregorianCalendar;

/**
 * Definit une plage de temps dans un journée dans un créneau
 */
@Entity
public class HeuresDebutFin {

    public HeuresDebutFin() {
    }

    public HeuresDebutFin(Long id, Long idCreneaux, GregorianCalendar tempsDebut, GregorianCalendar tempsFin) {
        this.id = id;
        this.idCreneaux = idCreneaux;
        this.tempsDebut = tempsDebut;
        this.tempsFin = tempsFin;
    }

    public  HeuresDebutFin(HeuresDebutFinDTO heuresDebutFinDTO){
        this.id=heuresDebutFinDTO.getId();
        this.tempsDebut=heuresDebutFinDTO.getTempsDebut();
        this.tempsFin=heuresDebutFinDTO.getTempsFin();
        this.idCreneaux= heuresDebutFinDTO.getIdCreneaux();
    }

    @Id
    @GeneratedValue
    private Long id;


    private  Long idCreneaux;

    @Column
    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm:ss[.SSS][.SS][.S]")
    private GregorianCalendar tempsDebut;

    @Column
    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm:ss[.SSS][.SS][.S]")
    private GregorianCalendar tempsFin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public GregorianCalendar getTempsDebut() {
        return tempsDebut;
    }

    public void setTempsDebut(GregorianCalendar tempsDebut) {
        this.tempsDebut = tempsDebut;
    }

    public Long getIdCreneaux() {
        return idCreneaux;
    }

    public void setIdCreneaux(Long idCreneaux) {
        this.idCreneaux = idCreneaux;
    }

    public GregorianCalendar getTempsFin() {
        return tempsFin;
    }

    public void setTempsFin(GregorianCalendar tempsFin) {
        this.tempsFin = tempsFin;
    }
}
