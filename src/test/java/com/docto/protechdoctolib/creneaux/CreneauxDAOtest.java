
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

}/*
    @Test
    public void shouldDeleteWindowsRoom() {
        Room room = roomDao.getReferenceById(-10L);
        List<Long> roomIds = room.getHeaters().stream().map(Heater::getId).collect(Collectors.toList());
        Assertions.assertThat(roomIds.size()).isEqualTo(2);

        heaterDao.deleteByRoomId(-10L);
        List<Heater> result = heaterDao.findAllById(roomIds);
        Assertions.assertThat(result).isEmpty();

    }
}
*/