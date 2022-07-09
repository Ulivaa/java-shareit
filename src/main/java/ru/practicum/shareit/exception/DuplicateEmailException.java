package ru.practicum.shareit.exception;

public class DuplicateEmailException extends RuntimeException {
    public DuplicateEmailException() {
        super("Такой email уже существует.");
    }
}
