package de.vanessabock.backend.exception;

public class StationAlreadyInDatabaseException extends Exception {
    public StationAlreadyInDatabaseException(String message) {
        super(message);
    }
}
