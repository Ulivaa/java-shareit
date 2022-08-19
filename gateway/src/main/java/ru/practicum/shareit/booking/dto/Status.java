package ru.practicum.shareit.booking.dto;

import java.util.Optional;

public enum Status {
    WAITING,
    APPROVED,
    REJECTED,
    CANCELED;

    public static Optional<Status> from(String stringStatus) {
        for (Status status : values()) {
            if (status.name().equalsIgnoreCase(stringStatus)) {
                return Optional.of(status);
            }
        }
        return Optional.empty();
    }
    }
