package com.docto.protechdoctolib.creneaux;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.server.ResponseStatusException;

import java.util.stream.Collectors;

@RestController // (1)
@RequestMapping("/api/creneaux") // (2)
@Transactional // (3)
public class CreneauxController {

    private final CreneauxDAO creneauxDAO;
    private final HeuresDebutFinDAO heuresDebutFinDAO;

    private static final Logger logger = LogManager.getLogger(CreneauxController.class);

    public CreneauxController(CreneauxDAO creneauxDAO, HeuresDebutFinDAO heuresDebutFinDAO) {
        this.creneauxDAO = creneauxDAO;
        this.heuresDebutFinDAO = heuresDebutFinDAO;
    }

    /**
     * Donne la liste de tous les créneaux
     * @return une liste de tous les créneaux
     */
    @GetMapping
    public List<CreneauxDTO> findAll(){
        logger.info("la fonction findAll à été utilisé");
        return creneauxDAO.findAll().stream().map(CreneauxDTO::new).collect(Collectors.toList());
    }

    /**
     * Renvoit le créneau ayant pour id le paramètre
     * @param id
     * @return creneau
     */
    @GetMapping(path = "/{id}")
    public CreneauxDTO findById(@PathVariable Long id) {
        logger.info( "la fonction findById à été utilisé avec l'id "+id.toString());
        CreneauxDTO creneauId= creneauxDAO.findById(id).map(CreneauxDTO::new).orElse(null);
        if (creneauId==null){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "entity not found"
            );
        }
        else{
            return creneauId;
        }

    }

    /**
     * Supprime le créneau ayant pour id le paramètre
     * @param id
     */
    @DeleteMapping(path = "/{id}")
    public void deleteParId(@PathVariable Long id) {
        logger.info( "le créneau avec l'id "+id.toString()+" a été supprimé");
        try{
            creneauxDAO.deleteById(id);
        }
        catch (EmptyResultDataAccessException e){
            logger.warn("Le créneau à supprimé n'existe pas");
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "entity not found"
            );
        }
    }


    /**
     * Prend un dto de créneau en paramètre, crée ce creneau dans la db si son id est null et le modifie si son id existe déjà
     * @param dto
     * @return le dto du créneau crée
     */
    @PostMapping("/create_or_modify") // (8)
    public CreneauxDTO create_or_modify(@RequestBody CreneauxDTO dto) {
        Creneaux creneaux = null;
        HeuresDebutFin heuresDebutFin1 = null;
        // On creation id is not defined
        if (dto.getId() == null) {
            creneaux = creneauxDAO.save(new Creneaux(null,dto.getDateDebut(),dto.getDateFin(),dto.getJours(),dto.getHeuresDebutFin().stream().map(HeuresDebutFin::new).collect(Collectors.toList()))); //la dernière partie sert uniquement à ce que swagger renvoie la bonne chose
            for(HeuresDebutFin heuresDebutFin : dto.getHeuresDebutFin().stream().map(HeuresDebutFin::new).collect(Collectors.toList())){ //On ajoute chaque element de la liste des heuresDebutFin à la table correspondante
                heuresDebutFin.setIdCreneaux(creneaux.getId());
                heuresDebutFinDAO.save(heuresDebutFin);
            }
            logger.info( "un nouveau créneau a été crée, il a pour id "+creneaux.getId().toString());
        }
        else {
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
                    heuresDebutFinDAO.save(heuresDebutFin);
                }
                creneaux.setHeuresDebutFin(dto.getHeuresDebutFin().stream().map(HeuresDebutFin::new).collect(Collectors.toList()));

                logger.info("le créneau avec l'id " + creneaux.getId().toString() + " a été modifié");

            }
            catch (EntityNotFoundException e){
                logger.error("id créneau non trouvé, pour en créer un nouveau, mettre null pour son id");
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "entity not found"
                );
            }
        }
        return new CreneauxDTO(creneaux);
    }



    /**
     * Prend un LocalDateTime de rendez-vous en paramètre et renvoit l'id du créneau correspondant s'il existe et null sinon
     * @param dateDebutRDV,duree
     * @return id du créneau corespondant et null sinon
     */

    /**
     * Cherche si il y a un créneau future dans le rendez-vous commencant à dateDebutRDV et d'une durée dureee qui correspond à ce rendez-vous
     * @param dateDebutRDV
     * @param duree
     * @return
     */
    public CreneauxDTO isWithinASlot(LocalDateTime dateDebutRDV, Duration duree){
        return isWithinASlot(dateDebutRDV,duree,LocalDate.now());
    }

    /**
     * Cherche si il y a un créneau après dateDebutRecherche la date  dans le rendez-vous commencant à dateDebutRDV et d'une durée dureee qui correspond à ce rendez-vous
     * @param dateDebutRDV
     * @param duree
     * @param dateDebutRecherche
     * @return
     */
    public CreneauxDTO isWithinASlot(LocalDateTime dateDebutRDV, Duration duree, LocalDate dateDebutRecherche){
        logger.info( "un créneau pour un rendez-vous le "+dateDebutRDV.toString()+" d'une durée de "+duree.toString()+ "après la date de "+dateDebutRecherche.toString()+" a été cherché");

        LocalDateTime dateFinRDV= dateDebutRDV.plus(duree);

        CreneauxDTO bonCreneau=null;
        for (Creneaux creneau : creneauxDAO.findCreneauxAfterDate(dateDebutRecherche)){
            if (
                    (creneau.getJours().contains(dateDebutRDV.getDayOfWeek())) &&
                    (dateDebutRDV.toLocalDate().isAfter(creneau.getDateDebut())) || (dateDebutRDV.toLocalDate().equals(creneau.getDateDebut())) &&
                    (dateFinRDV.toLocalDate().isBefore(creneau.getDateFin())) || (dateDebutRDV.toLocalDate().equals(creneau.getDateDebut()))
            ){
                for (HeuresDebutFin plage:creneau.getHeuresDebutFin()){
                    if (
                            (dateDebutRDV.toLocalTime().isAfter(plage.getTempsDebut())) || (dateDebutRDV.toLocalTime().equals(plage.getTempsDebut())) &&
                            (dateFinRDV.toLocalTime().isBefore(plage.getTempsFin())) || (dateFinRDV.toLocalTime().equals(plage.getTempsFin()))
                    ){
                        bonCreneau=new CreneauxDTO(creneau);
                    }
                }

            }
        }
        if(bonCreneau != null && logger.isDebugEnabled()){
            logger.info("Le créneau "+bonCreneau.getId().toString()+" correspond");
        } else if (logger.isDebugEnabled()) {
            logger.info("Aucun créneau ne correspond");
        }
        return bonCreneau;
    }

}
