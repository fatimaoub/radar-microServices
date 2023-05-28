package org.sid.radarService.feign;

import org.sid.radarService.model.Infraction;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "INFRACTION-SERVICE")
public interface InfractionFeignClient {

    @GetMapping("/web/infractions/{id}")
    Infraction getInfraction(@PathVariable("id") Long id);

    @GetMapping("/web/infractions/radar/{id}")
    List<Infraction> getInfractionsByRadarId(@PathVariable("id") Long id);

    @PostMapping("/web/infractions")
    Infraction saveInfraction(@RequestBody Infraction infraction);
}
