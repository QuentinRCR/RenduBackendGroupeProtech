package com.docto.protechdoctolib.creneaux;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.GregorianCalendar;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class CreneauxControlerTest {

    CreneauxController creneauxController;

     @Autowired
    CreneauxDAO creneauxDAO;
    @Autowired
     HeuresDebutFinDAO heuresDebutFinDAO;

    @Test
    public void testCenterTimeAndDate(){
        creneauxController= new CreneauxController(creneauxDAO,heuresDebutFinDAO);
        LocalDateTime heure = LocalDateTime.of(2022,10,11,9,1);
        Duration duree= Duration.ofMinutes(30);
        CreneauxDTO test= creneauxController.isWithinASlot(heure,duree);
        Assertions.assertThat(test.getId()).isEqualTo(2000L);
    }

    @Test
    public void testEdgeTime(){
        creneauxController= new CreneauxController(creneauxDAO,heuresDebutFinDAO);
        LocalDateTime heure = LocalDateTime.of(2022,10,11,9,0);
        Duration duree= Duration.ofMinutes(30);
        CreneauxDTO test= creneauxController.isWithinASlot(heure,duree);
        Assertions.assertThat(test.getId()).isEqualTo(2000L);
    }

    @Test
    public void testEdgeDate(){
        creneauxController= new CreneauxController(creneauxDAO,heuresDebutFinDAO);
        LocalDateTime heure = LocalDateTime.of(2022,10,10,9,0);
        Duration duree= Duration.ofMinutes(30);
        CreneauxDTO test= creneauxController.isWithinASlot(heure,duree);
        Assertions.assertThat(test.getId()).isEqualTo(2000L);
    }

}
