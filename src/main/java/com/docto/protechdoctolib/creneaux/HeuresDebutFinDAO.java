package com.docto.protechdoctolib.creneaux;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HeuresDebutFinDAO extends JpaRepository<HeuresDebutFin, Long> {

    /**
     * Permet de récupérer toutes les plages de temps qui appartiennent à un créneau
     * @param idCreneaux
     * @return Liste des plages de temps qui appartiennent à un créneau
     */
    @Query("select c from HeuresDebutFin c where c.idCreneaux=:idCreneaux")  // (2)
    List<HeuresDebutFin> findByIdCreneaux(@Param("idCreneaux") Long idCreneaux);
}
