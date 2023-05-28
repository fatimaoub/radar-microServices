package org.sid.radarService.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Data
@Builder @ToString
@AllArgsConstructor @NoArgsConstructor
public class Owner {
    private Long id;
    private String name;
    private String birthDate;
    private String email;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Vehicle> vehicles;
}
