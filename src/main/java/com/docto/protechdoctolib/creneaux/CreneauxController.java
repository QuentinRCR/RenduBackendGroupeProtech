package com.docto.protechdoctolib.creneaux;

import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
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

    @GetMapping
    public List<CreneauxDTO> findAll(){
        return creneauxDAO.findAll().stream().map(CreneauxDTO::new).collect(Collectors.toList());
    }

    @PostMapping // (8)
    public CreneauxDTO create(@RequestBody CreneauxDTO dto) {
        Creneaux creneaux = null;
        // On creation id is not defined
        if (dto.getId() == null) {
            creneaux = creneauxDAO.save(new Creneaux(dto.getId(),dto.getDateDebut(),dto.getDateFin(),dto.getJours(),dto.getTimeDebut(),dto.getTimeFin()));
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
}
