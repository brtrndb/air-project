package tech.brtrndb.airproject.flightplan.api.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import tech.brtrndb.airproject.flightplan.api.dto.ApiErrorDTO;
import tech.brtrndb.airproject.flightplan.error.DeliveryCannotBeSatisfiedException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DeliveryCannotBeSatisfiedException.class)
    public final ResponseEntity<ApiErrorDTO> handleException(DeliveryCannotBeSatisfiedException e) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        String message = e.getMessage();

        ApiErrorDTO error = new ApiErrorDTO(status.value(), message);

        ResponseEntity<ApiErrorDTO> response = ResponseEntity.status(status)
                .body(error);

        log.error("API exception caught with id={} caused by {}. Reason: {}.", error.id(), e.getClass().getSimpleName(), message);

        return response;
    }

}
