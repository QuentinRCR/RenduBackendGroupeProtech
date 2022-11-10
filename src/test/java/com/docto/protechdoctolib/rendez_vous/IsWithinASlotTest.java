package com.docto.protechdoctolib.rendez_vous;

import com.docto.protechdoctolib.creneaux.CreneauxDAO;
import com.docto.protechdoctolib.creneaux.CreneauxDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class IsWithinASlotTest {

    @Autowired
    Rendez_vousDAO rendez_vousDAO;
    @Autowired
    CreneauxDAO creneauxDAO;

    @Test
    public void testCenterTimeAndDate(){
        Rendez_vousController rendez_vousController = new Rendez_vousController(creneauxDAO,rendez_vousDAO);
        LocalDateTime heure = LocalDateTime.of(2022,10,11,9,1);
        Duration duree= Duration.ofMinutes(30);
        CreneauxDTO test= rendez_vousController.isWithinASlot(heure,duree, LocalDate.of(2022,8,1));
        Assertions.assertThat(test.getId()).isEqualTo(2000L);
    }

    @Test
    public void testEdgeTime(){
        Rendez_vousController rendez_vousController = new Rendez_vousController(creneauxDAO,rendez_vousDAO);
        LocalDateTime heure = LocalDateTime.of(2022,10,11,9,0);
        Duration duree= Duration.ofMinutes(30);
        CreneauxDTO test= rendez_vousController.isWithinASlot(heure,duree,LocalDate.of(2022,8,1));
        Assertions.assertThat(test.getId()).isEqualTo(2000L);
    }

    @Test
    public void testEdgeDate(){
        Rendez_vousController rendez_vousController = new Rendez_vousController(creneauxDAO,rendez_vousDAO);
        LocalDateTime heure = LocalDateTime.of(2022,10,10,9,0);
        Duration duree= Duration.ofMinutes(30);
        CreneauxDTO test= rendez_vousController.isWithinASlot(heure,duree,LocalDate.of(2022,8,1));
        Assertions.assertThat(test.getId()).isEqualTo(2000L);
    }

}
