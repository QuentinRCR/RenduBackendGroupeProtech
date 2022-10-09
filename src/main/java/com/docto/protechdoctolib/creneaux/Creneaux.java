package com.docto.protechdoctolib.creneaux;

import javax.persistence.*;
import java.sql.Time;
import java.time.DayOfWeek;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@Entity
public class Creneaux {

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
     private Date dateDebut;

    /**
     * Dernier jour de la plage de temps
     */
    @Column
     private Date dateFin;

    /**
     * Liste des jours pour lequels on peut prendre des rendez-vous
     */
    @Column
    @ElementCollection(targetClass=String.class)
    private List<DayOfWeek> jours;

    /**
     * Heure de début de la plage de prise de rendez-vous pour la journéé
     */
    @Column
    private Date TimeDebut;

    /**
     * Heure de fin de la plage de prise de rendez-vous pour la journéé
     */
    @Column
    private Date TimeFin;
}
