package com.docto.protechdoctolib.creneaux;

import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;

@RestController // (1)
@RequestMapping("/api/creneaux") // (2)
@Transactional // (3)
public class CreneauxController {

    private final CreneauxDAO creneauxDAO;

    public CreneauxController(CreneauxDAO creneauxDAO) {
        this.creneauxDAO = creneauxDAO;
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
        // On creation id is not defined
        if (dto.getId() == null) {
            creneaux = creneauxDAO.save(new Creneaux(null,dto.getDateDebut(),dto.getDateFin(),dto.getJours(),dto.getTimeDebut(),dto.getTimeFin()));
        }
        else {
            creneaux = creneauxDAO.getReferenceById( dto.getId());  // (9)
            creneaux.setDateDebut(dto.getDateDebut());
            creneaux.setDateFin(dto.getDateFin());
            creneaux.setJours(dto.getJours());
            creneaux.setTimeDebut(dto.getTimeDebut());
            creneaux.setTimeFin(dto.getTimeFin());

        }
        return new CreneauxDTO(creneaux);
    }

    /**
     * Prend un GregorianCalendar de rendez-vous en paramètre et renvoit l'id du créneau correspondant s'il existe et null sinon
     * @param dateeRDV
     * @return id du créneau corespondant et null sinon
     */
    /*@PostMapping("/isWithinASlot")
    public CreneauxDTO isWithinASlot(@RequestBody DateDTO dateeRDV){
        GregorianCalendar dateRDV = dateeRDV.getDate();
        CreneauxDTO bonCreneau=null;
        List<Creneaux> tousLesCreneaux=creneauxDAO.findAll();
        for (Creneaux creneau : tousLesCreneaux){
            if ((dateRDV.before(creneau.getDateFin()) & (dateRDV.after(creneau.getDateDebut())))& ((dateRDV.getTime()<=creneau.getTimeFin().getTime()) & (dateRDV.getTime()>=creneau.getTimeDebut().getTime())&(creneau.getJours().contains(DayOfWeek.of(dateRDV.getDay()))))){
                //il faut ajouter le check du jour
                bonCreneau=new CreneauxDTO(creneau);
            }
        }
        return bonCreneau;
    }*/

}
