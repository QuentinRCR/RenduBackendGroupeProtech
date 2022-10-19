package com.docto.protechdoctolib.creneaux;

import java.util.GregorianCalendar;

/**
 * Permet de pouvoir passer des dates en paramètre dans l'API
 */
public class DateDTO{

    private GregorianCalendar date;

    public DateDTO() {
    }

    public DateDTO(GregorianCalendar date) {
        this.date = date;
    }

    public GregorianCalendar getDate() {
        return date;
    }

    public void setDate(GregorianCalendar date) {
        this.date = date;
    }
}
