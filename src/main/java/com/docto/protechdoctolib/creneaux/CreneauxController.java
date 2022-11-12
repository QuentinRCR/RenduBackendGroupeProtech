package com.docto.protechdoctolib.creneaux;

import com.docto.protechdoctolib.rendez_vous.Rendez_vous;
import com.docto.protechdoctolib.rendez_vous.Rendez_vousDAO;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.server.ResponseStatusException;

import java.util.stream.Collectors;

@CrossOrigin
@RestController // (1)
@RequestMapping("/api/creneaux") // (2)
@Transactional // (3)
public class CreneauxController {

    private final CreneauxDAO creneauxDAO;
    private final HeuresDebutFinDAO heuresDebutFinDAO;

    private final Rendez_vousDAO rendez_vousDAO;

    private static final Logger logger = LogManager.getLogger(CreneauxController.class);

    public CreneauxController(CreneauxDAO creneauxDAO, HeuresDebutFinDAO heuresDebutFinDAO, Rendez_vousDAO rendez_vousDAO) {
        this.creneauxDAO = creneauxDAO;
        this.heuresDebutFinDAO = heuresDebutFinDAO;
        this.rendez_vousDAO = rendez_vousDAO;
    }

    /**
     * Donne la liste de tous les créneaux
     *
     * @return une liste de tous les créneaux
     */
    @GetMapping
    public List<CreneauxDTO> findAll() {
        logger.info("la fonction findAll à été utilisé");
        return creneauxDAO.findAll().stream().map(CreneauxDTO::new).collect(Collectors.toList());
    }

    /**
     * Renvoit le créneau ayant pour id le paramètre
     *
     * @param id
     * @return creneau
     */
    @GetMapping(path = "/{id}")
    public CreneauxDTO findById(@PathVariable Long id) {
        logger.info("la fonction findById à été utilisé avec l'id " + id.toString());
        CreneauxDTO creneauId = creneauxDAO.findById(id).map(CreneauxDTO::new).orElse(null);
        if (creneauId == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "entity not found"
            );
        } else {
            return creneauId;
        }

    }

    /**
     * Supprime le créneau ayant pour id le paramètre
     *
     * @param id
     */
    @DeleteMapping(path = "/{id}")
    public void deleteParId(@PathVariable Long id) {
        logger.info("le créneau avec l'id " + id.toString() + " a été supprimé");
        try {
            creneauxDAO.deleteById(id);
            List<Rendez_vous> listRendezVous = rendez_vousDAO.findAllByIdCreneau(id);
            for (Rendez_vous rdv: listRendezVous){
                rendez_vousDAO.deleteById(rdv.getId());
            };
        } catch (EmptyResultDataAccessException e) {
            logger.warn("Le créneau à supprimé n'existe pas");
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "entity not found"
            );
        }
    }


    /**
     * Prend un dto de créneau en paramètre, crée ce creneau dans la db si son id est null et le modifie si son id existe déjà
     *
     * @param dto
     * @return le dto du créneau crée
     */
    @PostMapping("/create_or_modify") // (8)
    public CreneauxDTO create_or_modify(@RequestBody CreneauxDTO dto) {
        Creneaux creneaux = null;
        HeuresDebutFin heuresDebutFin1 = null;
        List listHeureDebutFin = new ArrayList<>();
        // On creation id is not defined
        if (dto.getId() == null) {
            creneaux = creneauxDAO.save(new Creneaux(null, dto.getDateDebut(), dto.getDateFin(), dto.getJours(), dto.getHeuresDebutFin().stream().map(HeuresDebutFin::new).collect(Collectors.toList()))); //la dernière partie sert uniquement à ce que swagger renvoie la bonne chose
            for (HeuresDebutFin heuresDebutFin : dto.getHeuresDebutFin().stream().map(HeuresDebutFin::new).collect(Collectors.toList())) { //On ajoute chaque element de la liste des heuresDebutFin à la table correspondante
                heuresDebutFin.setIdCreneaux(creneaux.getId());
                HeuresDebutFin saved = heuresDebutFinDAO.save(heuresDebutFin);
                listHeureDebutFin.add(saved);
            }

            logger.info("un nouveau créneau a été crée, il a pour id " + creneaux.getId().toString());
        } else {
            try {
                creneaux = creneauxDAO.getReferenceById(dto.getId());  // (9)
                creneaux.setDateDebut(dto.getDateDebut());
                creneaux.setDateFin(dto.getDateFin());
                creneaux.setJours(dto.getJours());
                creneaux.setHeuresDebutFin(dto.getHeuresDebutFin().stream().map(HeuresDebutFin::new).collect(Collectors.toList()));

                //On supprime et en recrée les créneaux pour avoir des problèmes de nombres de créneaux
                for (HeuresDebutFin ancienheuresDebutFin : heuresDebutFinDAO.findByIdCreneaux(dto.getId())) { //supprime tout les anciens créneaux
                    heuresDebutFinDAO.deleteById(ancienheuresDebutFin.getIdPlage());
                }

                for (HeuresDebutFin heuresDebutFin : dto.getHeuresDebutFin().stream().map(HeuresDebutFin::new).collect(Collectors.toList())) { //Crée les nouveaux créneaux
                    heuresDebutFin.setIdCreneaux(creneaux.getId());
                    HeuresDebutFin save = heuresDebutFinDAO.save(heuresDebutFin);
                    listHeureDebutFin.add(save);
                }

                logger.info("le créneau avec l'id " + creneaux.getId().toString() + " a été modifié");

            } catch (EntityNotFoundException e) {
                logger.error("id créneau non trouvé, pour en créer un nouveau, mettre null pour son id");
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "entity not found"
                );
            }
        }
        creneaux.setHeuresDebutFin(listHeureDebutFin);
        return new CreneauxDTO(creneaux);
    }
}