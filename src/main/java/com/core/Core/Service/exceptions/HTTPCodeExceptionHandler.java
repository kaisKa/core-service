package com.core.Core.Service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.core.Core.Service.exceptions.NotFoundException;
import java.util.HashMap;
import java.util.Map;


@RestControllerAdvice
public class HTTPCodeExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> NotFoundHandler(Exception e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage() );
    }

//    @ExceptionHandler(MethodArgumentNotValidException.class)
////    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public ResponseEntity<Object> argumentExceptionHandler(MethodArgumentNotValidException e) {
//        HttpStatus status = HttpStatus.BAD_REQUEST;
//        return ResponseEntity.status(status)
//                .body(
//                        e.getBindingResult().getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(" & ")));
//    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body( e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<String> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ex.getMessage());
    }
    /**
     * fallback handler method
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class) // exception handled
    public ResponseEntity<Object> handleExceptions(
            Exception e
    ) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR; // 500
        e.printStackTrace();

        String response = e.getLocalizedMessage() ;
        return ResponseEntity.status(status).body(response);
    }
}
