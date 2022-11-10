package com.docto.protechdoctolib.rendez_vous;

import com.docto.protechdoctolib.creneaux.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController // (1)
@RequestMapping("/api/rendez_vous") // (2)
@Transactional // (3)
public class Rendez_vousController {

    private final CreneauxDAO creneauxDAO;
    private final Rendez_vousDAO rendez_vousDAO;

    private static final Logger logger = LogManager.getLogger(CreneauxController.class);

    public Rendez_vousController(CreneauxDAO creneauxDAO, Rendez_vousDAO rendez_vousDAO) {
        this.creneauxDAO = creneauxDAO;
        this.rendez_vousDAO = rendez_vousDAO;
    }

    /**
     * Donne la liste de tous les rdv
     *
     * @return une liste de tous les rdv
     */
    @GetMapping
    public List<Rendez_vousDTO> findAll() {
        return rendez_vousDAO.findAll().stream().map(Rendez_vousDTO::new).collect(Collectors.toList());
    }

    /**
     * Renvoit le rdv ayant pour id le paramètre
     *
     * @param id
     * @return rdv
     */
    @GetMapping(path = "/{id}")
    public Rendez_vousDTO findById(@PathVariable Long id) {
        Rendez_vousDTO rendez_vousId= rendez_vousDAO.findById(id).map(Rendez_vousDTO::new).orElse(null);
        if (rendez_vousId==null){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "entity not found"
            );
        }
        else{
            return rendez_vousId;
        }

    }


    /**
     * Supprime le créneau ayant pour id le paramètre
     * @param id
     */
    @DeleteMapping(path = "/{id}")
    public void deleteParId(@PathVariable Long id) {
        try{
            rendez_vousDAO.deleteById(id);
        }
        catch (EmptyResultDataAccessException e){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "entity not found"
            );
        }
    }

    /**
     * Prend un dto de rdv en paramètre, crée ce rdv dans la db si son id est null et le modifie si son id existe déjà
     *
     * @param dto
     * @return le dto du rdv crée
     */
    @PostMapping("/create_or_modify") // (8)
    public Rendez_vousDTO create_or_modify(@RequestBody Rendez_vousDTO dto) {
        CreneauxDTO creneauMatch = isWithinASlot(dto.getDateDebut(),dto.getDuree());
        if (creneauMatch == null){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "creneau not found"
            );
        }

        Long creneauId = creneauMatch.getId();
        Rendez_vous rendez_vous = null;
        // On creation id is not defined
        if (dto.getId() == null) {
            rendez_vous = rendez_vousDAO.save(new Rendez_vous(dto.getId(), creneauId ,dto.getIdUser(), dto.getDateDebut(), dto.getDuree(), dto.getMoyenCommunication(),dto.getZoomLink()));
        } else {
            rendez_vous = rendez_vousDAO.getReferenceById(dto.getId());  // (9)
            rendez_vous.setDateDebut(dto.getDateDebut());
            rendez_vous.setIdCreneau(creneauId);
            rendez_vous.setIdUser(dto.getIdUser());
            rendez_vous.setDuree(dto.getDuree()); /* mettre une durée dans le format "PT60S" ou "PT2M"...*/
            rendez_vous.setMoyenCommunication(dto.getMoyenCommunication());
            rendez_vous.setZoomLink(dto.getZoomLink());


        }
        return new Rendez_vousDTO(rendez_vous);
    }


    /**
     * Cherche si il y a un créneau future dans le rendez-vous commencant à dateDebutRDV et d'une durée dureee qui correspond à ce rendez-vous
     * @param dateDebutRDV
     * @param duree
     * @return
     */
    public CreneauxDTO isWithinASlot(LocalDateTime dateDebutRDV, Duration duree){
        return isWithinASlot(dateDebutRDV,duree, LocalDate.now());
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
                            ((dateDebutRDV.toLocalDate().isAfter(creneau.getDateDebut())) || (dateDebutRDV.toLocalDate().equals(creneau.getDateDebut()))) &&
                            ((dateFinRDV.toLocalDate().isBefore(creneau.getDateFin())) || (dateDebutRDV.toLocalDate().equals(creneau.getDateDebut())))
            ){
                for (HeuresDebutFin plage:creneau.getHeuresDebutFin()){
                    if (
                            ((dateDebutRDV.toLocalTime().isAfter(plage.getTempsDebut())) || (dateDebutRDV.toLocalTime().equals(plage.getTempsDebut()))) &&
                                    ((dateFinRDV.toLocalTime().isBefore(plage.getTempsFin())) || (dateFinRDV.toLocalTime().equals(plage.getTempsFin())))
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
