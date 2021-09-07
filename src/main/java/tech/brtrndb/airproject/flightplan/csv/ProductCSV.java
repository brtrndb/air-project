package tech.brtrndb.airproject.flightplan.csv;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductCSV {

    private String productId;
    private String productName;
    private Map<String, Integer> stockQuantity;

}
