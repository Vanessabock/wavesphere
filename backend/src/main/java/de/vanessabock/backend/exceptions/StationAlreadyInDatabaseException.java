package de.vanessabock.backend.exceptions;

public class StationAlreadyInDatabaseException extends Exception {
    public StationAlreadyInDatabaseException(String message) {
        super(message);
    }
}
