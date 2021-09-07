package tech.brtrndb.airproject.flightplan.csv;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderCSV {

    private String orderId;
    private String customerId;
    private Map<String, Integer> itemsQuantity;

}
