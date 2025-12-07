package uk.gov.hmcts.reform.dev.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * Global exception handler for the application.
 * Catches exceptions thrown by controllers and formats a proper HTTP response.
 */

@RestControllerAdvice
public class GlobalExceptionHandler {


    // This method will handle all MethodArgumentNotValidException exceptions,
    // which are thrown when @Valid validation on a controller method argument fails
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {

        // ex.getBindingResult() gives access to the results of the failed validation
        // getAllErrors() returns a list of all validation errors (could be multiple fields)
        // get(0) picks the first error in that list
        // getDefaultMessage() retrieves the human-readable message defined in the @NotBlank, @Size, etc.
        String errorMessage = ex.getBindingResult()
            .getAllErrors()
            .get(0)
            .getDefaultMessage();

        return ResponseEntity
            .badRequest()
            .body(errorMessage);
    }
}
