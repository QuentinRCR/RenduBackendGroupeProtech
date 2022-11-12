package com.docto.protechdoctolib.rendez_vous;

import com.docto.protechdoctolib.creneaux.Creneaux;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface Rendez_vousDAO extends JpaRepository<Rendez_vous, Long> {

    @Query("select c from Rendez_vous c where c.idUser=:idClient")  // (2)
    List<Rendez_vous> findAllByIdUser(@Param("idClient") Long idClient);

    @Query("select c from Rendez_vous c where c.idCreneau=:idCreneau")  // (2)
    List<Rendez_vous> findAllByIdCreneau(@Param("idCreneau") Long idCreneau);

}
