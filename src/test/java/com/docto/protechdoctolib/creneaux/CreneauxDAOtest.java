
package com.docto.protechdoctolib.creneaux;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class CreneauxDAOtest {

    @Autowired
    private CreneauxDAO creneauxDAO;

    /**
     * Teste si le créneaux 1 contient bien les bonnes informations
      */
    @Test
    public void shouldFindACreaneau() {
        Creneaux creneaux = creneauxDAO.getReferenceById(1L);
        List<DayOfWeek> listjours= List.of(DayOfWeek.MONDAY,DayOfWeek.FRIDAY);
        Creneaux creneauTest = new Creneaux(1L, new Date(2022-1900,10-1,16) , new Date(2022-1900,10-1,18), listjours, new Date(90,01-1,01,8,30,00), new Date(90,01-1,01,18,30,00));
        Assertions.assertThat(creneaux.getJours().get(0)).isEqualTo(creneauTest.getJours().get(0));
        Assertions.assertThat(creneaux.getDateDebut()).isEqualTo(creneauTest.getDateDebut());
        Assertions.assertThat(creneaux.getDateFin()).isEqualTo(creneauTest.getDateFin());
        Assertions.assertThat(creneaux.getTimeFin()).isEqualTo(creneauTest.getTimeFin());
        Assertions.assertThat(creneaux.getTimeDebut()).isEqualTo(creneauTest.getTimeDebut());
    }

    /**
     * Teste si la liste de créneaux retourné a bien une taille de 2
     */
    @Test
    public void shouldFind2Creneaux() {
        List<Creneaux> creneaux = creneauxDAO.findAll();
        Assertions.assertThat(creneaux.size()).isEqualTo(2);
    }

    /**
     * Teste si ça supprime bien le créneaux ayant pour id 1
     */
    @Test
    public void shouldDeleteCreneaux(){
        creneauxDAO.deleteById(1L);
        List<Creneaux> creneaux = creneauxDAO.findAll();
        Assertions.assertThat(creneaux.size()).isEqualTo(1);
        Assertions.assertThat(creneaux.get(0).getId()).isEqualTo(2);
    }

    /**
     * Teste si ça crée un nouveau créneau
     */
    @Test
    public void shouldCreateANewCreneau(){
        creneauxDAO.save(new Creneaux(3L,new Date(),new Date(),new ArrayList<DayOfWeek>(),new Date(),new Date()));
        Creneaux creneau = creneauxDAO.getReferenceById(3L);
        Assertions.assertThat(creneau).isInstanceOf(Creneaux.class);
    }

    /**
     * Teste si ça modifie bien le temps de fin du créneau ayant l'id 1
     */
    @Test
    public void shoudModifyCreneau1(){
        Creneaux creneaux = creneauxDAO.getReferenceById(1L);
        creneaux.setTimeFin(new Date(2695,12,30));
        Creneaux creneaux1 = creneauxDAO.getReferenceById(1L);
        Assertions.assertThat(creneaux1.getTimeFin()).isEqualTo(new Date(2695,12,30));
    }
}