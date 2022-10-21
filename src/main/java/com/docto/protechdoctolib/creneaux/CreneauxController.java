package com.docto.protechdoctolib.creneaux;

import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@RestController // (1)
@RequestMapping("/api/creneaux") // (2)
@Transactional // (3)
public class CreneauxController {

    private final CreneauxDAO creneauxDAO;
    private final HeuresDebutFinDAO heuresDebutFinDAO;

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
        return creneauxDAO.findAll().stream().map(CreneauxDTO::new).collect(Collectors.toList());
    }

    /**
     * Renvoit le créneau ayant pour id le paramètre
     * @param id
     * @return creneau
     */
    @GetMapping(path = "/{id}")
    public CreneauxDTO findById(@PathVariable Long id) {
        return creneauxDAO.findById(id).map(CreneauxDTO::new).orElse(null);
    }

    /**
     * Supprime le créneau ayant pour id le paramètre
     * @param id
     */
    @DeleteMapping(path = "/{id}")
    public void deleteParId(@PathVariable Long id) {
        creneauxDAO.deleteById(id);
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
        }
        else {
            creneaux = creneauxDAO.getReferenceById( dto.getId());  // (9)
            creneaux.setDateDebut(dto.getDateDebut());
            creneaux.setDateFin(dto.getDateFin());
            creneaux.setJours(dto.getJours());
            creneaux.setHeuresDebutFin(dto.getHeuresDebutFin().stream().map(HeuresDebutFin::new).collect(Collectors.toList()));

            //On supprime et en recrée les créneaux pour avoir des problèmes de nombres de créneaux
            for(HeuresDebutFin ancienheuresDebutFin:heuresDebutFinDAO.findByIdCreneaux(dto.getId())){ //supprime tout les anciens créneaux
                heuresDebutFinDAO.deleteById(ancienheuresDebutFin.getIdPlage());
            }
            for(HeuresDebutFin heuresDebutFin : dto.getHeuresDebutFin().stream().map(HeuresDebutFin::new).collect(Collectors.toList())){ //Crée les nouveaux créneaux
                heuresDebutFin.setIdCreneaux(creneaux.getId());
                heuresDebutFinDAO.save(heuresDebutFin);
            }
            creneaux.setHeuresDebutFin(dto.getHeuresDebutFin().stream().map(HeuresDebutFin::new).collect(Collectors.toList()));

        }
        return new CreneauxDTO(creneaux);
    }



    /**
     * Prend un GregorianCalendar de rendez-vous en paramètre et renvoit l'id du créneau correspondant s'il existe et null sinon
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
        return bonCreneau;
    }

}
