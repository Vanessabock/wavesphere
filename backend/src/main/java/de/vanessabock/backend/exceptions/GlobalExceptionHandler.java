package de.vanessabock.backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({NoSuchStationException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessage globalNoSuchStationExceptionHandler(NoSuchStationException exception){
        return new ErrorMessage("Not found. " + exception.getMessage());
    }

    @ExceptionHandler({StationAlreadyInDatabaseException.class})
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ErrorMessage globalStationAlreadyInDatabaseExceptionHandler(StationAlreadyInDatabaseException exception){
        return new ErrorMessage("Not allowed. " + exception.getMessage());
    }
}
