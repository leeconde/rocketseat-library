package org.example.exception;

public class BookWasNotAvailableException extends RuntimeException {

    public BookWasNotAvailableException() {
        super("The book was not available");
    }
}
