package ma.enset.infractionservice.web;

import ma.enset.infractionservice.entities.Infraction;
import ma.enset.infractionservice.repositories.InfractionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class InfractionRestController {
    private  InfractionRepository infractionRepository;

    @Autowired
    public InfractionRestController(InfractionRepository infractionRepository) {
        this.infractionRepository = infractionRepository;
    }

    @GetMapping("/infractions")
    public List<Infraction> getAllInfractions() {
        return infractionRepository.findAll();
    }

    @GetMapping("/infractions/{id}")
    public Infraction getInfractionById(@PathVariable Long id) {
        return infractionRepository.findById(id).orElseThrow(() -> new RuntimeException(String.format("Véhicule %s not found", id)));
    }

    @GetMapping("/infractions/radar/{id}")
    public List<Infraction> getInfractionsByRadarId(@PathVariable("id") Long id){
        return infractionRepository
    }

    @PostMapping
    public Infraction createInfraction(@RequestBody Infraction infraction) {
        return  infractionRepository.save(infraction);
    }

    @PutMapping("/{id}")
    public Infraction updateInfraction(@PathVariable Long id, @RequestBody Infraction inf) {
        Infraction infraction = infractionRepository.findById(id).orElseThrow(() -> new RuntimeException(String.format("Véhicule %s not found", id)));
        if (inf.getDate() != null) infraction.setDate(inf.getDate());
            return infractionRepository.save(infraction);
    }

    @DeleteMapping("/{id}")
    public void deleteInfraction(@PathVariable Long id) {
        infractionRepository.deleteById(id);
    }
}