package com.microecom.customerservice.http.controller.data;

import com.microecom.customerservice.model.exception.InvalidInputDataException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Response object for InvalidInputExceptions.
 */
public class InvalidInputExceptionRead extends ExceptionRead {
    private final InputViolationRead[] violations;

    public InvalidInputExceptionRead(InvalidInputDataException ex) {
        super(ex.getLocalizedMessage());
        if (ex.getFieldViolations().isPresent()) {
            violations = InputViolationRead.fromViolationsMap(ex.getFieldViolations().get());
        } else {
            violations = null;
        }
    }

    public InvalidInputExceptionRead(MethodArgumentNotValidException ex) {
        super(
                "Validation failed for "
                        + String.join(
                                ", ",
                        ex.getBindingResult().getAllErrors().stream()
                                .map(ObjectError::getObjectName).distinct().collect(Collectors.toList())
                )
        );
        violations = InputViolationRead.fromObjectErrors(ex.getBindingResult().getAllErrors());
    }

    public Optional<InputViolationRead[]> getViolations() {
        return Optional.ofNullable(violations);
    }
}
