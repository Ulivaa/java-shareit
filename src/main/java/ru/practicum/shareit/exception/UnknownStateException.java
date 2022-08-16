package ru.practicum.shareit.exception;


public class UnknownStateException extends RuntimeException {
    public UnknownStateException() {
        super("Unknown state: UNSUPPORTED_STATUS");
    }
}
