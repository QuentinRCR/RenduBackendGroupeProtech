package com.docto.protechdoctolib.creneaux;

import java.util.Date;

/**
 * Permet de pouvoir passer des dates en paramètre dans l'API
 */
public class DateDTO{

    private Date date;

    public DateDTO() {
    }

    public DateDTO(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
