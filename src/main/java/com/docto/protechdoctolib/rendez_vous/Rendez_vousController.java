package com.docto.protechdoctolib.rendez_vous;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.util.List;
import java.util.stream.Collectors;

@RestController // (1)
@RequestMapping("/api/rendez_vous") // (2)
@Transactional // (3)
public class Rendez_vousController {

    private final Rendez_vousDAO rendez_vousDAO;

    public Rendez_vousController(Rendez_vousDAO rendez_vousDAO) {
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
     * @return creneau
     */
    @GetMapping(path = "/{id}")
    public Rendez_vousDTO findById(@PathVariable Long id) {
        Rendez_vousDTO creneauId= rendez_vousDAO.findById(id).map(Rendez_vousDTO::new).orElse(null);
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
        Rendez_vous rendez_vous = null;
        // On creation id is not defined
        if (dto.getId() == null) {
            rendez_vous = rendez_vousDAO.save(new Rendez_vous(dto.getId(), dto.getIdCreneau(),dto.getIdUser(), dto.getDateDebut(), dto.getDuree(), dto.getMoyenCommunication(),dto.getZoomLink()));
        } else {
            rendez_vous = rendez_vousDAO.getReferenceById(dto.getId());  // (9)
            rendez_vous.setDateDebut(dto.getDateDebut());
            rendez_vous.setIdCreneau(dto.getIdCreneau());
            rendez_vous.setIdUser(dto.getIdUser());
            rendez_vous.setDuree(dto.getDuree()); /* mettre une durée dans le format "PT60S" ou "PT2M"...*/
            rendez_vous.setMoyenCommunication(dto.getMoyenCommunication());
            rendez_vous.setZoomLink(dto.getZoomLink());


        }
        return new Rendez_vousDTO(rendez_vous);
    }
}
