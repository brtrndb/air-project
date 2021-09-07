package tech.brtrndb.airproject.flightplan.csv;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DroneCSV {

    @JsonProperty("DroneId")
    private String droneId;

    @JsonProperty("x")
    private double x;

    @JsonProperty("y")
    private double y;

}
