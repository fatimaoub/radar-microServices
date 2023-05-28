package ma.enset.infractionservice.repositories;

import ma.enset.infractionservice.entities.Infraction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InfractionRepository extends JpaRepository<Infraction, Long> {
    List<Infraction> findByRadarId(Long radarId);
}