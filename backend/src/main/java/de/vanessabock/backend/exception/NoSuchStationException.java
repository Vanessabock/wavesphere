package de.vanessabock.backend.exception;

import java.util.NoSuchElementException;

public class NoSuchStationException extends NoSuchElementException {
    public NoSuchStationException(String message) {
        super(message);
    }
}
