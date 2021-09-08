package tech.brtrndb.airproject.flightplan.api;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tech.brtrndb.airproject.flightplan.api.dto.DeliveryDTO;
import tech.brtrndb.airproject.flightplan.api.mapper.DeliveryMapper;
import tech.brtrndb.airproject.flightplan.csv.CsvUtils;
import tech.brtrndb.airproject.flightplan.domain.Delivery;
import tech.brtrndb.airproject.flightplan.service.DeliveryService;

@Slf4j
@RestController
@RequestMapping("/api" + FlightPlanController.ENDPOINT)
public class FlightPlanController {

    public static final String ENDPOINT = "/flightplan";
    private static final String DEFAULT_FILENAME = "flightplan.csv";
    private static final ObjectWriter WRITER = CsvUtils.buildWriter(DeliveryDTO.class, ';');

    private final DeliveryService service;

    public FlightPlanController(DeliveryService service) {
        this.service = service;
    }

    @GetMapping(produces = "text/csv")
    public ResponseEntity<Resource> getFlightPlan() throws Exception {
        List<Delivery> deliveries = this.service.generateDeliveries();
        List<DeliveryDTO> deliveryDTOs = DeliveryMapper.toDTOs(deliveries);

        Resource csvResource = new ByteArrayResource(WRITER.writeValueAsBytes(deliveryDTOs));

        return ResponseEntity.ok()
                .contentType(MediaType.valueOf("text/csv"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"%s\"".formatted(DEFAULT_FILENAME))
                .body(csvResource);
    }

}
