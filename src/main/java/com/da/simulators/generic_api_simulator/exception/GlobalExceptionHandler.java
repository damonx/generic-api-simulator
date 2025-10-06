package com.da.simulators.generic_api_simulator.exception;

import com.da.simulators.generic_api_simulator.model.Message;
import com.da.simulators.generic_api_simulator.model.Response;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Global exception handler which intercepts different types of exceptions and returns error responses respectively.
 */
@ControllerAdvice
public class GlobalExceptionHandler
{
    /**
     * Handles all validation errors and translates the errors to HTTP 400 bad request error with proper error payload.
     *
     * @param ex the exception to be handled.
     * @return the instance of {@link ResponseEntity}.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response> handleValidationErrors(final MethodArgumentNotValidException ex)
    {
        final Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
            .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

        final Response response = new Response();
        response.setMessages(errors.entrySet().stream()
            .filter(Objects::nonNull)
            .map(entry -> new Message(String.join(":", entry.getKey(), entry.getValue()), 4002))
            .toList());
        return ResponseEntity.badRequest().body(response);
    }

    /**
     * Handles all instance of {@link GenericApiSimulatorException} and translates the errors to proper error payload with http status code.
     *
     * @param ex the instance of {@link GenericApiSimulatorException}.
     * @return the instance of {@link ResponseEntity}.
     */
    @ExceptionHandler(GenericApiSimulatorException.class)
    public ResponseEntity<Response> handleGenericApiSimulatorError(final GenericApiSimulatorException ex)
    {
        final Response response = new Response();
        response.setMessages(ex.getMessages());
        return ResponseEntity.status(ex.getStatusCode().value()).body(response);
    }
}
