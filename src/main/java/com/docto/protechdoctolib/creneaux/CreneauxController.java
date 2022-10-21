package com.docto.protechdoctolib.creneaux;

import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
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
                heuresDebutFinDAO.deleteById(ancienheuresDebutFin.getId());
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
    /*public CreneauxDTO isWithinASlot(GregorianCalendar dateDebutRDV,int duree){
        GregorianCalendar dateFinRDV= dateDebutRDV;
        System.out.println("kjdns");
        System.out.println(dateDebutRDV.get(Calendar.DAY_OF_MONTH));
        System.out.println(dateDebutRDV.get(Calendar.MONTH));
        dateFinRDV.add(Calendar.MINUTE,duree);

        CreneauxDTO bonCreneau=null;
        for (Creneaux creneau : creneauxDAO.findAll()){
            System.out.println(creneau.getDateDebut().get(Calendar.DAY_OF_MONTH));
            System.out.println(creneau.getDateDebut().get(Calendar.MONTH));
            if (
                    (creneau.getJours().contains(DayOfWeek.of(dateDebutRDV.get(Calendar.DAY_OF_WEEK)))) &&
                            (dateDebutRDV.getTimeInMillis()>=creneau.getDateDebut().getTimeInMillis()) &&
                            (dateFinRDV.getTimeInMillis()<=creneau.getDateFin().getTimeInMillis())
            ){
                for (HeuresDebutFin plage:creneau.getHeuresDebutFin()){
                    if (
                            (plage.getTempsDebut().get(Calendar.HOUR_OF_DAY)*60+plage.getTempsDebut().get(Calendar.MINUTE)>=creneau.getDateDebut().get(Calendar.HOUR_OF_DAY)*60+creneau.getDateDebut().get(Calendar.MINUTE)) &&
                                    (plage.getTempsFin().get(Calendar.HOUR_OF_DAY)*60+plage.getTempsFin().get(Calendar.MINUTE)<=creneau.getDateFin().get(Calendar.HOUR_OF_DAY)*60+creneau.getDateFin().get(Calendar.MINUTE))
                    ){
                        bonCreneau=new CreneauxDTO(creneau);
                    }
                }

            }
        }
        return bonCreneau;
    }*/

}
