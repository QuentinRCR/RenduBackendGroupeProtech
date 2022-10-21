package com.docto.protechdoctolib.creneaux;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface CreneauxDAO extends JpaRepository<Creneaux, Long> {

    @Query("select c from Creneaux c where c.dateDebut>=:datee")  // (2)
    List<Creneaux> findCreneauxAfterDate(@Param("datee") LocalDate datee);

}
