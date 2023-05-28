package org.sid.radarService.feign;

import org.sid.radarService.model.Owner;
import org.sid.radarService.model.Vehicle;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "REGISTRATION-SERVICE")
public interface RegistrationFeignClient {

    @GetMapping("/web/owners/exist/{id}")
    boolean isOwnerExistsById(@PathVariable("id") Long id);

    @GetMapping("/web/owners/{id}")
    Owner getOwner(@PathVariable("id") Long id);

    @GetMapping("/web/vehicles/exist/{id}")
    boolean isVehicleExistsById(@PathVariable("id") Long id);

    @GetMapping("/web/vehicles/{id}")
    Vehicle getVehicle(@PathVariable("id") Long id);
}
