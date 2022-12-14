
package com.docto.protechdoctolib.creneaux;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class CreneauxDAOtest {

    @Autowired
    private CreneauxDAO creneauxDAO;

    @Autowired
    private  HeuresDebutFinDAO heuresDebutFinDAO;

    /**
     * Teste si le créneau -1 contient bien les bonnes informations
      */
    @Test
    public void shouldFindACreaneau() {
        Creneaux creneaux =creneauxDAO.getReferenceById(-1L); //creneauxDAO.getReferenceById(2000L);
        List<DayOfWeek> listjours= List.of(DayOfWeek.FRIDAY);
        LocalDate dateDebut = LocalDate.of(2022,11,22);
        LocalDate dateFin = LocalDate.of(2022,11,30);
        HeuresDebutFin heuresDebutFin1 = new HeuresDebutFin(1001L,1000L, LocalTime.of(8,0),LocalTime.of(12,0));
        HeuresDebutFin heuresDebutFin2 = new HeuresDebutFin(1002L,1000L, LocalTime.of(14,0),LocalTime.of(18,0));
        List<HeuresDebutFin> listHeureDebutFin = List.of(heuresDebutFin1,heuresDebutFin2); //Recreate the same slot
        Creneaux creneauTest = new Creneaux(-1L,dateDebut,dateFin,listjours,listHeureDebutFin);
        Assertions.assertThat(creneaux.getDateDebut()).isEqualTo(creneauTest.getDateDebut());
        Assertions.assertThat(creneaux.getJours().get(0)).isEqualTo(creneauTest.getJours().get(0));
        Assertions.assertThat(creneaux.getDateFin()).isEqualTo(creneauTest.getDateFin());
        Assertions.assertThat(creneaux.getHeuresDebutFin().get(0).getTempsDebut()).isEqualTo(creneauTest.getHeuresDebutFin().get(0).getTempsDebut());
        Assertions.assertThat(creneaux.getHeuresDebutFin().get(0).getTempsFin()).isEqualTo(creneauTest.getHeuresDebutFin().get(0).getTempsFin());
    }

    /**
     * Test qu'on récupère bien tous les créneaux
     */
    @Test
    public void shouldFind2Creneaux() {
        List<Creneaux> creneaux = creneauxDAO.findAll();
        Assertions.assertThat(creneaux.size()).isEqualTo(2);
    }

    /**
     * Test si ça supprime bien le créneau ayant pour id -1
     */
    @Test
    public void shouldDeleteCreneaux(){
        creneauxDAO.deleteById(-1L);
        List<Creneaux> creneaux = creneauxDAO.findAll();
        Assertions.assertThat(creneaux.size()).isEqualTo(1); //Check that the remaining size is 1
        Assertions.assertThat(creneaux.get(0).getId()).isEqualTo(-2); //And that the remaining slot as id -2
    }

    /**
     * Test si ça modifie bien le temps de fin du créneau ayant l'id -1
     */
    @Test
    public void shoudModifyCreneau1(){
        Creneaux creneaux = creneauxDAO.getReferenceById(-1L);
        creneaux.setDateDebut(LocalDate.of(2695,12,30));
        Creneaux creneaux1 = creneauxDAO.getReferenceById(-1L);
        Assertions.assertThat(creneaux1.getDateDebut()).isEqualTo(LocalDate.of(2695,12,30));
    }

    /**
     * Test que la fonction findCreneauxAfterDate trouve bien uniquement les créneaux finissant après une certaine date
     */
    @Test
    public void findCreneauxAfterDate(){
        List<Creneaux> creneaux = creneauxDAO.findCreneauxAfterDate(LocalDate.of(2022,10,11));
        Assertions.assertThat(creneaux.get(0).getDateFin()).isAfter(LocalDate.of(2022,10,11));
    }
}