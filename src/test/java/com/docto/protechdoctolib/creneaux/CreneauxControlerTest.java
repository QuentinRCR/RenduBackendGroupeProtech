package com.docto.protechdoctolib.creneaux;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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
    public void testMilieuCreneau(){
    creneauxController= new CreneauxController(creneauxDAO,heuresDebutFinDAO);
    CreneauxDTO test= creneauxController.isWithinASlot(new GregorianCalendar(2022,10-1,14,9,0,0),30);
    Assertions.assertThat(test).isEqualTo(1000L);
    }

}
